package com.cash.machine.api;

import com.cash.machine.api.routers.Authentication;
import com.cash.machine.api.routers.Information;
import com.cash.machine.api.routers.Operation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


//Carregamento das classes que estão as rotas
@SpringBootApplication
@ComponentScan(basePackageClasses = Authentication.class)
@ComponentScan(basePackageClasses = Operation.class)
@ComponentScan(basePackageClasses = Information.class)
public class ApiApplication {
	
	
//inicia a aplicação
	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

}
