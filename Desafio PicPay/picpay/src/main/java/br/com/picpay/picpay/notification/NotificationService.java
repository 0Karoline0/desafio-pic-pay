package br.com.picpay.picpay.notification;

import org.springframework.stereotype.Service;

import br.com.picpay.picpay.transaction.Transaction;

/**
 * Serviço responsável por enviar notificações de transações.
 */
@Service
public class NotificationService {
    
    private final NotificationProducer notificationProducer;

    /**
     * Construtor da classe NotificationService.
     * @param notificationProducer O produtor de notificações utilizado para enviar as notificações.
     */
    public NotificationService(NotificationProducer notificationProducer){
        this.notificationProducer = notificationProducer;
    }

    /**
     * Envia uma notificação para a transação especificada.
     * @param transaction A transação a ser notificada.
     */
    public void notify(Transaction transaction){
        notificationProducer.sendNotification(transaction);
    }

}
