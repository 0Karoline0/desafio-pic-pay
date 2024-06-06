package br.com.picpay.picpay.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import br.com.picpay.picpay.transaction.Transaction;

/**
 * Consumidor de notificações de transações por meio do Kafka.
 */
@Service
public class NotificationConsumer {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(NotificationConsumer.class);
  private RestClient restClient;

  /**
   * Construtor da classe NotificationConsumer.
   * @param builder O construtor do cliente REST.
   */
  public NotificationConsumer(RestClient.Builder builder) {
    this.restClient = builder.baseUrl(
        "https://run.mocky.io/v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6")
        .build();
  }

  /**
   * Método que recebe e processa as notificações de transações.
   * @param transaction A transação recebida do tópico Kafka.
   */
  @KafkaListener(topics = "transaction-notification", groupId = "picpay-desafio-backend")
  public void receiveNotification(Transaction transaction) {
    LOGGER.info("Notificando transação {}...", transaction);

    var response = restClient.get().retrieve().toEntity(Notification.class);

    if (response.getStatusCode().isError() || !response.getBody().message())
      throw new NotificationException("Erro ao notificar transação " + transaction);

    LOGGER.info("Notificação enviada com sucesso {}...", response.getBody());
  }
}
