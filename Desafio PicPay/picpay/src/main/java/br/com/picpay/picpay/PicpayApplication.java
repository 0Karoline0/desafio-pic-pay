package br.com.picpay.picpay;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.kafka.config.TopicBuilder;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;

@EnableJdbcAuditing
@OpenAPIDefinition(info = @Info(title = "PicPay - Desafio", version = "1.0.0"),
servers = {@Server(url = "http://localhost:8080/")},
tags = {
	@Tag(name = "Transaction", description = "Acesso ao recurso Transaction"),
})
@SpringBootApplication
public class PicpayApplication {

	public static void main(String[] args) {
		SpringApplication.run(PicpayApplication.class, args);
	}

	@Bean
	NewTopic notificationTopic(){
		return TopicBuilder.name("transaction-notification").build();
	}

}
