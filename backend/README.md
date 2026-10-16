# 🍽️ RestauranteApp API

API REST do **RestauranteApp**, desenvolvida com Java e Spring Boot para fornecer produtos, categorias e criação/consulta de pedidos. O projeto utiliza MySQL para persistência e Docker Compose para facilitar a execução do ambiente local.

> **Status:** projeto inicial para desenvolvimento e integração com o frontend Angular. Ainda não inclui autenticação, pagamento, cálculo de entrega ou painel administrativo.

## 📌 Sumário

- [Funcionalidades](#-funcionalidades)
- [Tecnologias](#-tecnologias)
- [Arquitetura do projeto](#-arquitetura-do-projeto)
- [Pré-requisitos](#-pré-requisitos)
- [Como executar com Docker](#-como-executar-com-docker-recomendado)
- [Configuração de ambiente](#-configuração-de-ambiente)
- [Documentação e monitoramento](#-documentação-e-monitoramento)
- [Endpoints da API](#-endpoints-da-api)
- [Exemplos de requisição](#-exemplos-de-requisição)
- [Integração com Angular](#-integração-com-angular)
- [Executar sem Docker](#-executar-sem-docker)
- [Boas práticas e próximos passos](#-boas-práticas-e-próximos-passos)

## ✨ Funcionalidades

- Listagem de produtos ativos.
- Busca de produtos por nome e descrição.
- Filtro de produtos por categoria.
- Listagem das categorias disponíveis.
- Consulta de produto por identificador.
- Criação de pedidos com um ou mais itens.
- Cálculo do total no servidor, usando os preços armazenados no banco.
- Validação de dados de entrada e de quantidades.
- Persistência de pedidos e itens, incluindo o preço unitário registrado no momento da compra.
- Dados de demonstração inseridos na inicialização.
- Endpoint de saúde da API e documentação OpenAPI/Swagger.

## 🧰 Tecnologias

| Tecnologia | Utilização |
|---|---|
| Java 21 | Linguagem |
| Spring Boot 3.4.5 | Aplicação e API REST |
| Spring Web | Endpoints HTTP |
| Spring Data JPA / Hibernate | Persistência e mapeamento objeto-relacional |
| Bean Validation | Validação de requisições |
| MySQL 8.4 | Banco de dados relacional |
| Maven | Dependências e build |
| Docker e Docker Compose | Ambiente de execução |
| Springdoc OpenAPI | Documentação interativa da API |
| Spring Boot Actuator | Monitoramento básico |

## 🗂️ Arquitetura do projeto

```text
RestauranteApp-Backend-Java/
├── src/
│   ├── main/
│   │   ├── java/br/com/restauranteapp/
│   │   │   ├── category/       # Consulta de categorias
│   │   │   ├── config/         # Configurações, incluindo CORS
│   │   │   ├── health/         # Verificação de saúde
│   │   │   ├── order/          # Pedidos, itens e regras de negócio
│   │   │   ├── product/        # Produtos e consultas
│   │   │   └── RestauranteAppApplication.java
│   │   └── resources/
│   │       └── application.yml
│   └── test/
├── .env.example
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md
```

## ✅ Pré-requisitos

Para executar com Docker:

- [Docker Desktop](https://www.docker.com/products/docker-desktop/) instalado e em execução.
- Docker Compose disponível pelo comando `docker compose`.

Para executar localmente sem Docker, também será necessário Java 21 e Maven. O projeto já inclui o `pom.xml`.

## 🐳 Como executar com Docker (recomendado)

1. Baixe ou clone o repositório e abra o terminal na pasta que contém `docker-compose.yml`.
2. Opcionalmente, crie o arquivo `.env` a partir do modelo:

   **Windows PowerShell**
   ```powershell
   Copy-Item .env.example .env
   ```

   **Linux/macOS**
   ```bash
   cp .env.example .env
   ```

3. Inicie a aplicação e o banco de dados:

   ```bash
   docker compose up --build
   ```

4. Quando os serviços estiverem prontos, acesse:

    - API: <http://localhost:8080>
    - Swagger UI: <http://localhost:8080/swagger-ui/index.html>
    - OpenAPI JSON: <http://localhost:8080/v3/api-docs>
    - Saúde da aplicação: <http://localhost:8080/api/health>
    - Actuator: <http://localhost:8080/actuator/health>

O Compose aguarda o healthcheck do MySQL antes de iniciar a API. Na primeira execução, o Docker precisa baixar as imagens e compilar o projeto, então a inicialização pode levar alguns minutos.

### Comandos úteis

```bash
# Ver logs dos serviços
docker compose logs -f

# Ver apenas os logs da API
docker compose logs -f api

# Verificar os serviços
docker compose ps

# Parar os serviços sem apagar os dados do banco
docker compose down

# Parar os serviços e apagar também o volume do banco (ação destrutiva)
docker compose down -v
```

> **Atenção:** `docker compose down -v` remove o volume persistente do MySQL e apaga os dados armazenados nele.

## ⚙️ Configuração de ambiente

O arquivo `.env.example` contém os valores usados no desenvolvimento local:

| Variável | Padrão | Descrição |
|---|---|---|
| `MYSQL_DATABASE` | `restaurante` | Banco utilizado pela API |
| `MYSQL_USER` | `restaurante_app` | Usuário da aplicação no MySQL |
| `MYSQL_PASSWORD` | `change_me_local` | Senha de desenvolvimento |
| `MYSQL_ROOT_PASSWORD` | `change_me_root` | Senha root do MySQL |
| `MYSQL_PORT` | `3307` | Porta do MySQL exposta no computador |
| `APP_PORT` | `8080` | Porta HTTP da API |
| `APP_CORS_ORIGINS` | `http://localhost:4200` | Origens autorizadas pelo CORS |

As portas externas podem ser alteradas no `.env` caso já estejam sendo utilizadas por outros programas. Dentro da rede Docker, a API conecta ao serviço MySQL pela porta `3306`; não altere essa porta interna sem atualizar a configuração.

**Segurança:** as credenciais padrão são apenas para desenvolvimento. Não publique senhas, chaves ou arquivos `.env` com credenciais reais no Git.

## 📚 Documentação e monitoramento

Com a aplicação em execução:

- **Swagger UI:** <http://localhost:8080/swagger-ui/index.html>
- **Especificação OpenAPI:** <http://localhost:8080/v3/api-docs>
- **Health da API:** <http://localhost:8080/api/health>
- **Actuator Health:** <http://localhost:8080/actuator/health>

O endpoint `/api/health` retorna um status simples da aplicação. O Actuator disponibiliza a verificação de saúde configurada no projeto.

## 🔌 Endpoints da API

Base URL local: `http://localhost:8080`

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/api/health` | Verifica se a API responde |
| `GET` | `/api/categories` | Lista categorias de produtos ativos |
| `GET` | `/api/products` | Lista produtos ativos |
| `GET` | `/api/products?category=Hambúrgueres` | Filtra por categoria |
| `GET` | `/api/products?search=burger` | Busca por texto |
| `GET` | `/api/products/{id}` | Consulta um produto ativo |
| `POST` | `/api/orders` | Cria um pedido |
| `GET` | `/api/orders/{id}` | Consulta um pedido |

### Observações sobre os endpoints

- Os parâmetros `category` e `search` são opcionais e podem ser combinados.
- Os identificadores `{id}` são substituídos pelo ID real do produto ou pedido.
- O `POST /api/orders` retorna o pedido criado com status HTTP `201 Created`.
- O comportamento de erros e os campos de resposta devem ser conferidos na documentação Swagger enquanto a API estiver em execução.

## 🧪 Exemplos de requisição

### Listar produtos

```bash
curl http://localhost:8080/api/products
```

### Filtrar por categoria

```bash
curl --get "http://localhost:8080/api/products" --data-urlencode "category=Hambúrgueres"
```

### Buscar produtos

```bash
curl --get "http://localhost:8080/api/products" --data-urlencode "search=burger"
```

### Criar um pedido

```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerName": "Cliente Exemplo",
    "customerEmail": "cliente@example.com",
    "items": [
      { "productId": 1, "quantity": 2 },
      { "productId": 6, "quantity": 1 }
    ]
  }'
```

No PowerShell, se o comando `curl` apresentar comportamento diferente, use o Swagger UI ou `Invoke-RestMethod`.

A API consulta os produtos no banco e calcula os valores no servidor. Portanto, o frontend não deve enviar nem definir o preço final do pedido. Os IDs usados no exemplo dependem dos dados disponíveis no banco.

### Consultar um pedido

Substitua `1` pelo ID retornado na criação:

```bash
curl http://localhost:8080/api/orders/1
```

## 🅰️ Integração com Angular

A origem padrão liberada pelo CORS é `http://localhost:4200`, porta usual do servidor de desenvolvimento Angular.

Exemplo simples de serviço Angular:

```typescript
import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Product {
  id: number;
  name: string;
  description?: string;
  price: number;
  category: string;
  imageUrl?: string;
  active: boolean;
}

@Injectable({ providedIn: 'root' })
export class ProductService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = 'http://localhost:8080/api/products';

  getProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(this.apiUrl);
  }
}
```

> Ajuste as propriedades da interface TypeScript conforme o JSON real retornado pela entidade `Product`. Para enviar pedidos, crie um serviço equivalente que faça `POST` para `http://localhost:8080/api/orders`.

Garanta também que o `HttpClient` esteja habilitado na configuração do Angular. Em aplicações standalone, normalmente isso é feito com `provideHttpClient()` em `app.config.ts`.

## 💻 Executar sem Docker

Esta opção pressupõe que Java 21, Maven e um servidor MySQL estejam instalados localmente.

1. Crie um banco de dados chamado `restaurante` e um usuário com permissões adequadas.
2. Configure as variáveis de ambiente para apontar para o banco local. Por exemplo, ajuste `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME` e `SPRING_DATASOURCE_PASSWORD`.
3. Na raiz do projeto, execute:

   ```bash
   mvn spring-boot:run
   ```

A API será iniciada na porta `8080`, salvo se `SERVER_PORT` estiver configurada com outro valor.

## 🛡️ Boas práticas e próximos passos

Antes de usar o sistema em produção, considere:

- Adicionar autenticação e autorização com Spring Security.
- Criar migrações versionadas com Flyway ou Liquibase, em vez de depender de `ddl-auto: update`.
- Validar e padronizar os erros HTTP com respostas consistentes.
- Criar testes unitários e de integração para produtos e pedidos.
- Configurar logs, métricas, HTTPS e gestão segura de segredos.
- Adicionar controle administrativo para cadastro e edição de produtos.
- Integrar pagamentos e regras de entrega somente com validações e provedores apropriados.
- Configurar pipeline CI para compilar e testar a aplicação a cada pull request.

## 👤 Projeto

**RestauranteApp API** — backend de estudo e portfólio para praticar desenvolvimento de APIs REST com Java, Spring Boot, MySQL e Docker.

---

Se encontrar um problema, confira primeiro os logs com `docker compose logs -f` e valide se as portas `8080` e `3307` estão disponíveis.
