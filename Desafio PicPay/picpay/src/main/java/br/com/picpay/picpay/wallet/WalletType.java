package br.com.picpay.picpay.wallet;

/**
 * Enum que define os tipos de carteira disponíveis no sistema.
 */
public enum WalletType {
    /**
     * Representa o tipo de carteira comum.
     */
    COMUM(1),
    
    /**
     * Representa o tipo de carteira para lojistas.
     */
    LOJISTA(2);
  
    private int value;
  
    /**
     * Construtor privado para o enum WalletType.
     * @param value O valor associado ao tipo de carteira.
     */
    private WalletType(int value) {
      this.value = value;
    }
  
    /**
     * Obtém o valor associado ao tipo de carteira.
     * @return O valor associado ao tipo de carteira.
     */
    public int getValue() {
      return value;
    }
}
