# FutureBank

## Índice

- [Descrição do projeto](#descrição)
- [Instalação da API](#instalação-da-api)
- [Instalação do projeto com interface visual (frontend)](#utilizar-api-com-interface-visual-opcional)
- [Rotas](#rotas)
- [Links Úteis](#links-úteis)

## Descrição

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![React](https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-FF6600?style=for-the-badge&logo=rabbitmq&logoColor=white)
![Spring Data](https://img.shields.io/badge/Spring%20Data-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![SQL](https://img.shields.io/badge/SQL-4479A1?style=for-the-b)




É um webservice baseado na arquitetura de microsservices construído com SpringBoot e React que simula um banco virtual com operações de transferência monetária entre usuários, saque e depósito. 

Existe também o sistema de authenticação de usuários contendo campo cpf, email e senha para permissão de efetuar tais operações entre as contas.

Ao se cadastrar pela primera vez, o usuário recebe uma mensagem de boas vindas no email através do serviço de mensageria RabbitMQ.

## Como funciona

Ao criar uma conta a API principal da aplicação cadastra o cpf, senha, email e valor nulo em um banco de dados relacional. Logo em seguida envia uma requisição para a API de mensageria que enviará a mensagem ao determinado email.

## Instalação da API

### Começe clonando o repositório da api da seguinte maneira:

```
git clone https://github.com/guilhermeandradee/future-bank-api.git
```


### Execute o comando de instalação de dependências do Maven:


```
mvn install
```

### Depois o comando para rodar a aplicação:

```
mvn spring-boot:run
```

### Ou se estiver utilizando Gradle:

```
./gradlew build
```
### E depois:
```
./gradlew bootRun
```


## Utilizar API com interface visual (opcional) 
Para utilizar a aplicação completa (com interface visual) complemente com a instalação do frontend:

[Instalação do Frontend](http://github.com/guilhermeandradee/future-bank)

## Rotas

A aplicação rodará na rota: `http://localhost:8080`

|Requisições GET    |Tipo |Descrição                                              |
|-------------------|-----|-------------------------------------------------------|
|`/account/get-all` |[`GET`](#get-get-all)     |Obtém uma lista de contas cadastradas.                      |
|`/account/get-by-cpf`   | [`POST`](#get-get-all)     |Obtém detalhes de uma conta especíica pelo CPF.||
|`/account/save`   |  [`POST`](#get-get-all)    |Salva uma nova conta.                         |
|`/account/make-deposit` |[`PUT`](#get-get-all)     |Adiciona um valor na conta.                      |
|`/account/withdraw-value`   |  [`PUT`](#get-get-all)   |Saca um valor da conta.|
|`/account/transfer-value`   |  [`PUT`](#get-get-all)   |Transfere um valor de uma conta para outra.                         |



### Requisição /account/save
```json
{
    "cpf": "22222222222",
    "password": "222",
    "adress": "gui.andrade1510@gmail.com"
} 
```

### Requisição /account/make-deposit
```json
{
    "cpf": "22222222222",
    "password": "222",
    "value": 12.00
}
```

### Requisição /account/withdraw-valueRotas
```json
{
    "cpf": "22222222222",
    "password": "222",
    "value": 12.00
}
```

### Requisição /account/transfer-value

```json
{
    "cpf": "22222222222",
    "cpfToReceive": "12345678910",
    "password": "222",
    "value": 12.00
}
```

## Agradecimento

 Aproveite todas as funcionalidades e retorne um feedback para fazer um dev júnior feliz! 🌟