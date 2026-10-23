# Story 03: Search and listing APIs

**Status:** Implemented and verified

## Summary
As a public user, I want category, state, and filtered listing APIs so that I can discover relevant government jobs efficiently and the frontend can render rich index pages.

## Background
The project has a simple latest-jobs feed, but a production-ready job portal requires list and filter endpoints for key browsing patterns across job type, state, organization, and other metadata.

## User Story
As a public user, I need list and filter endpoints so that I can browse jobs by state, category, and other relevant dimensions without needing a database query directly.

## Scope
In scope:
- category listing endpoint
- state listing endpoint
- organization listing or detail endpoint
- filtered recruitments API by category/state
- optional sorting by closing date or newest published date

Out of scope:
- full-text search engine integration
- frontend rendering
- advanced analytics endpoints

## Functional Requirements
1. Public endpoint returns categories with metadata and published counts if needed.
2. Public endpoint returns states with published recruitment counts if needed.
3. Filtered recruitment queries support at least category and state filters.
4. Search/list endpoints return deterministic ordering and page metadata.
5. Empty results return a valid empty list response instead of server errors.

## Technical Requirements
- Define a consistent public response contract.
- Use repository query methods with pagination.
- Keep filtering logic in the service layer, not controller logic.
- Avoid N+1 queries when loading related categories or qualifications.
- Add query tests for the most common filters.

## Acceptance Criteria
1. `GET /api/public/categories` returns a list of categories available for public consumption.
2. `GET /api/public/states` returns a list of states available for public consumption.
3. `GET /api/public/recruitments?state=...&category=...` returns only matching published records.
4. An empty filter result returns an empty list and 200 response.
5. Sorting is stable and predictable across repeated calls.
6. Public endpoints do not expose admin-only fields or unpublished records.

## Definition of Done
- All required list endpoints exist and are covered by tests.
- Query behavior is validated for empty, single-result, and multi-result cases.
- Performance concerns for join-heavy queries are addressed or documented.

## Test Plan
### Unit tests
- should return filtered recruitments for category
- should return filtered recruitments for state
- should ignore unpublished records in list results
- should return empty list for non-matching filters

### Integration tests
- category endpoint returns list
- state endpoint returns list
- filter endpoint returns only matching recruitment records
- empty set response is valid JSON and non-error

## Code Review Checklist
- Are repository queries efficient and safe?
- Is pagination used when list size may grow?
- Are filter parameters validated and sanitized?
- Are public responses free from internal data?
- Is there any unnecessary eager loading or N+1 risk?

## QA Notes
QA should validate the list outputs against seed data and confirm that published-only results are returned while invalid or empty filter combinations behave predictably.
