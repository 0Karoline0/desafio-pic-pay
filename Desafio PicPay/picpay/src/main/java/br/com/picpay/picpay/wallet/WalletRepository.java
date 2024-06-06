package br.com.picpay.picpay.wallet;

import org.springframework.data.repository.CrudRepository;

/**
 * Repositório da classe Wallet
 */
public interface WalletRepository extends CrudRepository<Wallet, Long> {
    
}
