package br.com.picpay.picpay.notification;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import br.com.picpay.picpay.transaction.Transaction;

/**
 * Produtor de notificações de transações.
 */
@Service
public class NotificationProducer {
    
    private final KafkaTemplate<String, Transaction> kafkaTemplate;

    /**
     * Construtor da classe NotificationProducer.
     * @param kafkaTemplate O template do Kafka utilizado para enviar mensagens.
     */
    public NotificationProducer(KafkaTemplate<String, Transaction> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Envia uma notificação de transação para o tópico do Kafka.
     * @param transaction A transação a ser notificada.
     */
    public void sendNotification(Transaction transaction){
        kafkaTemplate.send("transaction-notification", transaction);
    }

}
