package br.com.picpay.picpay.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.annotation.CreatedDate;

/**
 * Representa uma transação financeira no sistema.
 */
@Table("TRANSACTIONS")
public record Transaction(
    /**
     * O identificador único da transação.
     */
    @Id Long id,
    
    /**
     * O identificador do pagador da transação.
     */
    Long payer,
    
    /**
     * O identificador do beneficiário da transação.
     */
    Long payee,
    
    /**
     * O valor da transação.
     */
    BigDecimal value,
    
    /**
     * A data e hora de criação da transação.
     */
    @CreatedDate LocalDateTime createdAt) {
  
  /**
   * Construtor da classe Transaction. Arredonda o valor da transação para duas casas decimais.
   */
  public Transaction {
    value = value.setScale(2);
  }
}
