# Story 08: API contract and frontend alignment

**Status:** Implemented and verified

## Summary
As a frontend engineer and backend owner, I want a clear API contract so that public endpoints and admin endpoints are consistent, predictable, and easy to consume without ambiguity.

## Background
The backend is moving from a local prototype toward a real product. The API contract must be intentionally defined so that frontend development can proceed without repeated backend churn.

## User Story
As a consumer of the backend, I need a stable contract for response shapes, status codes, pagination, and naming conventions so that I can build predictable client code.

## Scope
In scope:
- public response object contract
- admin response contract
- pagination and filtering semantics
- naming consistency and canonical field names
- documentation for frontend integration

Out of scope:
- HTML page generation
- visual design
- frontend component implementation

## Functional Requirements
1. Public and admin APIs use consistent naming and structure.
2. Error responses are predictable, structured, and documented.
3. Pagination rules are explicit for list endpoints.
4. JSON fields use consistent casing, naming, and null-handling semantics.

## Technical Requirements
- Document the API contract in the repo.
- Ensure response payloads align with the frontend’s likely rendering needs.
- Use DTOs where responses should be intentionally shaped rather than exposing raw entities.
- avoid ambiguous field names and hidden assumptions.

## Acceptance Criteria
1. Public and admin endpoints have documented request/response structures.
2. Pagination semantics are clear and implemented consistently.
3. Error responses are standardized and documented.
4. Frontend and backend teams can consume the APIs without hidden conventions.

## Definition of Done
- Contract is documented and implemented.
- API consumers can build against the contract without guesswork.
- Review confirms request/response shapes are consistent and deliberate.

## Test Plan
### Unit tests
- should serialize public recruitment response with expected fields
- should serialize errors in a consistent format

### Integration tests
- response JSON matches API docs for all key endpoints
- pagination metadata follows the documented contract

## Code Review Checklist
- Are field names consistent and clear?
- Is pagination defined for all list endpoints?
- Are API contracts documented in the repo?
- Are there any accidental response leaks or inconsistent naming conventions?

## QA Notes
QA should validate the actual response payloads against the documented contract to ensure the backend remains predictable for downstream frontend or agent integrations.
