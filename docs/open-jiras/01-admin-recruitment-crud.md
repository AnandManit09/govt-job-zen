# Story 01: Admin recruitment CRUD

## Status
Implemented and verified. The admin controller and service layer now support create, read, update, and delete operations for recruitment records.

## Summary
As an admin user, I want to read, update, and delete recruitment records through secure admin endpoints so that I can manage the content lifecycle without using direct database edits.

## Background
The project already supports admin creation of recruitment records. The next step is to complete the management lifecycle so the backend supports the full operational workflow for content editors.

## User Story
As a site administrator, I need CRUD operations for recruitments so that I can publish, update, archive, and remove jobs while preserving data integrity and security.

## Scope
In scope:
- `GET /api/admin/recruitments/{id}`
- `GET /api/admin/recruitments`
- `PUT /api/admin/recruitments/{id}`
- `DELETE /api/admin/recruitments/{id}`
- validation of request payloads
- safe update semantics for related entities
- audit-safe persistence behavior

Out of scope:
- frontend pages
- lifecycle domains such as result/admit card/answer key
- public search topics

## Functional Requirements
1. Admin can fetch a single recruitment by ID.
2. Admin can fetch a paginated list of recruitments with sorting and filtering support.
3. Admin can update an existing recruitment while preserving slug uniqueness and referential integrity.
4. Admin can delete a recruitment without breaking related database constraints.
5. Admin actions must require authenticated admin access.
6. Validation must reject invalid or incomplete requests cleanly with structured error responses.

## Technical Requirements
- Use the existing service/repository layering pattern.
- Keep DTOs separate from domain entities.
- Follow the repository pattern already established in the project.
- Prefer transactional updates for any mutation.
- Ensure duplicate category/qualification resolution logic is reused, not duplicated.
- Add proper error handling for not-found, validation, and constraint violations.

## Acceptance Criteria
1. An authenticated admin can fetch one recruitment by ID and receive a 200 response with expected fields.
2. An authenticated admin can fetch a list of recruitments and receive ordered, paginated output.
3. An authenticated admin can update an existing recruitment and receive the modified record.
4. A recruitment that does not exist returns a deterministic 404 response.
5. A request with invalid dates or required fields returns a 400 response.
6. A delete action removes the record from the database and returns a successful status code.
7. The API behaves consistently under repeated writes without duplicate entity creation.
8. Security is enforced at the controller layer and the route remains protected.

## Definition of Done
- Implementation is complete and merged in a reviewable change set.
- All stories in this task are covered by unit/integration tests.
- Code review checklist has been satisfied.
- Error responses are consistent with the rest of the backend.

## Test Plan
### Unit tests
- should return recruitment when valid ID is provided
- should throw a domain-specific exception or return 404 when record is missing
- should resolve category and qualification references correctly during update
- should reject invalid payloads before hitting repository layer

### Integration tests
- `GET /api/admin/recruitments/{id}` returns 200 for a valid admin session
- `GET /api/admin/recruitments` returns ordered list
- `PUT /api/admin/recruitments/{id}` updates title, dates, status, published flag
- `DELETE /api/admin/recruitments/{id}` removes the record
- unauthorized user gets 401/403

## Code Review Checklist
- Does the story follow layered architecture without leaking JPA concerns into controllers?
- Are DTOs used correctly and validation annotations present?
- Are transactions used for mutating operations?
- Are edge cases such as missing records and invalid IDs handled explicitly?
- Is the code secure and not vulnerable to common injection or overbroad access issues?

## QA Notes
QA should verify both happy-path and negative-path behaviors. Focus on authorization, payload validation, response structure, and database state after update and delete calls.
