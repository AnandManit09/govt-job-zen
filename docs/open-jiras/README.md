# Open Jira Stories

This folder contains the remaining implementation work as Jira-style stories for agent execution.

Each story is written to be actionable, testable, and reviewable. They are designed so that:

- a backend engineer can implement one story independently
- a QA agent can validate acceptance criteria against the running app
- a code review agent can verify design, correctness, and maintainability

## Story Index

1. [01-admin-recruitment-crud.md](./01-admin-recruitment-crud.md)
2. [02-public-recruitment-detail.md](./02-public-recruitment-detail.md)
3. [03-search-and-listing-apis.md](./03-search-and-listing-apis.md)
4. [04-lifecycle-content-models.md](./04-lifecycle-content-models.md)
5. [05-production-admin-auth.md](./05-production-admin-auth.md)
6. [06-health-checks-and-monitoring.md](./06-health-checks-and-monitoring.md)
7. [07-query-performance-and-indexing.md](./07-query-performance-and-indexing.md)
8. [08-api-contract-and-frontend-alignment.md](./08-api-contract-and-frontend-alignment.md)

## Execution Notes

- Stories are intentionally independent, but some depend on earlier foundational work.
- Prefer small, reviewable PRs per story.
- Every story must include tests and proof of behavior.
- Do not broaden scope beyond the story unless it is required for correctness.
- Code review must verify clean architecture, security boundaries, validation, and database safety.

## Definition of Ready

A story is ready for implementation when:

- scope is well defined
- acceptance criteria are explicit
- expected HTTP contract is clear
- test strategy is included
- dependencies are known

## Definition of Done

A story is complete when:

- all acceptance criteria pass
- tests are added or updated
- code review feedback is addressed
- no critical security or data integrity issues remain
- docs or API contract updates are included if needed
