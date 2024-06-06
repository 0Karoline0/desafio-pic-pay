package br.com.picpay.picpay.transaction;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controlador REST para operações relacionadas a transações financeiras.
 */
@RestController
@RequestMapping("transaction")
@Tag(name = "Transaction", description = "Operações relacionadas a transações financeiras")
public class TransactionController {
    
    private final TransactionService transactionService;

    /**
     * Construtor da classe TransactionController.
     * @param transactionService O serviço responsável por manipular as transações.
     */
    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    /**
     * Endpoint para criar uma nova transação.
     * @param transaction A transação a ser criada.
     * @return A transação criada.
     */
    @Operation(summary = "Criar uma nova transação", description = "Cria uma nova transação no sistema")
    @PostMapping
    public Transaction createTransaction(@RequestBody Transaction transaction){
        return transactionService.create(transaction);
    }

    /**
     * Endpoint para listar todas as transações.
     * @return Uma lista contendo todas as transações.
     */
    @Operation(summary = "Listar todas as transações", description = "Lista todas as transações cadastradas no sistema")
    @GetMapping
    public List<Transaction> list(){
        return transactionService.list();
    }

}
