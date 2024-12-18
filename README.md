# TraceSuport

TraceSuport é um projeto desenvolvido na disciplina de Programação Web ministrada pelo Professor Gadelha. O foco principal do projeto é integrar o backend utilizando Spring Boot, o frontend com React, e o banco de dados MySQL.

## Tecnologias Utilizadas

- **Spring Boot** - Backend
- **React** - Frontend
- **MySQL** - Banco de Dados

## Funcionalidades

- Cadastro e autenticação de usuários.
- CRUD de entidades relevantes ao sistema.
- Integração entre o backend e o frontend para manipulação dos dados.
- Uso de mapas para rastreamento de chamados.

## Como Configurar e Executar

1. Clone este repositório:
   ```bash
   git clone https://github.com/edu-doc/TraceSuport.git
   ```
2. Acesse o diretório do projeto:
   ```bash
   cd TraceSuport
   ```
3. Configure as variáveis de ambiente no arquivo `application.properties`:
     ```properties
        spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/TraceSuport?createDatabaseIfNotExist=true&useSSL=false
        spring.datasource.username=seuUser
        spring.datasource.password=suaSenha
     ```
4. Inicie o backend: (Maven precisa estar instalado previamente)
   ```bash
    mvn spring-boot:run
   ```

## Estrutura do Projeto

```
|-- Spring-API/ -> MYSQL 
|     ↓    ↑
|-- React-frontend/
```

## Contato

- Equipe: Paulo Victor, Antonio Eduardo e Pedro Pereira
- GitHubs: https://github.com/PedroPereiraJPPF
           https://github.com/edu-doc
           https://github.com/paulovictor181

