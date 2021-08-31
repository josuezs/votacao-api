## Pré-requisitos

- Java 8+
- Banco PostgreSQL:
    - configurado na porta 5432;
    - com um database `votacao` vazio;
    - usuário e senha `teste` (caso deseje outro, altere em `application.properties`)
- Executar a classe `VotacaoApplication`.

## API

Para a documentação dos endpoins, acesse: http://localhost:8080/swagger-ui.html.

Optei em versionar a API com a estratégia `path`, onde na própria URL consta a versão da mesma. Vejo esta forma como
mais fácil pra quem for usar, pra saber em qual versão está usando e se quiser navegar entre elas ajuda também.

## Frameworks e Bibliotecas

- **Spring Boot**: Framework completo para desenvolvimento mais ágil.
- **Lombook**: Permite um código mais limpo, evitando erros.
- **Maven**: Para solução de dependências e build.
- **Hibernate**: Indispensável ter uma ferramenta ORM.
- **ModelMapper**: Facilita a conversão de DTOs para Objetos do banco, evitando expor desnecessariamente toda a
  estrutura da tabela.
- **Swagger**: Para documentação e inclusive testes dos endpoints.

## Banco

- Optei por mapear o id das tabelas com `BigInteger` para evitar uma limitação por parte do Java, assim depende apenas
  da capacidade do banco e memória do servidor.
- Na votação Sim/Não optei por armazenar em um inteiro (campo `ind_voto_sim`) a fim de permitir portabilidade para
  bancos sem tipo de dado booleano.
- Optei por criar as tabelas por script (arquivo `schema.sql`) ao invés de o próprio Hibernate criá-las (
  propriedade `ddl-auto`) para garantir que a estrutura do banco ficará conforme o esperado. Poderia ter sido utilizado
  também o `Liquibase` para criação da estrutura do banco.
