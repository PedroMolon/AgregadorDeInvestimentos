# Agregador de Investimentos API

![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue)
![Redis](https://img.shields.io/badge/Redis-7-red)
![Docker](https://img.shields.io/badge/Docker-blue)

API para agregação e gerenciamento de portfólios de investimentos, permitindo que usuários cadastrem contas e monitorem o valor de suas ações.

---

## 🚀 Funcionalidades

- **Gerenciamento de Usuários:** Cadastro e autenticação segura de usuários com JWT (JSON Web Tokens).
- **Contas de Investimento:** Criação de múltiplas contas por usuário para organizar os investimentos.
- **Portfólio de Ações:** Adição de ações (ações) a uma conta de investimento.
- **Cotações em Tempo Real:** Integração com a API [Brapi](https://brapi.dev/) para buscar cotações atualizadas das ações.
- **Cache de Performance:** Utilização de Redis para armazenar em cache as cotações, melhorando a performance e diminuindo a latência.
- **Documentação da API:** Geração automática de documentação com Springdoc (Swagger UI).

---

## 🛠️ Tecnologias Utilizadas

- **Backend:**
  - [Java 21](https://www.oracle.com/java/)
  - [Spring Boot](https://spring.io/projects/spring-boot)
  - [Spring Web](https://docs.spring.io/spring-framework/reference/web/webmvc.html)
  - [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
  - [Spring Security](https://spring.io/projects/spring-security)
- **Banco de Dados:**
  - [PostgreSQL](https://www.postgresql.org/)
  - [Flyway](https://flywaydb.org/) (para versionamento de schema)
- **Cache:**
  - [Redis](https://redis.io/)
- **Comunicação API:**
  - [OpenFeign](https://docs.spring.io/spring-cloud-openfeign/docs/current/reference/html/) (para chamadas à API Brapi)
- **Desenvolvimento:**
  - [Maven](https://maven.apache.org/)
  - [Lombok](https://projectlombok.org/)
  - [MapStruct](https://mapstruct.org/)
  - [Docker](https://www.docker.com/) & [Docker Compose](https://docs.docker.com/compose/)

---

## 📋 Pré-requisitos

Antes de começar, você vai precisar ter instalado em sua máquina:
- [JDK 21](https://www.oracle.com/java/technologies/downloads/#java21) ou superior.
- [Apache Maven](https://maven.apache.org/download.cgi) 3.8 ou superior.
- [Docker](https://www.docker.com/products/docker-desktop/) e [Docker Compose](https://docs.docker.com/compose/install/).

---

## ⚙️ Configuração e Execução

Siga os passos abaixo para executar o projeto localmente.

### 1. Clone o Repositório

```bash
git clone https://github.com/seu-usuario/AgregadorDeInvestimentos.git
cd AgregadorDeInvestimentos
```

### 2. Configure as Variáveis de Ambiente

A aplicação requer duas variáveis de ambiente para funcionar corretamente. Crie um arquivo `.env` na raiz do projeto ou configure as variáveis diretamente no seu sistema.

```env
# Chave secreta para a geração e validação dos tokens JWT.
# Use um valor longo e aleatório.
SECRET=SUA_CHAVE_SECRETA_AQUI

# Token de acesso para a API da Brapi.
# Obtenha seu token em https://brapi.dev/
TOKEN=SEU_TOKEN_DA_BRAPI_AQUI
```

O arquivo `docker-compose.yaml` está configurado para carregar o arquivo `.env` automaticamente.

### 3. Inicie os Serviços com Docker Compose

O `docker-compose.yaml` no projeto irá iniciar os contêineres do **PostgreSQL** e do **Redis** com as configurações necessárias (`ports`, `volumes`, `passwords`, etc.).

```bash
docker-compose up -d
```

Este comando irá baixar as imagens (se necessário) e iniciar os serviços em background.

### 4. Execute a Aplicação Spring Boot

Finalmente, execute a aplicação usando o Maven Wrapper.

```bash
./mvnw spring-boot:run
```

A API estará em execução e acessível em `http://localhost:8080`.

---

## 📚 Documentação da API (Swagger)

Com a aplicação em execução, você pode acessar a documentação interativa da API (Swagger UI) no seu navegador:

[**http://localhost:8080/swagger-ui.html**](http://localhost:8080/swagger-ui.html)

Lá você poderá visualizar todos os endpoints, seus DTOs (Data Transfer Objects) e testá-los diretamente.
