package br.com.picpay.picpay.transaction;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Classe que trata exceções relacionadas a transações financeiras em todo o aplicativo.
 */
@ControllerAdvice
public class TransactionExceptionHandler {
    
    /**
     * Trata exceções do tipo InvalidTransactionException.
     * @param e A exceção a ser tratada.
     * @return Uma resposta HTTP com o status de erro 400 (Bad Request) e a mensagem de erro da exceção.
     */
    @ExceptionHandler(InvalidTransactionException.class)
    public ResponseEntity<Object> handle(InvalidTransactionException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
