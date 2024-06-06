package br.com.picpay.picpay.transaction;

/**
 * Exceção lançada quando ocorre uma transação inválida.
 */
public class InvalidTransactionException extends RuntimeException {
    
    /**
     * Constrói uma nova instância de InvalidTransactionException com uma mensagem específica.
     * @param message A mensagem que descreve a razão da exceção.
     */
    public InvalidTransactionException(String message){
        super(message);
    }
}
