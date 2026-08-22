# Requirements + Development Plan

## 1. Product Vision

Create a fast, trustworthy Indian government jobs and exam information portal focused on:

- Government job notifications
- Recruitment summaries
- Application dates
- Eligibility
- Vacancies
- Official links
- Results
- Admit cards
- Answer keys
- State/category/qualification/organization discovery

Primary acquisition channel:

> Organic Google search.

Primary initial monetization:

> Google AdSense.

---

## 2. Target Users

Users preparing for Indian government exams and users searching for:

- New recruitment notifications
- State government jobs
- Central government jobs
- Results
- Admit cards
- Answer keys
- Jobs by qualification
- Jobs by department/organization
- Jobs nearing application deadlines

---

## 3. Functional Requirements

### Content

- Create recruitment
- Edit recruitment
- Publish/unpublish recruitment
- Update recruitment
- Preserve historical pages
- Record last updated time
- Record official links
- Track application dates
- Track vacancies
- Track eligibility
- Track qualification
- Track state
- Track organization
- Track category

### Discovery

- Latest jobs
- State pages
- Category pages
- Organization pages
- Qualification pages
- Closing-soon jobs
- Search

### Lifecycle

- Upcoming
- Active
- Extended
- Closed
- Result declared
- Admit card available
- Answer key available

### SEO

- Stable URLs
- Sitemap
- Canonical URLs
- Metadata
- Internal linking
- Fast rendering
- Mobile UX

---

## 4. Non-Functional Requirements

### Performance

- Fast public pages
- CDN caching
- Efficient PostgreSQL queries
- Pagination
- Avoid N+1 queries

### Security

- No public login
- Admin authentication required
- Database not directly exposed to users
- HTTPS
- Secrets stored in environment variables
- Protected admin APIs

### Reliability

- PostgreSQL backups
- Off-platform backup
- Restore procedure
- Cache invalidation on content update

### Cost

- Start as cheaply as practical.
- Railway for backend/PostgreSQL.
- Cloudflare for DNS/CDN.
- Avoid AWS until justified.

---

## 5. Initial Technology Stack

```text
Frontend:
Next.js

Backend:
Java + Spring Boot

Database:
PostgreSQL

Hosting:
Railway

DNS/CDN:
Cloudflare

Source control:
GitHub

Backups:
Separate object storage/provider
```

---

## 6. MVP

### Phase 1

- [ ] Domain
- [ ] Cloudflare account
- [ ] GitHub repositories
- [ ] Next.js project
- [ ] Spring Boot project
- [ ] Railway project
- [ ] PostgreSQL
- [ ] Database migrations
- [ ] Basic admin authentication
- [ ] Recruitment CRUD
- [ ] State/category/organization/qualification entities
- [ ] Public job page
- [ ] Latest jobs
- [ ] State page
- [ ] Category page
- [ ] Search
- [ ] Sitemap
- [ ] robots.txt
- [ ] Canonical metadata
- [ ] Official links
- [ ] Backup

### Phase 2

- [ ] Results
- [ ] Admit cards
- [ ] Answer keys
- [ ] Exam dates
- [ ] Closing-soon page
- [ ] Related content
- [ ] Better search
- [ ] Cache invalidation

### Phase 3

- [ ] AI-assisted extraction
- [ ] Notification monitoring
- [ ] Automated draft generation
- [ ] Admin review workflow
- [ ] Analytics dashboard
- [ ] Affiliate integrations
- [ ] Direct advertising

---

## 7. Initial Deployment

Goal:

> Get online quickly.

Do not spend time on:

- Kubernetes
- AWS networking
- Microservices
- Elasticsearch
- Redis
- Kafka
- multi-region
- complex CI/CD

Basic deployment:

```text
GitHub
  |
  +--> Next.js hosting
  |
  +--> Railway Spring Boot
           |
           +--> Railway PostgreSQL
```

Cloudflare:

```text
Domain
  |
  v
Cloudflare DNS/CDN
```

---

## 8. Traffic Validation Plan

The first objective is not revenue.

It is:

> Determine whether the site can acquire organic search traffic.

Monitor:

- Google Search Console
- Google Analytics
- Indexed pages
- Search impressions
- Search clicks
- CTR
- Average position
- Organic sessions
- Pageviews
- Pages/session
- Top landing pages

Traffic milestones discussed:

```text
10K pageviews/month
        |
        v
100K
        |
        v
500K
        |
        v
1M
```

Infrastructure should scale only when actual metrics justify it.

---

## 9. Monetization Planning

AdSense planning assumption:

```text
₹80–₹200 page RPM
```

Use approximately:

```text
₹120 RPM
```

for conservative middle-case planning.

Formula:

```text
Revenue = Pageviews / 1000 × Page RPM
```

Examples:

```text
100K pageviews × ₹120 / 1000 = ₹12K/month
500K pageviews × ₹120 / 1000 = ₹60K/month
1M pageviews × ₹120 / 1000 = ₹1.2L/month
```

