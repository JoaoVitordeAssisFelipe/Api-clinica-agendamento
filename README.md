# 🏥 Sistema de Gestão de Clínica (API)

Este projeto é uma **API REST** desenvolvida em **Java com Spring Boot** para gerenciar os dados fundamentais de uma clínica médica. O sistema permite o cadastro e gestão de pacientes, profissionais de saúde e prontuários médicos, com foco na integridade dos dados e boas práticas de desenvolvimento.

## 🚀 Tecnologias Utilizadas

* **Java 17**
* **Spring Boot 3** (Web, Data JPA, Validation)
* **Banco de Dados** (H2 / MySQL / PostgreSQL)
* **Lombok** (Para redução de código boilerplate)
* **JUnit 5 & Mockito** (Testes Unitários)
* **Maven** (Gerenciamento de dependências)

## ⚙️ Funcionalidades

### 1. Gestão de Pacientes
* Cadastro completo com dados pessoais e endereço.
* Atualização cadastral inteligente (atualiza ou cria endereço conforme necessário).
* Busca por ID e listagem geral.
* Exclusão lógica/física com validação de existência.

### 2. Gestão de Profissionais
* Cadastro de médicos e profissionais com Registro Profissional (CRM).
* Vinculação de endereço.
* Validação de persistência no banco de dados.

### 3. Prontuário Médico
* Relacionamento **1:1** com Pacientes.
* Histórico evolutivo: Suporte a múltiplas observações (`List<String>`) usando `@ElementCollection`.
* Estrutura otimizada para armazenar notas clínicas.

## 🏗️ Arquitetura do Projeto

O projeto segue a arquitetura em camadas padrão do Spring Boot para garantir a separação de responsabilidades:

1.  **DTO (Data Transfer Object):** Define os contratos de entrada e saída da API, protegendo a camada de domínio.
2.  **Service:** Contém as regras de negócio, validações (ex: verificar se ID existe) e conversão de dados.
3.  **Repository:** Camada de persistência responsável pela comunicação direta com o banco de dados (JPA/Hibernate).
4.  **Model (Entity):** Representação das tabelas do banco de dados e seus relacionamentos.

### Diagrama de Classes Simplificado

```mermaid
classDiagram
    class Paciente {
        Long id
        String nome
        String cpf
        Endereco endereco
    }
    class Endereco {
        String logradouro
        String cidade
        String estado
    }
    class Profissional {
        Long id
        String registroMedico
        String nome
        Endereco endereco
    }
    class Prontuario {
        Long id
        List~String~ observacoes
        Paciente paciente
    }

    Paciente *-- Endereco : Possui
    Profissional *-- Endereco : Possui
    Prontuario --> Paciente : Pertence a (1:1)
