# Ecommerce Rest API

## Descrição

Este projeto é uma aplicação RESTful de um sistema de e-commerce desenvolvido em Java utilizando Spring Boot. Ele fornece endpoints para autenticação de usuários e gerenciamento de produtos, pedidos e usuários.

## Estrutura do Projeto

![Diagrama](https://github.com/Arthurrsm/ecommerce_RestAPI/assets/125709335/57a9b85d-d75f-498e-ae9d-5ce698d094df)

O projeto está organizado nas seguintes camadas:

- `application`: Contém a classe principal que inicia a aplicação Spring Boot.
- `config`: Configurações de segurança da aplicação.
- `controller`: Controladores REST que lidam com as requisições HTTP.
- `model`: Entidades que representam os dados da aplicação.
- `repository`: Repositórios para persistência de dados.
- `security`: Utilitários de segurança para geração e validação de tokens JWT.
- `service`: Serviços que contêm a lógica de negócios da aplicação.

## Arquivos e Funções

### `EcommerceRestApiApplication.java`

Este é o ponto de entrada da aplicação Spring Boot.

```java
package com.example.Ecommerce_RestAPI.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example"})
public class EcommerceRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceRestApiApplication.class, args);
	}

}

## Diagrama de microsserviços
