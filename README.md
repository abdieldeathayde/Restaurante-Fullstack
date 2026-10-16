# RestauranteApp — Full Stack

Aplicação de cardápio e pedidos com **Angular 19**, **Java 21**, **Spring Boot 3.4**, **Spring Data JPA**, **MySQL 8.4** e **Docker Compose**. O projeto reúne frontend e backend em um único repositório, com serviços separados e comunicação via API REST.

## Arquitetura

```text
frontend/  Angular + TypeScript + Nginx
    |  /api/* (proxy reverso)
    v
backend/   Spring Boot REST API
    |
    v
MySQL 8.4 (volume persistente)
```

## Executar com Docker

Pré-requisitos: Docker Desktop com o mecanismo Linux ativo e Docker Compose v2.

1. Na raiz do projeto, crie seu arquivo de ambiente:

   **PowerShell**
   ```powershell
   Copy-Item .env.example .env
   ```

2. Edite `.env` e defina senhas próprias.
3. Construa e inicie todos os serviços:

   ```bash
   docker compose up --build -d
   ```

4. Acesse:

   - Frontend: http://localhost:8081
   - API: http://localhost:8080
   - Saúde da API: http://localhost:8080/actuator/health
   - Swagger UI: http://localhost:8080/swagger-ui/index.html
   - MySQL: `localhost:3307` (somente para ferramentas locais)

Para acompanhar a inicialização: `docker compose logs -f api`. A primeira inicialização pode demorar enquanto o banco e as imagens são preparados.

## Executar em desenvolvimento

### Backend

Pré-requisitos: JDK 21, Maven (ou Maven Wrapper, se incluído) e MySQL 8.4.

1. Inicie apenas o banco:
   ```bash
   docker compose up -d mysql
   ```
2. Entre em `backend/` e execute:
   ```bash
   mvn spring-boot:run
   ```

A API ficará em `http://localhost:8080`. A configuração padrão espera o MySQL em `localhost:3307`. Credenciais podem ser substituídas por variáveis de ambiente.

### Frontend

Pré-requisitos: Node.js 22 e npm.

```bash
cd frontend
npm ci
npm start
```

O Angular fica em `http://localhost:4200`; o proxy de desenvolvimento encaminha `/api` para `http://localhost:8080`.

## API REST

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/api/products` | Lista produtos ativos |
| `GET` | `/api/products/{id}` | Busca produto ativo |
| `GET` | `/api/categories` | Lista categorias disponíveis |
| `POST` | `/api/orders` | Cria pedido e calcula o total no servidor |
| `GET` | `/api/orders/{id}` | Consulta pedido |
| `GET` | `/actuator/health` | Estado de saúde da API |
| `GET` | `/swagger-ui/index.html` | Documentação interativa |

O checkout envia nome, e-mail, IDs de produtos e quantidades. O backend valida os dados, consulta preços no banco e calcula o total no servidor — o frontend não é fonte confiável para preços.

## Estrutura do repositório

```text
.
├── backend/        # API Spring Boot, JPA e MySQL
├── frontend/       # SPA Angular
├── docker-compose.yml
├── .env.example
└── README.md
```

## Observações de segurança e produção

- Troque todas as senhas de exemplo e não versione `.env`.
- O backend atual usa `spring.jpa.hibernate.ddl-auto=update` para facilitar desenvolvimento; em produção, prefira migrações versionadas (Flyway/Liquibase).
- O projeto não implementa autenticação de clientes, pagamento, cálculo de entrega nem painel administrativo.
- Configure HTTPS, gestão de segredos, backups, limites de requisição e monitoramento antes de publicar.
- A criação de pedido usa prompts simples no navegador; substitua por um formulário acessível com validação visual para uma experiência de produção.
- A API usa CORS configurável; no Docker, o frontend acessa a API pelo proxy Nginx no mesmo host.

## Verificações

```bash
# Backend (na pasta backend)
mvn test

# Frontend (na pasta frontend)
npm ci
npm run build
```

## Licença

Adicione um arquivo de licença antes de distribuir o projeto, se aplicável.
