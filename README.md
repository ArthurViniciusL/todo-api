# 🚀 To-Do API

-----

## 📋 Propósito do Projeto

Este projeto é uma **API RESTful** simples para gerenciamento de **Tarefas (Tasks)** e **Pessoas (Persons)**. Ele foi desenvolvido como um exercício prático e didático para explorar e aplicar os conceitos de uma arquitetura baseada em **Spring Boot**, seguindo o padrão **MVC (Model-View-Controller)** e a **arquitetura em camadas** (Entidade, Repositório, Serviço e Controlador).

O principal objetivo é demonstrar:

  * A separação de responsabilidades entre as camadas.
  * Operações CRUD (Create, Read, Update, Delete) para diferentes entidades.
  * Mapeamento de relacionamentos complexos, como o de **Muitos para Muitos (N:N)** entre `Person` e `Task`.
  * Uso de um banco de dados em memória para desenvolvimento ágil.

-----

## 🛠️ Tecnologias Utilizadas

O projeto utiliza as seguintes tecnologias e ferramentas principais:

  * **Java 17+:** A linguagem de programação base do projeto.
  * **Spring Boot 3.x:** Framework para desenvolvimento rápido e fácil de aplicações Spring, com foco em microserviços e APIs REST.
      * **Spring Web:** Módulo para construção de APIs RESTful.
      * **Spring Data JPA:** Simplifica a interação com o banco de dados, fornecendo implementações de repositórios prontas e gerenciamento de entidades.
      * **Spring Boot DevTools:** Ferramentas que aceleram o ciclo de desenvolvimento (ex: reinício automático).
  * **Hibernate:** Implementação padrão da JPA utilizada pelo Spring Data JPA para o mapeamento Objeto-Relacional (ORM).
  * **H2 Database:** Banco de dados relacional em memória (padrão) para desenvolvimento. Leve e fácil de usar, ideal para testes e prototipagem.
  * **Lombok:** Biblioteca que reduz a verbosidade do código Java, gerando automaticamente getters, setters, construtores e outros métodos através de anotações.
  * **Maven:** Ferramenta de automação de *build* e gerenciamento de dependências.
  * **Git:** Sistema de controle de versão.
  * **.http Client (IntelliJ IDEA):** Ferramenta integrada à IDE para testar endpoints HTTP diretamente.

-----

## 🚀 Como Rodar o Projeto

1.  **Clone o Repositório:**
    ```bash
    git clone https://github.com/seu-usuario/todo.git # Substitua pelo seu usuário/link
    cd todo
    ```
2.  **Abra na IDE:** Importe o projeto no IntelliJ IDEA, Eclipse ou VS Code como um projeto Maven.
3.  **Execute a Aplicação:**
      * Execute a classe principal `TodoApplication.java`.
      * A aplicação será iniciada na porta `8080` por padrão.

-----

## 🧪 Testando a API

Você pode testar os endpoints da API utilizando o arquivo `requests.http` localizado na raiz do projeto (se estiver usando o IntelliJ IDEA ou VS Code com extensões HTTP Client).

**Exemplos de Endpoints:**

  * **Pessoas (Persons):**

      * `GET /api/persons`: Listar todas as pessoas.
      * `POST /api/persons`: Criar uma nova pessoa.
      * `GET /api/persons/{id}`: Obter pessoa por ID.
      * `PUT /api/persons/{id}`: Atualizar pessoa.
      * `DELETE /api/persons/{id}`: Deletar pessoa.

  * **Tarefas (Tasks):**

      * `GET /api/tasks`: Listar todas as tarefas.
      * `POST /api/tasks`: Criar uma nova tarefa.
      * `GET /api/tasks/{uuid}`: Obter tarefa por UUID.
      * `PUT /api/tasks/{uuid}`: Atualizar tarefa.
      * `DELETE /api/tasks/{uuid}`: Deletar tarefa.

  * **Relacionamento Person-Task (Muitos para Muitos):**

      * `GET /api/persons/{personId}/tasks`: Listar todas as tarefas atribuídas a uma pessoa.
      * `POST /api/persons/{personId}/tasks/{taskId}`: Atribuir uma tarefa a uma pessoa.
      * `DELETE /api/persons/{personId}/tasks/{taskId}`: Remover uma tarefa de uma pessoa.

-----

## 🤝 Contribuição

Contribuições são bem-vindas\! Sinta-se à vontade para abrir *issues* ou *pull requests*.

-----

-----

O que achou deste `README.md`? Ele aborda bem os pontos que você queria?
