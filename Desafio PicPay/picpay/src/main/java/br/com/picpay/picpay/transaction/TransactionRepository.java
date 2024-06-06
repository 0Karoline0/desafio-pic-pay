package br.com.picpay.picpay.transaction;

import org.springframework.data.repository.ListCrudRepository;

/**
 * Interface responsável por definir as operações de acesso aos dados das transações financeiras.
 */
public interface TransactionRepository extends ListCrudRepository<Transaction, Long> {
    
}
