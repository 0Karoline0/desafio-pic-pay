package br.com.picpay.picpay.authorization;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import br.com.picpay.picpay.transaction.Transaction;

/**
 * Serviço responsável por autorizar transações.
 */
@Service
public class AuthorizerService {

    private RestClient restClient;

    /**
     * Construtor da classe AuthorizerService.
     * @param builder O construtor do cliente REST utilizado para realizar a chamada ao endpoint de autorização.
     */
    public AuthorizerService(RestClient.Builder builder){
        // Endpoint não está mais disponível
        this.restClient = builder.baseUrl("https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc").build();
    }
    
    /**
     * Autoriza uma transação.
     * @param transaction A transação a ser autorizada.
     * @throws UnauthorizedTransactionException Se a transação não for autorizada.
     */
    public void authorize(Transaction transaction){
        var response = restClient.get().retrieve().toEntity(Authorization.class);

        if (response.getStatusCode().isError() || !response.getBody().isAuthorized()){
            throw new UnauthorizedTransactionException("Unauthorized transaction!");
        }
    }

}

/**
 * Representa a autorização de uma transação.
 */
record Authorization(String message) {
    /**
     * Verifica se a transação foi autorizada.
     * @return true se a transação foi autorizada, false caso contrário.
     */
    public boolean isAuthorized() {
      return message.equals("Autorizado");
    }
  }
