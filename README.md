# 🏦 API Desafio Itaú

Aplicação **backend** desenvolvida em **Java + Spring Boot** para processar **transações financeiras em memória** e gerar estatísticas com base nas transações dos últimos 60 segundos, conforme o desafio proposto pelo **Itaú**.

## 🧱 Tecnologias Utilizadas

* Java 17
* Spring Boot
* Lombok
* Maven
* Spring DevTools

## 📁 Estrutura do Projeto

```
Desafio-Itau-API/
├── src/
│   ├── main/java/com/itau/transacoes/
│   │   ├── controller/              # Endpoints principais da API
│   │   ├── business/                # Regras de negócio
│   │   ├── dto/                     # Objetos de transferência de dados
│   │   ├── exceptions/              # Tratamento de exceções personalizadas
│   │   └── infrastructure/entities/ # Entidades do domínio
│   └── resources/
│       └── application.properties
├── pom.xml
└── TransacoesApplication.java
```
## 🚀 Funcionalidades

* Registro de **transações** com valor e data/hora
* Cálculo das **estatísticas** (soma, média, mínimo, máximo e quantidade) das transações dos últimos 60 segundos
* Remoção de todas as transações em memória
* Tratamento de erros conforme o desafio (201, 400, 422, etc)
* Processamento **100% em memória**, sem uso de banco de dados

## ⚙️ Execução do Projeto

### 🔹 Ambiente de Desenvolvimento

```bash
mvn clean install
mvn spring-boot:run
```

A API será iniciada em:

```
http://localhost:8080/
```

### 🔹 Build de Produção

```bash
mvn clean package
```

O arquivo final será gerado na pasta `target/` como:

```
Desafio-Itau-API.jar
```

## 🌐 Estrutura de Endpoints

| Método   | Endpoint                 | Descrição                               |
| -------- | ------------------------ | --------------------------------------- |
| `POST`   | `/transacao`             | Registra uma nova transação             |
| `GET`    | `/transacao/estatistica` | Retorna as estatísticas das últimas 60s |
| `DELETE` | `/transacao`             | Remove todas as transações em memória   |

> **Obs:** As transações são mantidas em memória durante a execução. Ao reiniciar a aplicação, todos os dados são perdidos.

## 🧠 Conceito

A **API Desafio Itaú** foi projetada para operar totalmente **em memória**, simulando um fluxo real de transações financeiras.
Seu objetivo é validar o entendimento de **boas práticas REST**, **tratamento de exceções**, e **separação de responsabilidades** entre camadas de negócio, controle e dados.

## 📄 Licença

MIT License
