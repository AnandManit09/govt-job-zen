# Backend Open Questions and Architecture Gaps

## 1. Security baseline is still too simple

The current admin authentication uses an in-memory user and HTTP Basic auth. That is acceptable for local development and early MVP work, but it is not production-ready.

Open questions:

- Should admin credentials live in a database or environment variables only?
- Should we enable a proper admin role model and multiple admin users?
- Should we add CSRF protection for browser-based admin UI?
- Should we support OAuth or one-time admin login for deployment?

## 2. Public API security is not fully specified

The public endpoints are intentionally open, but the architecture doc suggests static SEO pages and dynamic API access. It is not yet defined whether the same backend should serve public data directly or only support the frontend service layer.

Questions to resolve:

- Should the backend be public-facing only behind a Next.js frontend origin?
- Should an API gateway or reverse proxy handle rate limits and caching?
- Should public APIs return raw entities or a public DTO model?

## 3. Data model for lifecycle content is incomplete

The docs suggest separate `Result`, `Admit Card`, `Answer Key`, and `Exam Event` domains, but only the core recruitment table is implemented.

Architectural concern:

- Recruitment is a central fact table, but lifecycle content will grow into a second dimension of domain objects.
- Without a proper model, future queries can easily become inconsistent or duplicated.

## 4. Search and listing behavior is undefined

The docs mention state pages, category pages, latest jobs, closing-soon pages, and search, but no concrete API contract has been defined yet.

Open questions:

- Should search be database-backed or a separate search engine later?
- Are listing pages primarily database queries or edge-cached HTML pages?
- How should published vs unpublished records behave during search indexing?

## 5. Historical or versioning model is missing

The docs emphasize preserving historical pages and updating records without changing URLs. The current backend does not yet support a version history or record revision model.

Needs clarification:

- Should a recruitment update overwrite the same row?
- Should we keep a revision table for auditability?
- How do we handle prior URLs and canonical URLs when content changes?

## 6. Database migration strategy is still minimal

Flyway is enabled and a base schema is present, but production data management needs better practice.

Gaps:

- No seed/default organizations/states/categories
- No rollback plan for data migrations
- No index review for production query patterns
- No backup/restore procedure documented in code

## 7. Deployment environment assumptions are not fully engineered

The docs propose Railway + PostgreSQL + Cloudflare, but the current application still assumes a local PostgreSQL service and fixed admin credentials.

Open questions:

- How will environment variables be managed in Railway?
- What are the readiness and liveness checks?
- What is the health-check path for external monitoring?
- How do we handle migrations on deploy?

## 8. Domain slug strategy needs stronger rules

The app currently uses a simple slugify method and may create collisions or inconsistent slugs for repeated names.

Needs review:

- Should the slug be globally unique across all recruitment content?
- Should there be a separate slug history table?
- Should user-provided slugs be locked or auto-generated?

## 9. Query and performance expectations are not yet validated

The implementation is ready for MVP but there has been no query profiling or indexing review against expected traffic.

Needs validation:

- Latest jobs query performance
- Category and state listing query counts
- Join-table fetch behavior for categories and qualifications
- N+1 risks in public listing responses

## 10. Frontend/backend boundary is not finalized

The backend is safe to build independently, but the boundary between backend APIs and the frontend rendering layer is still a design decision.

Important decision:

- Are public pages generated server-side by the frontend using backend JSON?
- Or does the backend serve direct HTML pages too?

## Recommendation

The next priority should be to make the backend production-usable at the data layer, not to expand the feature set too quickly. That means:

1. Add recruitment read/update/delete admin APIs
2. Add public detail and listing endpoints
3. Add lifecycle content models for result/admit-card/answer-key domains
4. Replace in-memory admin credentials with a deploy-safe solution
5. Validate queries against expected large dataset patterns
6. Lock down the API contract for the frontend consumers
