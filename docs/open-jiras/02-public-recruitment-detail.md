# Story 02: Public recruitment detail endpoint

## Status
Implemented and verified. The public API now exposes a published recruitment detail endpoint by slug and returns 404 for missing or unpublished records.

## Summary
As a public user, I want to view a single recruitment detail page via the public API so that I can read the complete details for a specific job opening.

## Background
The project currently exposes a “latest jobs” feed but does not yet provide a detail endpoint. This is required before the frontend can render article-like pages or SEO-friendly detail screens.

## User Story
As a public visitor, I need a recruitment detail resource so that I can view all job requirements, dates, organization, and official links for a single advertisement.

## Scope
In scope:
- `GET /api/public/recruitments/{slug}`
- public response shape for a single recruitment
- 404 handling for missing records
- optional metadata fields returned by the public API

Out of scope:
- frontend templates
- lifecycle records attached to a recruitment
- pre-rendered HTML pages

## Functional Requirements
1. A public call by valid recruitment slug returns the complete recruitment record.
2. Only published records are returned in public detail responses.
3. Unpublished or deleted records are not exposed to the public API.
4. A missing slug returns a clean 404 response.
5. The response should align with the same domain model used in the public listing API.

## Technical Requirements
- Reuse the `RecruitmentService` pattern established in the project.
- Add a public DTO if required to avoid returning JPA entities directly.
- Add repository query for the exact published recruitment by slug.
- Ensure ordering and query behavior are deterministic.
- Document the public contract before frontend integration.

## Acceptance Criteria
1. `GET /api/public/recruitments/{slug}` returns 200 and the correct recruitment when the slug exists and is published.
2. `GET /api/public/recruitments/{slug}` returns 404 when the slug does not exist.
3. `GET /api/public/recruitments/{slug}` returns 404 or filtered result when the recruitment is unpublished.
4. The response contains the recruitment title, slug, organization, state, dates, description, official URLs, and related categories/qualifications.
5. The endpoint does not leak internal fields that are not intended for public consumers.
6. The endpoint follows the same conventions as the public latest list API regarding status codes and payload structure.

## Definition of Done
- The detail endpoint is in place and consistently documented.
- Security review confirms it stays public and non-admin.
- Tests cover success and not-found paths.
- Response payload is reviewed for data leakage.

## Test Plan
### Unit tests
- should fetch published recruitment by slug
- should reject unpublished record for public access
- should return empty/not-found result when slug does not exist

### Integration tests
- `GET /api/public/recruitments/{slug}` returns valid payload for known published record
- `GET /api/public/recruitments/unknown-slug` returns 404
- response contains all required fields and no admin-only fields

## Code Review Checklist
- Are public DTOs being used instead of raw entities?
- Is the repository query scoped to only published rows?
- Are there any accidental auth leaks or internal-only fields in the public response?
- Is error handling explicit and consistent?
- Is the endpoint easy for frontend consumers to use without domain confusion?

## QA Notes
QA should validate the response body and confirm that unpublished or missing records are not accessible through the public route.
