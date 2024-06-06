package br.com.picpay.picpay.wallet;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Representa uma carteira de usuário no sistema.
 */
@Table("WALLETS")
public record Wallet (
    /**
     * O identificador único da carteira.
     */
    @Id Long id,
    
    /**
     * O nome completo do titular da carteira.
     */
    String fullName,
    
    /**
     * O CPF (Cadastro de Pessoa Física) do titular da carteira.
     */
    Long cpf,
    
    /**
     * O email associado à carteira.
     */
    String email,
    
    /**
     * A senha da carteira.
     */
    String password,
    
    /**
     * O tipo de carteira.
     * <p>
     * 0 - Tipo padrão
     * 1 - Outro tipo (definir aqui)
     * </p>
     */
    int type,
    
    /**
     * O saldo atual da carteira.
     */
    BigDecimal balance) {

    /**
     * Debita um valor da carteira.
     * @param value O valor a ser debitado.
     * @return Uma nova instância de Wallet com o saldo atualizado.
     */
    public Wallet debit(BigDecimal value) {
        return new Wallet(id, fullName, cpf, email, password, type, balance.subtract(value));
    }
    
    /**
     * Credita um valor na carteira.
     * @param value O valor a ser creditado.
     * @return Uma nova instância de Wallet com o saldo atualizado.
     */
    public Wallet credit(BigDecimal value) {
        return new Wallet(id, fullName, cpf, email, password, type, balance.add(value));
    }
}
