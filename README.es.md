<div align="center">

[![Português](https://img.shields.io/badge/Idioma-Portugu%C3%Aas-green.svg)](./README.md)
[![English](https://img.shields.io/badge/Language-English-blue.svg)](./README.en.md)
[![Español](https://img.shields.io/badge/Idioma-Espa%C3%B1ol-yellow.svg)](./README.es.md)

[![Release](https://img.shields.io/badge/release-v1.0.0-brightgreen?style=flat-square&logo=github)](../../releases/tag/v1.0.0)
![Status](https://img.shields.io/badge/status-planificado-blue?style=flat-square)
![License](https://img.shields.io/badge/license-MIT-green?style=flat-square)

</div>

# 🚘 AlgaTrânsito API — Sistema de Gestión de Vehículos e Infracciones de Tránsito

Este repositorio contiene la **AlgaTrânsito API**, una REST API desarrollada para la gestión de vehículos, propietarios
y multas de tránsito, diseñada para consolidar los fundamentos esenciales del ecosistema **Spring**, modelado de APIs
RESTful profesionales y buenas prácticas de ingeniería de software.

El proyecto fue desarrollado durante la capacitación **Ignição Spring REST** de **AlgaWorks**, alineando la base teórica
esencial con la implementación práctica inmediata en código, enfocándose en la construcción gradual de una aplicación
empresarial segura, testeable y desacoplada.

---

## 🎯 Sobre el Sistema y Conceptos Clave

**AlgaTrânsito** resuelve los desafíos del dominio de organismos de tránsito, gestionando el registro de propietarios,
inscripción de vehículos, aplicación de multas y control del estado operativo (como retención/embargo de vehículos).

La aplicación aborda e implementa:

* **Modelado REST y Buenas Prácticas HTTP:**
    * Nombramiento semántico de URIs, uso correcto de verbos HTTP (GET, POST, PUT, DELETE), códigos de estado adecuados
      y soporte para *Content Negotiation*.
    * Estandarización de fechas/horas en formato ISO 8601 (`OffsetDateTime`).
    * Modelado de subrecursos (ej: `/veiculos/{id}/autuacoes`) y acciones no-CRUD (ej: retención y liberación de
      vehículos).
* **Capa de Persistencia y Migraciones:**
    * Mapeo objeto-relacional (ORM) con **Jakarta Persistence (JPA)** y **Spring Data JPA**.
    * Evolución automatizada y versionada del esquema de base de datos mediante **Flyway Migration**.
* **Validación de Datos y Manejo de Errores:**
    * Validaciones declarativas y en cascada en la entrada de datos con **Jakarta Bean Validation**.
    * Manejo global y estandarizado de excepciones con `@RestControllerAdvice` y soporte para la especificación **RFC
      7807 (Problem Details for HTTP APIs)**.
* **Desacoplamiento y DTOs (Representation Models):**
    * Aislamiento entre el modelo de dominio (*Domain Model*) y el modelo de representación (*Representation Model /
      DTOs*) para entrada y salida de datos.
    * Mapeo y transformación transparente de objetos utilizando **ModelMapper** y ensambladores dedicados (
      *Assemblers*).

---

## 📁 Estructura del Proyecto

```text
aw-transito-api/
├── backend/                  # Código fuente de la aplicación Java / Spring Boot (AlgaTrânsito)
├── .gitignore                # Reglas de ignorados de Git (Java, IDEs, Maven, Node)
├── LICENSE                   # Licencia del repositorio (MIT)
├── README.md                 # Documentación principal (Portugués)
├── README.en.md              # Documentation (Inglés)
└── README.es.md              # Documentación (Español)
```

---

## 🏛️ Arquitectura de la Solución

```mermaid
graph TD
    Client[Cliente / Postman / Frontend] -->|Petición HTTP| Controller[REST Controllers]
    Controller -->|DTO Input| Assembler[ModelMapper / Assembler]
    Controller -->|Ejecuta Regla de Negocio| Service[Domain Services]
    Service -->|Operaciones de Datos| Repository[Spring Data JPA Repositories]
    Repository -->|Consultas SQL| DB[(Base de Datos - MySQL)]
    
    Service -.->|Exceptions| ExceptionHandler[RestControllerAdvice - RFC 7807]
    ExceptionHandler -.->|ProblemDetail JSON| Client
```

### 📐 Diagrama de Clases del Dominio (UML)

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

    Veiculo "0..*" --> "1" Proprietario : propietario
    Veiculo "1" *-- "0..*" Autuacao : multas
    Veiculo --> StatusVeiculo : estado
```

### 🧩 Componentes del Ecosistema

* **`REST Controllers`:** Controladores responsables de exponer los endpoints de la API, validación inicial y
  enrutamiento de peticiones.
* **`Domain Services`:** Capa de servicio donde residen las reglas de negocio puras del dominio de tránsito (ej:
  verificación de correo duplicado, retención de vehículos).
* **`Spring Data JPA Repositories`:** Repositorios inteligentes para abstracción total de comandos SQL y consultas
  refinadas (*Query Methods*).
* **`Flyway Migration`:** Herramienta responsable de ejecutar scripts SQL versionados para mantener la base de datos
  sincronizada.
* **`ModelMapper & Assemblers`:** Componentes encargados de convertir entidades de dominio en DTOs de representación y
  viceversa.

---

## 🛠️ Tecnologías y Herramientas

- **Lenguaje y Framework:** Java 17+, Spring Boot 3+, Spring MVC
- **Construcción y Dependencias:** Apache Maven
- **Persistencia y Migración:** Jakarta Persistence (JPA), Spring Data JPA, Flyway Migration, MySQL
- **Validación y Productividad:** Jakarta Bean Validation, Project Lombok, Spring Boot DevTools
- **Mapeo y DTOs:** ModelMapper
- **Estandarización y Manejo de Errores:** RFC 7807 (Problem Details), ISO 8601 (`OffsetDateTime`)
- **Pruebas y Documentación de API:** Postman

<br />

<div align="center">

![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?style=flat-square&logo=openjdk&logoColor=white) ![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=flat-square&logo=spring&logoColor=white) ![Maven](https://img.shields.io/badge/Apache_Maven-Build-C71A36?style=flat-square&logo=apachemaven&logoColor=white) ![Spring Data JPA](https://img.shields.io/badge/Spring_Data_JPA-Persistence-6DB33F?style=flat-square&logo=spring&logoColor=white)  
![Flyway](https://img.shields.io/badge/Flyway-Database_Migrations-CC0200?style=flat-square&logo=flyway&logoColor=white) ![MySQL](https://img.shields.io/badge/MySQL-Database-4479A1?style=flat-square&logo=mysql&logoColor=white) ![Postman](https://img.shields.io/badge/Postman-API_Testing-FF6C37?style=flat-square&logo=postman&logoColor=white) ![Lombok](https://img.shields.io/badge/Lombok-Boilerplate_Reduction-red?style=flat-square)  
![RFC 7807](https://img.shields.io/badge/RFC_7807-Problem_Details-00599C?style=flat-square) ![Bean Validation](https://img.shields.io/badge/Bean_Validation-Jakarta-green?style=flat-square)

</div>

---

## 📌 Próximos Pasos (Hoja de Ruta)

> 💡 **Nota sobre el alcance:** La versión **v1.0.0** consolida la línea base (*baseline*) completa de la capacitación *
*Ignição Spring REST** de AlgaWorks. Las versiones a partir de la **v2.0.0** representan **expansiones autorales y
prácticas extras**, implementadas por iniciativa propia para elevar la madurez del proyecto con pruebas automatizadas,
> interfaz gráfica, documentación interactiva y seguridad.

| Versión                                                                                                                                                                           | Descripción                                                                                                                          |
|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|:-------------------------------------------------------------------------------------------------------------------------------------|
| [![v1.0.0 Concluido](https://img.shields.io/badge/v1.0.0-Concluido-brightgreen?style=flat-square&logo=github)](https://github.com/ivanfsilva/aw-transito-api/releases/tag/v1.0.0) | **[Baseline AlgaWorks]** Entrega del ecosistema principal: CRUDs, Flyway, Bean Validation, ModelMapper, RFC 7807 y acciones no-CRUD. |
| [![v2.0.0](https://img.shields.io/badge/v2.0.0-Futuro-lightgrey?style=flat-square)](../../releases/tag/v2.0.0)                                                                    | **[Práctica Extra]** Suite de pruebas automatizadas (unitarias y de integración) con JUnit 5, Mockito y REST Assured.                |
| [![v3.0.0](https://img.shields.io/badge/v3.0.0-Futuro-lightgrey?style=flat-square)](../../releases/tag/v3.0.0)                                                                    | **[Práctica Extra]** Interfaz web (Dashboard Front-end) para la gestión y seguimiento visual de vehículos y multas.                  |
| [![v4.0.0](https://img.shields.io/badge/v4.0.0-Futuro-lightgrey?style=flat-square)](../../releases/tag/v4.0.0)                                                                    | **[Práctica Extra]** Documentación interactiva de rutas de la API utilizando OpenAPI 3 / Swagger UI.                                 |
| [![v5.0.0](https://img.shields.io/badge/v5.0.0-Futuro-lightgrey?style=flat-square)](../../releases/tag/v5.0.0)                                                                    | **[Práctica Extra]** Capa de seguridad, control de acceso y autenticación con Spring Security y OAuth2 / JWT.                        |

---

## 🍃 Sobre

Teoría y proyecto práctico desarrollado durante el curso **Ignição Spring REST** de la plataforma 🍃 **AlgaWorks**.

---

## 👤 Autor

Desarrollado por **Ivan Ferreira**.

Copyright © 2026 Ivan Ferreira. Todos los derechos reservados.

---

<div align="center">

[![Volver al README Principal](https://img.shields.io/badge/⬅️_Volver_al_README_Principal-PT--BR-brightgreen?style=flat-square)](./README.md)

</div>