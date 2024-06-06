package br.com.picpay.picpay.authorization;

/**
 * Exceção lançada quando uma transação não é autorizada.
 */
public class UnauthorizedTransactionException extends RuntimeException {
    
    /**
     * Construtor da classe UnauthorizedTransactionException.
     * @param message A mensagem que descreve a razão da exceção.
     */
    public UnauthorizedTransactionException(String message){
        super(message);
    }

}
