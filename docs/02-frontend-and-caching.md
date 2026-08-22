# Frontend + Cloudflare + Caching Specification

## 1. Goal

Build a fast, SEO-first public website for an Indian government jobs information portal.

Recommended frontend:

- Next.js
- Server-rendered/static/cached HTML where appropriate
- Cloudflare as DNS/CDN/HTTPS/security layer

The frontend should not require visitors to log in.

---

## 2. Frontend Responsibilities

The frontend is responsible for:

- Public page rendering
- SEO metadata
- Navigation
- Internal linking
- Search UI
- Job listing pages
- Individual job pages
- State/category/organization/qualification pages
- Results/admit card/answer key pages when implemented
- Responsive/mobile UX
- Calling backend APIs when required
- Cache/revalidation behavior
- Sitemap/robots/canonical metadata

Admin UI can be part of the frontend application but must be authenticated.

---

## 3. URL Structure

Use stable canonical URLs:

```text
/
 /jobs/{slug}
 /results/{slug}
 /admit-card/{slug}
 /answer-key/{slug}
 /states/{state-slug}
 /categories/{category-slug}
 /organizations/{organization-slug}
 /qualification/{qualification-slug}
 /latest-government-jobs
 /closing-soon
```

Examples:

```text
/jobs/ssc-cgl-recruitment-2026
/jobs/mpesb-group-2-sub-group-4-recruitment-2026
/results/ssc-cgl-result-2026
/states/madhya-pradesh
/categories/railway
/qualification/graduate
```

Do not create a new URL merely because a deadline is extended.

---

## 4. Page Types

### Home

Should include:

- Latest government jobs
- Results
- Admit cards
- Answer keys
- Popular categories
- State links
- Useful search/navigation

### Job detail

Should include:

- Title
- Organization
- State
- Status
- Important dates
- Vacancies
- Qualification
- Age limit
- Fee
- Selection process
- Salary where available
- Application instructions
- Official notification link
- Official website link
- Apply online link
- Last updated
- Related jobs

### Listing pages

Examples:

- Latest jobs
- State jobs
- Category jobs
- Organization jobs
- Qualification jobs
- Closing soon

These must be generated from the database, not manually maintained.

---

## 5. SEO

Every indexable page needs:

- `<title>`
- Meta description
- Canonical URL
- One clear H1
- Semantic headings
- Internal links
- Open Graph metadata
- Mobile-friendly layout
- Fast page load
- Appropriate structured data where useful
- Sitemap inclusion

Do not generate indexable pages for arbitrary filter combinations unless deliberately designed for SEO.

Avoid duplicate content caused by query parameters and URL variants.

---

## 6. Internal Linking

Internal links are a core requirement.

A job page should link to:

- State page
- Organization page
- Relevant category
- Qualification pages
- Related current jobs
- Related results/admit cards/answer keys where available

Example:

```text
SSC CGL 2026
   |
   +-- SSC
   +-- All India
   +-- Graduate Jobs
   +-- Latest Government Jobs
   +-- SSC CGL Result
```

This helps users navigate and helps search engines discover related content.

---

## 7. Cloudflare Responsibilities

Cloudflare should provide:

- DNS
- HTTPS/SSL at the edge
- CDN
- DDoS protection
- Cache
- Traffic routing
- Cache purge/invalidation

Cloudflare does not replace Railway.

Architecture:

```text
User
  |
  v
Cloudflare
  |
  v
Frontend / origin
```

---

## 8. Domain Setup

Recommended simple path:

1. Create Cloudflare account.
2. Register domain through Cloudflare Registrar, or buy from another reputable registrar.
3. If purchased elsewhere, point registrar nameservers to Cloudflare.
4. Add DNS records required by the frontend/backend hosting.
5. Enable HTTPS.
6. Verify the domain.

Conceptually:

```text
examplejobs.in
       |
       v
Cloudflare DNS
       |
       v
Frontend origin
```

If the domain is registered through Cloudflare Registrar, Cloudflare nameservers are already part of the setup.

---

## 9. Caching Strategy

Do NOT blindly cache the entire site.

### Cache candidates

```text
/jobs/*
/results/*
/admit-card/*
/answer-key/*
/states/*
/categories/*
/organizations/*
/qualification/*
```

