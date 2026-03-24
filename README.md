# 💰 Agregador de Investimentos API

[![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0-brightgreen?style=for-the-badge&logo=springboot)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?style=for-the-badge&logo=postgresql)](https://www.postgresql.org/)
[![Redis](https://img.shields.io/badge/Redis-7-red?style=for-the-badge&logo=redis)](https://redis.io/)
[![Docker](https://img.shields.io/badge/Docker-Container-blue?style=for-the-badge&logo=docker)](https://www.docker.com/)

A **Agregador de Investimentos API** é uma solução moderna para a gestão centralizada de portfólios de investimentos, permitindo que usuários monitorem em tempo real o valor de suas ações através da integração com APIs do mercado financeiro.

---

## 🚀 Tecnologias e Ferramentas

O projeto utiliza uma stack focada em performance e escalabilidade financeira:

* **Linguagem:** Java 21 (LTS)
* **Framework:** Spring Boot 4.0.0
* **Cache:** **Redis** (para armazenamento em cache de cotações e performance)
* **Comunicação API:** **OpenFeign** (integração com a API Brapi para cotações)
* **Persistência & ORM:** Spring Data JPA e Hibernate
* **Banco de Dados:** PostgreSQL 16
* **Migrações de Banco:** Flyway
* **Segurança:** Spring Security com autenticação JWT
* **Mapeamento de Objetos:** MapStruct e Lombok
* **Conteinerização:** Docker e Docker Compose

---

## 🛠️ Funcionalidades Principais

* **Gerenciamento de Usuários:** Cadastro e autenticação segura com tokens JWT.
* **Cotações em Tempo Real:** Busca automatizada de preços de ações via API [Brapi](https://brapi.dev/).
* **Cache Inteligente:** Utilização de Redis para minimizar chamadas externas e reduzir latência.
* **Portfólio de Ações:** Adição de ativos a contas específicas para monitoramento.
* **Documentação Interativa:** Geração automática com Springdoc (Swagger UI).

### 🛡️ Segurança com JWT (Stateless)
O projeto implementa uma camada robusta de segurança financeira:
* **Autenticação via JWT:** Tokens seguros para autenticação sem estado.
* **Proteção de Endpoints:** Controle de acesso granular baseado em permissões de usuário.
* **Criptografia:** Armazenamento seguro de senhas com BCrypt.

---

## 📂 Arquitetura do Projeto

O projeto é estruturado seguindo os princípios de camadas para garantir separação de responsabilidades:

1.  **Controller:** Camada de entrada, validação de DTOs e orquestração de respostas REST.
2.  **Service:** Onde reside toda a lógica de negócio financeira e de investimentos.
3.  **Client:** Camada de integração externa (Brapi via Feign).
4.  **Repository:** Acesso aos dados persistentes no PostgreSQL.
5.  **Mapper:** Conversão eficiente entre Entidades JPA e DTOs.

---

## ⚙️ Como Executar o Projeto

### Pré-requisitos
* Docker e Docker Compose instalados.
* JDK 21 ou superior.
* Uma chave de API da **Brapi** ([Brapi Dev](https://brapi.dev/)).

### Passo a Passo

1. **Clone o repositório:**
```bash
git clone https://github.com/PedroMolon/AgregadorDeInvestimentos.git
cd AgregadorDeInvestimentos
```

2. **Configure as variáveis de ambiente:**
   Crie um arquivo `.env` na raiz do projeto:
```env
SECRET=SUA_CHAVE_SECRETA_JWT
TOKEN=SEU_TOKEN_DA_BRAPI
```

3. **Inicie os serviços (PostgreSQL e Redis) com Docker Compose:**
```bash
docker-compose up -d
```

4. **Execute a aplicação:**
```bash
./mvnw spring-boot:run
```

---

## 📝 Documentação da API (Endpoints Principais)

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `POST` | `/auth/login` | Autenticação e geração de token JWT |
| `GET` | `/stocks` | Consulta de cotações de ações |
| `GET` | `/users` | Gerenciamento de usuários e contas |
| `GET` | `/swagger-ui.html` | Documentação Swagger completa |

---

## 👨‍💻 Autor

Desenvolvido com ☕ por **Pedro Molon**.
Conecte-se comigo no [LinkedIn](https://www.linkedin.com/in/pedromolon/) ou veja outros projetos no meu [GitHub](https://github.com/PedroMolon).
