# RestauranteApp Frontend

Frontend SPA em Angular 19 + TypeScript para o RestauranteApp Full Stack.

## Desenvolvimento

Requer Node.js 22 e npm. Com a API Spring Boot disponível em `http://localhost:8080`:

```bash
npm ci
npm start
```

Abra `http://localhost:4200`. O `proxy.conf.json` encaminha chamadas `/api` para o backend local.

## Build de produção

```bash
npm ci
npm run build
```

A aplicação é compilada para `dist/restauranteapp/browser` e servida por Nginx no Docker. As URLs de imagens do cardápio vêm dos dados retornados pela API.

## Integração

- `GET /api/products`: catálogo
- `GET /api/categories`: categorias
- `POST /api/orders`: envio de pedido
- `GET /api/orders/{id}`: consulta de pedido

O frontend envia somente IDs de produtos e quantidades; o backend valida preços e calcula o total.