### Do not cache

```text
/api/*
/admin/*
/login
```

Public HTML can be cached.

Admin/API responses should bypass public HTML caching.

---

## 10. Recommended Request Flow

### Cache hit

```text
Browser
  |
  v
Cloudflare
  |
  v
Cached HTML
  |
  v
Browser
```

No application/database request is needed.

### Cache miss

```text
Browser
  |
  v
Cloudflare
  |
  v
Next.js/origin
  |
  v
Spring Boot API if required
  |
  v
PostgreSQL
  |
  v
HTML
  |
  v
Cloudflare cache
  |
  v
Browser
```

---

## 11. Why caching matters

Government job/result traffic can spike suddenly.

Example:

```text
Normal traffic: 500 requests/hour
Result announcement: 50,000 requests/hour
```

The goal is:

```text
50,000 requests
       |
       v
Cloudflare
       |
       v
mostly cached response
```

rather than:

```text
50,000 requests
       |
       v
Spring Boot
       |
       v
PostgreSQL
```

Caching protects both performance and infrastructure cost.

---

## 12. Cache Invalidation

When admin changes content:

```text
Admin
  |
  v
Spring Boot
  |
  v
PostgreSQL update
  |
  v
Purge/revalidate affected URL
  |
  v
Next request renders new content
  |
  v
Cloudflare caches new response
```

At MVP stage, cache invalidation can initially be manual if necessary.

Eventually automate it.

Only purge affected URLs/prefixes where practical. Avoid full-site purges for every edit.

---

## 13. Cache TTL

Do not hard-code a single universal TTL.

Suggested conceptual policy:

- Very frequently changing pages: short TTL/revalidation.
- Individual job pages: moderate TTL with explicit purge on update.
- State/category pages: moderate TTL with purge/revalidation when a related recruitment changes.
- Static assets: long TTL with immutable/versioned filenames.
- Admin/API: no public caching.

Exact TTLs should be tuned after measuring actual update frequency.

---

## 14. Frontend Data Fetching

Avoid a design where every public page request performs multiple sequential API calls.

Prefer:

- Server-side data fetching.
- Consolidated backend endpoints where appropriate.
- Cached/revalidated responses.
- Avoid N+1 API calls.
- Use pagination for large listings.
- Keep database filtering on the backend.

Example bad pattern:

```text
Page
 -> API organizations
 -> API states
 -> API categories
 -> API jobs
 -> API qualifications
 -> API related jobs
```

Prefer a page-specific backend query or server-side aggregation where it improves performance.

---

## 15. Mobile First

A large portion of Indian search traffic is mobile.

Prioritize:

- Fast initial render
- Minimal JavaScript
- Readable typography
- Sticky/obvious important links
- Clear application status
- Tables that work on narrow screens
- Avoid intrusive popups
- Avoid excessive ads

---

## 16. Frontend Security

- Never expose database credentials.
- Never put backend secrets in browser bundles.
- Keep admin routes authenticated.
- Validate/escape user-controlled content.
- Do not trust content submitted through the admin UI blindly.
- Use HTTPS.
- Use secure cookies if session-based admin auth is used.
- Implement CSRF protection if applicable to the chosen authentication approach.

---

## 17. Observability

Track:

- Page load performance
- Cache hit ratio
- API latency
- Error rate
- 404 rate
- Search queries
- Top landing pages
- Search Console impressions/clicks
- Traffic by page type

The key business metrics are:

```text
Organic impressions
Organic clicks
Indexed pages
CTR
Average position
Pageviews
Pages/session
Returning visitors
Ad RPM
```

---

## 18. Deployment

Recommended early deployment:

```text
GitHub
  |
  +--> Frontend deployment
  |
  +--> Backend deployment on Railway
```

Use separate environment variables for:

- API URL
- database credentials (backend only)
- admin secrets
- analytics IDs
- Cloudflare credentials if automation requires them

Never commit secrets.

---

## 19. MVP Rule

Do not spend days implementing advanced Cloudflare caching before the first deployment works.

Order:

1. Domain
2. Cloudflare DNS
3. Frontend deployment
4. Backend deployment
5. PostgreSQL
6. Verify public URLs
7. Search Console
8. Publish content
9. Add caching
10. Measure
11. Optimize
