# API Contract

This document defines the contract for the government jobs backend so frontend and backend teams can work against a stable, predictable interface.

## Base conventions

- Base path: `/api`
- Public API prefix: `/api/public`
- Admin API prefix: `/api/admin`
- Response format: JSON
- Timestamp format: ISO-8601 UTC instant
- All list responses return a JSON array unless specifically documented otherwise
- Empty list responses return `200 OK` with `[]`

## Public recruitment endpoints

### 1. Get latest jobs

`GET /api/public/recruitments/latest?limit=10`

Request parameters:
- `limit` optional, default `10`, maximum returned items

Response: `200 OK`

```json
[
  {
    "id": 101,
    "title": "SSC CGL 2026",
    "slug": "ssc-cgl-2026",
    "status": "UPCOMING",
    "published": true,
    "featured": false,
    "organization": { "id": 1, "name": "Staff Selection Commission" },
    "state": { "id": 2, "name": "Kerala", "slug": "kerala" },
    "applicationEndDate": "2026-12-31",
    "categories": [
      { "id": 1, "name": "Banking", "slug": "banking" }
    ]
  }
]
```

### 2. Get recruitment by slug

`GET /api/public/recruitments/{slug}`

Response: `200 OK` for published and existing records
Response: `404 Not Found` for missing or non-published records

### 3. List categories

`GET /api/public/categories`

Response: `200 OK`

```json
[
  { "id": 1, "name": "Banking", "slug": "banking" },
  { "id": 2, "name": "Engineering", "slug": "engineering" }
]
```

### 4. List states

`GET /api/public/states`

Response: `200 OK`

```json
[
  { "id": 1, "name": "Kerala", "slug": "kerala" },
  { "id": 2, "name": "Delhi", "slug": "delhi" }
]
```

### 5. Filter public recruitments

`GET /api/public/recruitments?state=kerala&category=banking&limit=10`

Request parameters:
- `state` optional slug or name filter
- `category` optional slug or name filter
- `limit` optional, default `10`

Response: `200 OK` with matching published records only
Empty results return `[]`

## Admin recruitment endpoints

### Create recruitment

`POST /api/admin/recruitments`

Request body: `RecruitmentRequest`
Response: `201 Created` with created recruitment payload

### List recruitments

`GET /api/admin/recruitments?page=0&size=20`

Request parameters:
- `page` optional, default `0`
- `size` optional, default `20`

Response: `200 OK` with recruitment list payload

### Get recruitment by id

`GET /api/admin/recruitments/{id}`

Response: `200 OK` or `404 Not Found`

### Update recruitment

`PUT /api/admin/recruitments/{id}`

Response: `200 OK`

### Delete recruitment

`DELETE /api/admin/recruitments/{id}`

Response: `204 No Content`

## Standard error contract

All REST endpoints use the same structured error payload for application-level and validation failures.

Response format:

```json
{
  "timestamp": "2026-10-21T12:30:00Z",
  "status": 404,
  "error": "Not Found",
  "message": "Recruitment not found for slug: missing-slug",
  "path": "/api/public/recruitments/missing-slug"
}
```

Status code mapping:
- `400` bad request or validation error
- `404` not found
- `409` conflict on unique business rules
- `500` unexpected server error

## Naming standards

- JSON uses camelCase field names
- Slugs are normalized lowercase kebab-case values
- Dates use `YYYY-MM-DD` format
- API fields avoid leaking internal persistence-only concepts
- Public endpoints exclude admin-only fields and unpublished records

## Frontend guidance

- Cache public listings by slug and category/state filters
- Treat the `slug` as the canonical public identifier
- Use the error contract to render consistent user-facing validation and not-found states
- Prefer category and state filter params over direct data queries in the UI
