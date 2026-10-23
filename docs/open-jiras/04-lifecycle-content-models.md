# Story 04: Lifecycle content domain models

**Status:** Implemented and verified

## Summary
As a product team, we need domain models for results, admit cards, answer keys, and other lifecycle records so that employment-related content can evolve beyond the base recruitment record.

## Background
The architecture states that recruitment content has a lifecycle: notification, application, result, admit card, answer key, and final outcome. The current backend only models the core recruitment record and its reference data.

## User Story
As an editor or product owner, I need lifecycle records tied to a recruitment so that I can publish associated notices and updates without mixing unrelated operational content with the base recruitment entity.

## Scope
In scope:
- domain entities for result, admit card, and answer key
- relationship mapping to recruitment
- migration scripts for new tables
- minimal repository and service layer scaffolding
- read endpoints for lifecycle records

Out of scope:
- advanced analytics or dashboard reporting
- third-party integrations
- notifications or email pipelines

## Functional Requirements
1. Each recruitment may have zero or more lifecycle records.
2. Lifecycle records must be clearly associated with a recruitment and have a publication state.
3. The data model supports an ordered, auditable lifecycle for a recruitment.
4. Public consumers can access published lifecycle data for a recruitment in a controlled fashion.

## Technical Requirements
- Keep lifecycle entities separate from the recruitment entity to preserve domain boundaries.
- Add migration scripts for new tables and indexes.
- Add repository interfaces and service methods for read and write operations.
- Follow the same validation and response conventions as the recruitment module.
- Avoid creating an overly generic “content” table that mixes unrelated record types.

## Acceptance Criteria
1. A recruitment can be linked to one or more result/admit card/answer key records.
2. These records are stored in dedicated tables, not as JSON blobs or ad-hoc columns.
3. Migration applies cleanly to an empty database and keeps indexes and constraints intact.
4. Lifecycle data can be queried by recruitment ID and publication status.
5. Unpublished lifecycle entries are not exposed through public APIs.

## Definition of Done
- Domain model is implemented with proper relationships.
- Database schema is versioned using Flyway.
- Tests cover persistence and retrieval behavior.
- Public/private access boundaries are enforced.

## Test Plan
### Unit tests
- should create a result linked to a recruitment
- should create admit card linked to a recruitment
- should reject missing required fields on lifecycle entities

### Integration tests
- migration creates lifecycle tables successfully
- `GET` endpoints for recruitment lifecycle return expected records
- unpublished records are hidden from public consumers

## Code Review Checklist
- Does the design keep lifecycle domains distinct and discoverable?
- Are constraints and indexes correctly defined?
- Is the database schema versioned and migration-safe?
- Are service methods following the project layering pattern?
- Is the public/private API split clear and intentional?

## QA Notes
QA should validate that lifecycle records can be stored and retrieved correctly and that only published records are visible in public responses.
