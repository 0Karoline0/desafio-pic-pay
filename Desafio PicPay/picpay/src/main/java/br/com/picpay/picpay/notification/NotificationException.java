package br.com.picpay.picpay.notification;

/**
 * Exceção lançada quando ocorre um erro durante o processo de notificação.
 */
public class NotificationException extends RuntimeException {

    /**
     * Construtor da classe NotificationException.
     * @param message A mensagem que descreve a razão da exceção.
     */
    NotificationException(String message){
        super(message);
    }
    
}
