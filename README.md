<div align="center">

[![Português](https://img.shields.io/badge/Idioma-Portugu%C3%Aas-green.svg)](./README.md)
[![English](https://img.shields.io/badge/Language-English-blue.svg)](./README.en.md)
[![Español](https://img.shields.io/badge/Idioma-Espa%C3%B1ol-yellow.svg)](./README.es.md)

[![Release](https://img.shields.io/badge/release-v1.0.0-brightgreen?style=flat-square&logo=github)](../../releases/tag/v1.0.0)
![Status](https://img.shields.io/badge/status-planejado-blue?style=flat-square)
![License](https://img.shields.io/badge/license-MIT-green?style=flat-square)

</div>

# 🚘 AlgaTrânsito API — Sistema de Gestão de Veículos e Infração de Trânsito

Este repositório contém a **AlgaTrânsito API**, uma REST API desenvolvida para a gestão de veículos, proprietários e
autuações de trânsito, projetada para consolidar os fundamentos essenciais do ecossistema **Spring**, modelagem de APIs
RESTful profissionais e boas práticas de engenharia de software.

O projeto foi desenvolvido ao longo do treinamento **Ignição Spring REST** da **AlgaWorks**, que alinha o embasamento
teórico essencial à implementação prática imediata no código, focando na construção gradual de uma aplicação corporativa
segura, testável e desacoplada.

---

## 🎯 Sobre o Sistema e Conceitos-Chave

O **AlgaTrânsito** resolve os desafios do domínio de órgãos de trânsito, gerenciando o cadastro de proprietários,
registro de veículos, aplicação de autuações e controle do status operacional (como retenção/apreensão de veículos).

A aplicação aborda e implementa:

* **Modelagem REST e Boas Práticas HTTP:**
    * Nomeação semântica de URIs, uso correto dos verbos HTTP (GET, POST, PUT, DELETE), códigos de status adequados e
      suporte a *Content Negotiation*.
    * Padronização de datas/horas no formato ISO 8601 (`OffsetDateTime`).
    * Modelagem de sub-recursos (ex: `/veiculos/{id}/autuacoes`) e ações não-CRUD (ex: apreensão e liberação de
      veículos).
* **Camada de Persistência e Migrações:**
    * Mapeamento objeto-relacional (ORM) com **Jakarta Persistence (JPA)** e **Spring Data JPA**.
    * Evolução automatizada e versionada do schema do banco de dados utilizando **Flyway Migration**.
* **Validação de Dados e Tratamento de Erros:**
    * Validações declarativas e em cascata na entrada de dados com **Jakarta Bean Validation**.
    * Tratamento global e padronizado de exceções com `@RestControllerAdvice` e suporte à especificação **RFC 7807 (
      Problem Details for HTTP APIs)**.
* **Desacoplamento e DTOs (Representation Models):**
    * Isolamento entre o modelo de domínio (*Domain Model*) e o modelo de representação (*Representation Model / DTOs*)
      para entrada e saída de dados.
    * Mapeamento e transformação transparente de objetos utilizando **ModelMapper** e montadores dedicados (
      *Assemblers*).

---

## 📁 Estrutura do Projeto

```text
aw-transito-api/
├── backend/                  # Código-fonte da aplicação Java / Spring Boot (AlgaTrânsito)
├── .gitignore                # Regras de ignorados do Git (Java, IDEs, Maven, Node)
├── LICENSE                   # Licença do repositório (MIT)
├── README.md                 # Documentação principal (Português)
├── README.en.md              # Documentation (English)
└── README.es.md              # Documentación (Español)
```

---

## 🏛️ Arquitetura da Solução

```mermaid
graph TD
    Client[Cliente / Postman / Frontend] -->|Requisição HTTP| Controller[REST Controllers]
    Controller -->|DTO Input| Assembler[ModelMapper / Assembler]
    Controller -->|Executa Regra| Service[Domain Services]
    Service -->|Operações de Dados| Repository[Spring Data JPA Repositories]
    Repository -->|SQL Queries| DB[(Banco de Dados - MySQL)]
    
    Service -.->|Exceptions| ExceptionHandler[RestControllerAdvice - RFC 7807]
    ExceptionHandler -.->|ProblemDetail JSON| Client
```

### 📐 Diagrama de Classes do Domínio (UML)

```mermaid
classDiagram
    class Proprietario {
        +Long id
        +String nome
        +String email
        +String telefone
    }

    class Veiculo {
        +Long id
        +Proprietario proprietario
        +String marca
        +String modelo
        +String placa
        +StatusVeiculo status
        +OffsetDateTime dataCadastro
        +OffsetDateTime dataApreensao
        +List~Autuacao~ autuacoes
        +adicionarAutuacao()
        +apreender()
        +removerApreensao()
    }

    class Autuacao {
        +Long id
        +Veiculo veiculo
        +String descricao
        +BigDecimal valorMulta
        +OffsetDateTime dataOcorrencia
    }

    class StatusVeiculo {
        <<enumeration>>
        REGULAR
        APREENDIDO
    }

    Veiculo "0..*" --> "1" Proprietario : proprietario
    Veiculo "1" *-- "0..*" Autuacao : autuacoes
    Veiculo --> StatusVeiculo : status
```

### 🧩 Componentes do Ecossistema

* **`REST Controllers`:** Controladores responsáveis pela exposição dos endpoints da API, validação inicial e
  direcionamento de chamadas.
* **`Domain Services`:** Camada de serviço onde residem as regras de negócio puras do domínio de trânsito (ex:
  verificação de e-mail duplicado, apreensão de veículos).
* **`Spring Data JPA Repositories`:** Repositórios inteligentes para abstração total de comandos SQL e consultas
  refinadas (*Query Methods*).
* **`Flyway Migration`:** Ferramenta responsável por executar scripts SQL versionados para manter o banco de dados
  sincronizado.
* **`ModelMapper & Assemblers`:** Componentes encarregados de converter entidades de domínio em DTOs de representação e
  vice-versa.

---

## 🛠️ Tecnologias e Ferramentas

- **Linguagem & Framework:** Java 17+, Spring Boot 3+, Spring MVC
- **Build & Dependências:** Apache Maven
- **Persistência & Migração:** Jakarta Persistence (JPA), Spring Data JPA, Flyway Migration, MySQL
- **Validação & Produtividade:** Jakarta Bean Validation, Project Lombok, Spring Boot DevTools
- **Mapeamento & DTOs:** ModelMapper
- **Padronização & Tratamento de Erros:** RFC 7807 (Problem Details), ISO 8601 (`OffsetDateTime`)
- **Testes & Documentação de API:** Postman

<br />

<div align="center">

![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?style=flat-square&logo=openjdk&logoColor=white) 
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=flat-square&logo=spring&logoColor=white) ![Maven](https://img.shields.io/badge/Apache_Maven-Build-C71A36?style=flat-square&logo=apachemaven&logoColor=white) ![Spring Data JPA](https://img.shields.io/badge/Spring_Data_JPA-Persistence-6DB33F?style=flat-square&logo=spring&logoColor=white)  
![Flyway](https://img.shields.io/badge/Flyway-Database_Migrations-CC0200?style=flat-square&logo=flyway&logoColor=white) ![MySQL](https://img.shields.io/badge/MySQL-Database-4479A1?style=flat-square&logo=mysql&logoColor=white) ![Postman](https://img.shields.io/badge/Postman-API_Testing-FF6C37?style=flat-square&logo=postman&logoColor=white) ![Lombok](https://img.shields.io/badge/Lombok-Boilerplate_Reduction-red?style=flat-square)  
![RFC 7807](https://img.shields.io/badge/RFC_7807-Problem_Details-00599C?style=flat-square) ![Bean Validation](https://img.shields.io/badge/Bean_Validation-Jakarta-green?style=flat-square)

</div>

---

## 📌 Próximos Passos (Roadmap)

> 💡 **Nota sobre o escopo:** A versão **v1.0.0** consolida o *baseline* completo do treinamento **Ignição Spring REST**
> da AlgaWorks. As versões a partir da **v2.0.0** representam **expansões autorais e práticas extras**, implementadas
> por
> iniciativa própria para elevar a maturidade do projeto com testes automatizados, interface gráfica, documentação
> interativa e segurança.

| Versão                                                                                                                                                                                 | Descrição                                                                                                                          |
|:---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|:-----------------------------------------------------------------------------------------------------------------------------------|
| [![v1.0.0 Concluído](https://img.shields.io/badge/v1.0.0-Conclu%C3%ADdo-brightgreen?style=flat-square&logo=github)](https://github.com/ivanfsilva/aw-transito-api/releases/tag/v1.0.0) | **[Baseline AlgaWorks]** Entrega do ecossistema principal: CRUDs, Flyway, Bean Validation, ModelMapper, RFC 7807 e ações não-CRUD. |
| [![v2.0.0](https://img.shields.io/badge/v2.0.0-Futuro-lightgrey?style=flat-square)](../../releases/tag/v2.0.0)                                                                         | **[Prática Extra]** Suíte de testes automatizados (unitários e de integração) com JUnit 5, Mockito e REST Assured.                 |
| [![v3.0.0](https://img.shields.io/badge/v3.0.0-Futuro-lightgrey?style=flat-square)](../../releases/tag/v3.0.0)                                                                         | **[Prática Extra]** Interface web (Dashboard Front-end) para gestão e acompanhamento visual de veículos e autuações.               |
| [![v4.0.0](https://img.shields.io/badge/v4.0.0-Futuro-lightgrey?style=flat-square)](../../releases/tag/v4.0.0)                                                                         | **[Prática Extra]** Documentação interativa das rotas da API utilizando OpenAPI 3 / Swagger UI.                                    |
| [![v5.0.0](https://img.shields.io/badge/v5.0.0-Futuro-lightgrey?style=flat-square)](../../releases/tag/v5.0.0)                                                                         | **[Prática Extra]** Adição de camada de segurança, controle de acesso e autenticação com Spring Security e OAuth2 / JWT.           |

---


## 🍃 Sobre

Teoria e projeto prático desenvolvido durante o curso **Ignição Spring REST** da plataforma 🍃 **AlgaWorks**.

---

## 👤 Autor

Desenvolvido por **Ivan Ferreira**.

Copyright © 2026 Ivan Ferreira. Todos os direitos reservados.