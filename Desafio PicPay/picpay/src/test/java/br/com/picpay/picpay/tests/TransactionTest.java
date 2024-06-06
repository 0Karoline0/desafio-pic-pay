package br.com.picpay.picpay.tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.picpay.picpay.wallet.Wallet;
import br.com.picpay.picpay.wallet.WalletRepository;
import br.com.picpay.picpay.wallet.WalletType;
import br.com.picpay.picpay.authorization.AuthorizerService;
import br.com.picpay.picpay.notification.NotificationService;
import br.com.picpay.picpay.transaction.InvalidTransactionException;
import br.com.picpay.picpay.transaction.Transaction;
import br.com.picpay.picpay.transaction.TransactionRepository;
import br.com.picpay.picpay.transaction.TransactionService;

@ExtendWith(MockitoExtension.class)
public class TransactionTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private AuthorizerService authorizerService;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private TransactionService transactionService;

    private Wallet payer;
    private Wallet payee;
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        payer = new Wallet(1L, "John Doe", 12345678901L, "john.doe@example.com", "password", WalletType.COMUM.getValue(), new BigDecimal("100.00"));
        payee = new Wallet(2L, "Jane Doe", 12345678902L, "jane.doe@example.com", "password", WalletType.COMUM.getValue(), new BigDecimal("50.00"));
        transaction = new Transaction(1L, payer.id(), payee.id(), new BigDecimal("20.00"), null);
    }

    @Test
    void testCreateTransactionSuccessful() {
        when(walletRepository.findById(payer.id())).thenReturn(Optional.of(payer));
        when(walletRepository.findById(payee.id())).thenReturn(Optional.of(payee));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction createdTransaction = transactionService.create(transaction);

        assertNotNull(createdTransaction);
        verify(walletRepository, times(2)).save(any(Wallet.class)); // Espera 2 chamadas para save
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void testCreateTransactionInvalidPayer() {
        // Ensure that findById for payer returns Optional.empty()
        when(walletRepository.findById(payer.id())).thenReturn(Optional.empty());
        when(walletRepository.findById(payee.id())).thenReturn(Optional.of(payee));

        InvalidTransactionException exception = assertThrows(InvalidTransactionException.class, () -> {
            transactionService.create(transaction);
        });

        assertTrue(exception.getMessage().contains("Invalid Transaction"));
    }

    @Test
    void testCreateTransactionInvalidPayee() {
        when(walletRepository.findById(payee.id())).thenReturn(Optional.empty());

        Exception exception = assertThrows(InvalidTransactionException.class, () -> {
            transactionService.create(transaction);
        });

        assertTrue(exception.getMessage().contains("Invalid Transaction"));
    }

    @Test
    void testCreateTransactionInsufficientFunds() {
        payer = new Wallet(1L, "John Doe", 12345678901L, "john.doe@example.com", "password", WalletType.COMUM.getValue(), new BigDecimal("10.00"));
        when(walletRepository.findById(payer.id())).thenReturn(Optional.of(payer));
        when(walletRepository.findById(payee.id())).thenReturn(Optional.of(payee));

        Exception exception = assertThrows(InvalidTransactionException.class, () -> {
            transactionService.create(transaction);
        });

        assertTrue(exception.getMessage().contains("Invalid Transaction"));
    }

    @Test
    void testCreateTransactionSameWallet() {
        Transaction sameWalletTransaction = new Transaction(1L, payer.id(), payer.id(), new BigDecimal("20.00"), null);
    
        Exception exception = assertThrows(InvalidTransactionException.class, () -> {
            transactionService.create(sameWalletTransaction);
        });
    
        assertTrue(exception.getMessage().contains("Invalid Transaction"));
    }

    @Test
    void testCreateTransactionZeroValue() {
        Transaction zeroValueTransaction = new Transaction(1L, payer.id(), payee.id(), BigDecimal.ZERO, null);
    
        Exception exception = assertThrows(InvalidTransactionException.class, () -> {
            transactionService.create(zeroValueTransaction);
        });
    
        assertTrue(exception.getMessage().contains("Invalid Transaction"));
    }

    @Test
    void testCreateTransactionNegativeValue() {
        Transaction negativeValueTransaction = new Transaction(1L, payer.id(), payee.id(), new BigDecimal("-20.00"), null);
    
        Exception exception = assertThrows(InvalidTransactionException.class, () -> {
            transactionService.create(negativeValueTransaction);
        });
    
        assertTrue(exception.getMessage().contains("Invalid Transaction"));
    }

    @Test
    void testCreateTransactionNullPayer() {
        Transaction nullPayerTransaction = new Transaction(1L, null, payee.id(), new BigDecimal("20.00"), null);
    
        Exception exception = assertThrows(InvalidTransactionException.class, () -> {
            transactionService.create(nullPayerTransaction);
        });
    
        assertTrue(exception.getMessage().contains("Invalid Transaction"));
    }

    @Test
    void testCreateTransactionNullPayee() {
        Transaction nullPayeeTransaction = new Transaction(1L, payer.id(), null, new BigDecimal("20.00"), null);
    
        Exception exception = assertThrows(InvalidTransactionException.class, () -> {
            transactionService.create(nullPayeeTransaction);
        });
    
        assertTrue(exception.getMessage().contains("Invalid Transaction"));
    }
}