These are not guarantees.

The business should optimize for useful traffic and user value, not ads alone.

---

## 10. Competitive Learnings

From GovtJobsAlert, Career Power and FreeJobAlert:

### Learn

- Use separate content types.
- Have individual recruitment pages.
- Have state navigation.
- Have category navigation.
- Have qualification navigation.
- Have organization/department navigation.
- Separate jobs/results/admit cards/answer keys.
- Provide clear important dates.
- Provide important links.
- Build extensive internal linking.
- Keep historical pages accessible.

### Do not copy

- Exact design
- Exact article wording
- Exact content
- Branding
- Proprietary assets
- Competitors' images without rights

Build an original UX with the same useful information architecture.

---

## 11. Critical Business Insight

The software is not the hardest part.

The difficult problem is:

> Building enough trustworthy, useful content and domain authority to acquire organic traffic.

Do not mistake infrastructure work for business progress.

The system should make publishing extremely easy so effort can be spent on:

- Finding notifications
- Verifying information
- Publishing quickly
- Updating extensions
- Improving search coverage
- Building internal links
- Improving UX
- Monitoring Google performance

---

## 12. Content Strategy Requirement

Avoid a site made of thousands of thin pages.

Each page should add useful value:

- concise summary
- verified dates
- vacancies
- eligibility
- age limit
- fees
- selection process
- exam information
- official links
- update history
- related resources

If AI assists with content creation, the pipeline should be:

```text
Official source
   |
   v
Extraction
   |
   v
AI-assisted draft
   |
   v
Human verification
   |
   v
Publish
```

---

## 13. Important URL Rule

Once a job URL is indexed:

```text
/jobs/ssc-cgl-recruitment-2026
```

do not create a new URL merely because:

- last date changed
- vacancies changed
- application was extended
- minor notification update occurred

Update the same canonical page.

---

## 14. Expired Content Rule

Do not automatically delete expired recruitment pages.

Instead:

```text
ACTIVE
  |
  v
CLOSED
```

Keep the page accessible and clearly display:

> Application closed.

Later:

```text
RESULT DECLARED
```

can add result information while preserving the original recruitment page.

---

## 15. Search Architecture

Initial search:

PostgreSQL.

Search across:

- title
- organization
- state
- category
- qualification
- description where useful

Use:

- filtering
- sorting
- pagination

Do not introduce Elasticsearch until PostgreSQL search is proven insufficient.

---

## 16. Admin Workflow

Ideal workflow:

```text
Login
  |
  v
Dashboard
  |
  v
Create Recruitment
  |
  +--> Title
  +--> Organization
  +--> State
  +--> Categories
  +--> Qualifications
  +--> Dates
  +--> Vacancies
  +--> Eligibility
  +--> Fee
  +--> Selection
  +--> Official notification
  +--> Apply URL
  |
  v
Preview
  |
  v
Publish
```

Later:

```text
Notification PDF
       |
       v
AI extraction
       |
       v
Draft
       |
       v
Human review
       |
       v
Publish
```

---

## 17. Operational Principles

- One domain.
- One primary PostgreSQL database initially.
- One Spring Boot backend initially.
- One frontend application initially.
- Cloudflare in front.
- Cache public pages.
- Protect admin APIs.
- Back up database.
- Monitor actual usage.
- Scale only based on evidence.

---

## 18. Agent Guardrails

When implementing this project, the coding agent MUST NOT assume:

- AWS is required.
- Kubernetes is required.
- Microservices are required.
- Redis is required.
- Elasticsearch is required.
- Kafka is required.
- Users need accounts.
- Every post requires a custom frontend component.
- Expired jobs should be deleted.
- A deadline extension requires a new URL.
- Every visitor must hit Spring Boot.
- Every page must query PostgreSQL on every request.
- State/category pages need manually maintained content.
- Competitor content can be copied.

The coding agent SHOULD assume:

- PostgreSQL is the source of truth for structured content.
- Spring Boot is the backend.
- Next.js is the frontend.
- Railway is the initial backend/database hosting platform.
- Cloudflare is the DNS/CDN/security layer.
- Admin operations are authenticated.
- Public pages should be SEO-friendly and cacheable.
- URLs should be stable.
- Historical content should remain available.
- Content changes should eventually invalidate relevant caches.
- Infrastructure should remain simple until traffic proves the need to scale.

---

## 19. Definition of MVP Success

Technical:

- Website is live.
- HTTPS works.
- Admin can publish jobs.
- Job URLs work.
- Listing pages update automatically.
- PostgreSQL is backed up.
- Public pages are indexable.
- Sitemap works.
- No critical security issues.
- Cloudflare is correctly configured.

Business:

- Google discovers pages.
- Pages begin receiving impressions.
- Organic traffic is measurable.
- Users navigate to related content.
- Content publishing is fast enough to keep up with notifications.

Do not judge the business by AdSense revenue in the first few weeks. First establish whether organic search acquisition is working.
