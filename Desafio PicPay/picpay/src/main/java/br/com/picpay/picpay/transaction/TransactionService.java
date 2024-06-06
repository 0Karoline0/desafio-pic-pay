package br.com.picpay.picpay.transaction;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.picpay.picpay.authorization.AuthorizerService;
import br.com.picpay.picpay.notification.NotificationService;
import br.com.picpay.picpay.wallet.Wallet;
import br.com.picpay.picpay.wallet.WalletRepository;
import br.com.picpay.picpay.wallet.WalletType;

/**
 * Serviço responsável por manipular transações financeiras.
 */
@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    private final AuthorizerService authorizerService;
    private final NotificationService notificationService;

    /**
     * Construtor da classe TransactionService.
     * @param transactionRepository Repositório de transações.
     * @param walletRepository Repositório de carteiras.
     * @param authorizerService Serviço de autorização de transações.
     * @param notificationService Serviço de notificação de transações.
     */
    public TransactionService(TransactionRepository transactionRepository, WalletRepository walletRepository, AuthorizerService authorizerService, NotificationService notificationService) {
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
        this.authorizerService = authorizerService;
        this.notificationService = notificationService;
    }

    /**
     * Cria uma nova transação.
     * @param transaction A transação a ser criada.
     * @return A transação criada.
     */
    @Transactional
    public Transaction create(Transaction transaction) {
        validate(transaction);
        var newTransaction = transactionRepository.save(transaction);


        var walletPayer = walletRepository.findById(transaction.payer()).get();
        var walletPayee = walletRepository.findById(transaction.payee()).get();
        walletRepository.save(walletPayer.debit(transaction.value()));
        walletRepository.save(walletPayee.credit(transaction.value()));



        // Precisou ser comentado pois o endpoint informado pelo desafio não está mais disponível
        // authorizerService.authorize(transaction);
        
        // Precisou ser comentado pois o endpoint informado pelo desafio não está mais disponível
        // notificationService.notify(transaction);

        return newTransaction;
    }

    /**
     * Valida se uma transação é válida.
     * @param transaction A transação a ser validada.
     */
    private void validate(Transaction transaction) {
        walletRepository.findById(transaction.payee())
            .map(payee -> walletRepository.findById(transaction.payer())
                .map(payer -> isTransactionValid(transaction, payer) ? transaction : null)
                .orElseThrow(() -> new InvalidTransactionException("Invalid Transaction - %s".formatted(transaction))))
            .orElseThrow(() -> new InvalidTransactionException("Invalid Transaction - %s".formatted(transaction)));
    }

    /**
     * Verifica se uma transação é válida.
     * @param transaction A transação a ser verificada.
     * @param payer A carteira do pagador da transação.
     * @return true se a transação for válida, false caso contrário.
     */
    private boolean isTransactionValid(Transaction transaction, Wallet payer) {
        return payer.type() == WalletType.COMUM.getValue() && payer.balance().compareTo(transaction.value()) >= 0
                && !payer.id().equals(transaction.payee());
    }

    /**
     * Lista todas as transações.
     * @return Uma lista contendo todas as transações.
     */
    public List<Transaction> list(){
        return transactionRepository.findAll();
    }
}