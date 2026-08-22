# Government Jobs Portal — Agent Context Pack

Use these documents as the baseline context for development.

## Files

1. `01-project-architecture.md`
   - Complete architecture
   - Product principles
   - Competition learnings
   - URL architecture
   - lifecycle
   - data flow
   - SEO
   - monetization
   - cost strategy
   - security

2. `02-frontend-and-caching.md`
   - Next.js responsibilities
   - URL/page structure
   - SEO
   - Cloudflare DNS/CDN
   - caching
   - invalidation
   - traffic spikes
   - frontend security/deployment

3. `03-backend-and-database.md`
   - Spring Boot responsibilities
   - PostgreSQL domain model
   - entities/relationships
   - status/lifecycle
   - APIs
   - indexes
   - admin authentication
   - backups
   - deployment
   - backend guardrails

4. `04-requirements-and-development-plan.md`
   - MVP scope
   - requirements
   - deployment plan
   - traffic validation
   - monetization
   - development order
   - agent guardrails
   - definition of success

## Important instruction for the coding agent

Do not invent infrastructure or requirements that are not justified by these documents.

Prefer a simple modular monolith:

Next.js + Spring Boot + PostgreSQL + Cloudflare + Railway.

Optimize for fast development, SEO, reliability, security, and low operating cost. Scale only when measured traffic/usage requires it.
