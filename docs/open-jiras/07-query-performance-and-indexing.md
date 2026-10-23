# Story 07: Query performance and indexing review

**Status:** Implemented and verified

## Summary
As a platform owner, I want the backend query and indexing strategy validated so that public listing and admin queries remain fast as the dataset grows.

## Background
The current schema is functional for MVP, but there is no formal performance validation for common query patterns such as latest jobs, category filtering, and join-heavy retrieval.

## User Story
As a system maintainer, I need performance validation and index review so that critical recruitment queries remain efficient as the dataset scales.

## Scope
In scope:
- review of indexes on recruitment and lookup tables
- review of query patterns for latest jobs and list filtering
- analysis of common join-table fetch patterns
- service-level optimization where needed

Out of scope:
- redesigning the database around a search engine
- complex analytics warehousing
- frontend caching optimization

## Functional Requirements
1. Latest and filtered recruitment queries are efficient for expected dataset sizes.
2. Indexes exist on the fields used in sorting and filtering.
3. The schema supports query patterns without excessive table scans.
4. Performance issues are documented when they are intentional trade-offs.

## Technical Requirements
- Review indexes for `published_at`, `status`, `slug`, and key foreign keys.
- Confirm `findByPublishedTrueOrderByPublishedAtDesc` is backed by appropriate indexing.
- Review `category` and `qualification` join operations for growth risks.
- Document which queries need future tuning or materialized caching.

## Acceptance Criteria
1. Database indexes match the critical query patterns used by the app.
2. Query performance is reviewed and measured on representative data sizes.
3. Any N+1 or expensive join pattern is identified and mitigated or documented.
4. The project contains a summary of indexing decisions for future maintainers.

## Definition of Done
- Index review is complete and reflected in the schema or migration strategy.
- Critical queries are validated against performance expectations.
- Documentation explains where future optimization may still be needed.

## Test Plan
### Unit tests
- repository method returns expected records for published latest query
- filter and sort methods remain stable under realistic sample data

### Integration tests
- query runtime is acceptable for representative seed data
- key indexes are present in the database schema

## Code Review Checklist
- Are indexes added only where they support known access patterns?
- Does the schema avoid redundant or conflicting indexes?
- Is query logic efficient and not prone to N+1 behavior?
- Is there a clear record of the performance assumptions?

## QA Notes
QA should focus on query correctness and on confirming the app remains stable under moderate test data volumes.
