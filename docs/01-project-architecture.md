# Government Jobs Information Portal — Complete Architecture

## 1. Project Context

Build a content-driven Indian government jobs and exam information portal.

The product is **not a traditional blog**. It is a structured information portal where recruitment, admit card, answer key, result, state, category, organization, and qualification information are stored as structured data and rendered through reusable page templates.

Primary business goal:

- Publish government job/exam information quickly.
- Capture organic Google search traffic.
- Provide useful summaries and official links.
- Monetize traffic with Google AdSense initially.
- Add affiliate/direct advertising opportunities later if traffic becomes meaningful.

Initial objective:

> Launch quickly and cheaply, validate organic traffic, and only increase infrastructure spend when traffic justifies it.

Do NOT over-engineer the first version.

---

## 2. Important Product Principles

1. One domain can contain thousands/millions of URLs.
2. A new job should normally be a new database record, **not a manually coded webpage**.
3. Reusable templates render individual records.
4. State/category/qualification/organization/latest pages are dynamic views over the same underlying data.
5. Expired jobs should generally remain online for historical/search value.
6. Job lifecycle/status should control whether a job appears in "active/latest/closing soon" views.
7. Government deadline extensions should update the existing recruitment record and existing URL.
8. Public pages should be cacheable.
9. Admin/API/database writes must bypass public HTML caching.
10. The system should prioritize SEO, reliability, low operational effort, and fast publishing.
11. Do not copy competitors' content or exact design. Learn from their information architecture and UX patterns.
12. Do not create thin, repetitive, AI-generated pages merely to increase page count.

---

## 3. Reference Competitors

The following sites were used as architecture/content-model references:

- https://govtjobsalert.in/
- https://www.careerpower.in/government-jobs.html
- https://www.freejobalert.com/

Key observations:

### GovtJobsAlert

Observed structure includes:

- Latest government jobs
- Results
- Admit Cards
- Answer Keys
- State/category navigation
- Individual recruitment pages
- Large number of job entries

### Career Power

Observed pattern:

- Government Jobs landing page
- Individual recruitment articles
- Recruitment-specific URLs
- Content organized around government recruitment notifications

### FreeJobAlert

Particularly useful as an information architecture reference. It exposes information through:

- All India government jobs
- State government jobs
- Banking
- Teaching
- Engineering
- Railway
- Police/Defence
- Latest notifications
- Search
- Results
- Admit Cards
- Exam Dates
- Answer Keys
- Cutoff Marks
- Written Marks
- Interview Results
- Qualification pages such as 10th, 12th, Diploma, ITI, B.Tech/B.E, B.Com, Any Graduate, etc.

### Competition lesson

The important pattern is:

> One underlying recruitment can be discoverable through many relevant search/navigation dimensions.

For example, a recruitment can appear on:

- its individual job page
- latest jobs
- state page
- organization page
- category page
- qualification page

Do not duplicate the article itself for every dimension. Generate listing pages from relationships/queries.

---

## 4. Recommended High-Level Architecture

```text
                         Google / Users
                               |
                               v
                       +----------------+
                       |   Cloudflare   |
                       | DNS / CDN /    |
                       | HTTPS / DDoS   |
                       +-------+--------+
                               |
                  +------------+-------------+
                  |                          |
             Cached public              Dynamic/API
                pages                    requests
                  |                          |
                  v                          v
             +---------+              +-------------+
             | Next.js |              | Spring Boot |
             | Frontend|              | Backend API |
             +---------+              +------+------+
                                             |
                                             v
                                      +--------------+
                                      | PostgreSQL   |
                                      +--------------+
                                             |
                                             v
                                      Backup/Object
                                        Storage
```

Recommended initial hosting:

- Domain: Cloudflare Registrar or another reputable registrar.
- DNS/CDN/HTTPS: Cloudflare.
- Frontend: Next.js hosted on a low-cost/free platform compatible with the deployment model.
- Backend: Spring Boot on Railway.
- Database: PostgreSQL on Railway.
- Source control: GitHub.
- Backups: separate/off-platform storage.
- Do not start with AWS unless there is a concrete reason to migrate.

---

## 5. Why Railway Initially

The initial requirement is fast launch and low cost, while not compromising on PostgreSQL.

Railway is preferred for the MVP because:

- Spring Boot can run there.
- PostgreSQL can run there.
- GitHub-based deployment is simple.
- No need to manually manage EC2, Nginx, systemd, VPC, ALB, RDS, etc.
- Usage-based pricing allows the infrastructure to start small.
- The application can later be moved to AWS or another provider if traffic/requirements justify it.

Target initial infrastructure budget discussed:

- Approximately ₹700–₹1,500/month for early MVP infrastructure, excluding the domain.
- Actual Railway cost is usage-dependent and must be monitored.
- Do not treat these numbers as guarantees.

Potential scaling planning ranges discussed:

| Stage | Approx traffic | Rough infrastructure planning range |
|---|---:|---:|
| Launch | <10K pageviews/month | ~₹1K–₹2K/month |
| Growing | 10K–100K | ~₹1K–₹3K/month |
| Traction | 100K–500K | ~₹2K–₹6K/month |
| Serious | 500K–1M | ~₹5K–₹12K/month |
| Large | 1M–5M | ~₹10K–₹30K+/month |

These are architecture planning estimates, not vendor quotes.

---

## 6. Domain

Only one domain is required.

Example:

```text
examplejobs.in
```

It can contain unlimited logical content URLs:

```text
examplejobs.in/jobs/ssc-cgl-recruitment-2026
examplejobs.in/jobs/mp-police-recruitment-2026
examplejobs.in/results/ssc-cgl-result-2026
examplejobs.in/states/madhya-pradesh
examplejobs.in/categories/railway
```

The user does NOT buy a domain per article.

Domain planning budget discussed:

- Approximately ₹1,000–₹1,500/year.
- Check renewal pricing, not only first-year promotional pricing.

Cloudflare Registrar is one possible option and avoids an additional registrar-to-Cloudflare DNS setup when the domain is registered there.

---

## 7. Public URL Architecture

Use clean, human-readable, stable URLs.

### Home

```text
/
```

### Individual recruitment

```text
/jobs/{slug}
```

Example:

```text
/jobs/mpesb-group-2-sub-group-4-recruitment-2026
/jobs/ssc-cgl-recruitment-2026
/jobs/rrb-technician-recruitment-2026
```

### Results

```text
/results/{slug}
```

Example:

```text
/results/ssc-cgl-result-2026
/results/mpesb-group-2-result-2026
```

### Admit cards

```text
/admit-card/{slug}
```

### Answer keys

```text
/answer-key/{slug}
```

### State pages

```text
/states/{state-slug}
```

Example:

```text
/states/madhya-pradesh
/states/uttar-pradesh
/states/bihar
```

### Category pages

```text
/categories/{category-slug}
```

Examples:

```text
/categories/railway
/categories/banking
/categories/teaching
/categories/police
```

### Organization pages

```text
/organizations/{organization-slug}
```

Examples:

```text
/organizations/ssc
/organizations/mpesb
```

### Qualification pages

```text
/qualification/{qualification-slug}
```

Examples:

```text
/qualification/10th
/qualification/12th
/qualification/iti
/qualification/diploma
/qualification/graduate
/qualification/btech
```

### Listing pages

```text
/latest-government-jobs
/closing-soon
/results
/admit-card
/answer-key
```

The exact URL hierarchy can be adjusted later, but once URLs are indexed, avoid unnecessary changes.

---

## 8. Core Content Model

The central entity should be a recruitment/job rather than an HTML page.

Example:

```text
Recruitment
- id
- title
- slug
- organization
- state
- categories
- qualifications
- notification_date
- application_start_date
- application_end_date
- vacancies
- qualification_details
- age_limit
- salary
- application_fee
- selection_process
- exam_date
- description/content
- official_notification_url
- official_website_url
- apply_url
- status
- created_at
- updated_at
```

Additional entities should include:

- Organization
- State
- Category
- Qualification
- RecruitmentQualification
- RecruitmentCategory
- RecruitmentStatus / lifecycle fields
- Result
- AdmitCard
- AnswerKey

Exact normalization can be finalized during implementation.

---

## 9. Recruitment Lifecycle

Do not delete a recruitment when its application closes.

Recommended conceptual lifecycle:

```text
UPCOMING
   |
   v
ACTIVE
   |
   v
EXTENDED (when deadline is extended)
   |
   v
CLOSED
   |
   +--> ADMIT_CARD_OUT
   |
   +--> ANSWER_KEY_OUT
   |
   +--> RESULT_DECLARED
```

A more normalized implementation may model events separately rather than putting every state into one enum.

Important behavior:

- Active listings should be driven by dates/status.
- Closed jobs remain accessible.
- Result/admit-card/answer-key information can be attached as related content.
- Search engines can continue to find historical recruitment pages.
- If a deadline is extended, update the existing record and preserve the same canonical URL.

---

## 10. Example Lifecycle

Initial:

```text
SSC CGL 2026
application_end_date = 2026-08-25
status = ACTIVE
```

On August 26:

```text
application_end_date < current_date
```

The job should no longer appear in active/latest application listings.

But:

```text
/jobs/ssc-cgl-recruitment-2026
```

must remain online.

If SSC extends the deadline:

```text
application_end_date = 2026-09-10
status = EXTENDED
```

The same URL is updated.

The page should visibly indicate the extension and update timestamp.

---

## 11. Home/Listing Page Behavior

The homepage should be generated from database queries.

Conceptual sections:

```text
Latest Government Jobs
Latest Results
Latest Admit Cards
Latest Answer Keys
Popular Categories
State Jobs
```

A state page should query the recruitment table using the state relationship.

Example:

```sql
SELECT ...
FROM recruitment
WHERE state_id = :madhyaPradesh
ORDER BY notification_date DESC;
```

An active-jobs query can include date/status conditions.

Do not manually edit homepage/category/state lists whenever a new job is added.

---

## 12. Data Flow — Publishing a New Job

```text
Admin
  |
  v
Admin Dashboard
  |
  v
Spring Boot API
  |
  +--> Validate fields
  |
  +--> Create recruitment record
  |
  +--> Create relationships
  |
  v
PostgreSQL
  |
  v
Revalidation / cache purge
  |
  v
Public URL becomes available
  |
  +--> Individual job page
  +--> Latest jobs
  +--> State page
  +--> Category page
  +--> Qualification page
  +--> Organization page
```

Eventually, cache invalidation should be automated when content changes.

---

## 13. Data Flow — Visitor

Preferred public flow:

```text
User / Google
    |
    v
Cloudflare
    |
    +--> cache hit --> cached public HTML
    |
    +--> cache miss
             |
             v
         Next.js
             |
             v
        Backend/API if required
             |
             v
         PostgreSQL
             |
             v
        Render response
             |
             v
       Cloudflare caches
             |
             v
            User
```

Not every visitor should hit Spring Boot/PostgreSQL.

This is critical for handling traffic spikes.

---

## 14. Caching Strategy

Cache public, relatively stable pages:

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

Do not cache:

```text
/api/*
/admin/*
/login
```

Do not blindly enable "Cache Everything" for the whole domain.

HTML caching should be explicitly controlled with Cloudflare Cache Rules and application-level/revalidation strategy.

When a job changes:

```text
Admin update
   |
   v
PostgreSQL update
   |
   v
Invalidate/purge affected URL(s)
   |
   v
Next request renders latest version
   |
   v
New response is cached
```

For an MVP, caching can be introduced after the basic site is working. Do not spend excessive time optimizing before traffic exists.

---

## 15. Traffic Spike Design

Government result/notification announcements can cause sudden traffic spikes.

Bad:

```text
50,000 visitors
      |
      v
Spring Boot
      |
      v
PostgreSQL
```

Preferred:

```text
50,000 visitors
      |
      v
Cloudflare
      |
      v
Cached result page
```

Cloudflare should absorb the majority of repeat requests for cacheable public pages.

Monitor:

- Cache hit ratio
- Railway CPU
- Railway memory
- PostgreSQL connections
- Query latency
- Application response time
- Network egress

---

## 16. Security

Public users do not need accounts/login.

However, the admin interface MUST be authenticated.

Public:

```text
GET /jobs/...
GET /states/...
GET /categories/...
```

Admin:

```text
/admin/...
```

Admin/API write operations must be protected.

Do not expose PostgreSQL publicly if it can be avoided.

The public website should never have direct database access.

Use:

```text
Browser -> Frontend -> Backend -> Database
```

not:

```text
Browser -> Database
```

---

## 17. SEO Requirements

SEO is a primary product requirement.

Every recruitment page should have:

- Stable canonical URL
- Unique title
- Unique meta description
- Proper H1
- Logical H2/H3 structure
- Relevant internal links
- Last updated timestamp
- Structured information where appropriate
- Official notification/apply links
- Sitemap inclusion
- Mobile-friendly HTML
- Fast loading
- No accidental duplicate URLs

Create XML sitemap(s) for crawlable canonical pages.

Do not create indexable pages for every arbitrary filter combination unless there is a deliberate SEO/content reason.

Avoid duplicate-content URL permutations.

---

## 18. Search/Navigation Strategy

Users should be able to navigate:

```text
Home
 -> State
 -> Category
 -> Organization
 -> Qualification
 -> Job
```

A single recruitment should be discoverable from several relevant dimensions without duplicating its content.

Example:

```text
MPESB Group 2
  |
  +--> /jobs/mpesb-group-2-...
  +--> /states/madhya-pradesh
  +--> /organizations/mpesb
  +--> /qualification/graduate
  +--> /categories/government-jobs
```

---

## 19. Content Quality Requirements

The website should not become a thin-content generator.

Do not:

- Copy competitor articles.
- Copy large amounts of notification text.
- Generate hundreds of near-identical AI pages without added value.
- Create pages solely to target keywords.
- Create duplicate URLs for the same recruitment.

Prefer:

- Structured summary
- Verified dates
- Vacancy information
- Eligibility
- Age limit
- Application fee
- Selection process
- Exam date
- Official links
- Important documents
- Clear update history
- Related useful content

AI may assist extraction/drafting, but publishing should include validation/review.

---

## 20. Monetization

Initial monetization:

- Google AdSense.

There is no stated Google requirement for a minimum traffic number before applying, but the site should be substantially built, policy-compliant, useful, and contain original/high-quality content.

Traffic is the real economic driver.

Planning-only Page RPM assumption discussed:

- ₹80–₹200/pageview RPM as a broad planning range.
- Use ~₹120 RPM as a middle planning assumption.
- This is NOT a guaranteed AdSense rate.

Approximate planning:

| Monthly pageviews | ₹80 RPM | ₹120 RPM | ₹200 RPM |
|---:|---:|---:|---:|
| 10K | ₹800 | ₹1,200 | ₹2,000 |
| 50K | ₹4K | ₹6K | ₹10K |
| 100K | ₹8K | ₹12K | ₹20K |
| 250K | ₹20K | ₹30K | ₹50K |
| 500K | ₹40K | ₹60K | ₹1L |
| 1M | ₹80K | ₹1.2L | ₹2L |
| 2M | ₹1.6L | ₹2.4L | ₹4L |
| 5M | ₹4L | ₹6L | ₹10L |

These are planning scenarios, not promises.

Potential later monetization:

- Education/test-series affiliates
- Direct advertising
- Sponsored placements, used carefully
- Premium tools/content if there is genuine demand

Do not optimize the product around ads before traffic/product-market fit.

---

## 21. Cost Strategy

The explicit goal is:

> Start tiny, validate traffic, then scale infrastructure.

Initial:

```text
Domain
Cloudflare
Next.js
Railway Spring Boot
Railway PostgreSQL
GitHub
```

Target initial infrastructure budget discussed:

~₹700–₹1,500/month plus domain, depending on actual Railway usage.

Do not build an AWS-heavy architecture before traffic validates the business.

AWS may become appropriate later for:

- Multiple backend instances
- Managed RDS
- More control
- Higher reliability requirements
- More complex workloads

But it is not required for the MVP.

---

## 22. Database Backup Requirement

Do not compromise on the PostgreSQL data itself.

Once meaningful content exists:

- Maintain automated backups.
- Prefer an off-platform backup copy.
- Do not rely only on a temporary/free database tier.
- Test restoration periodically.

The database is a core business asset.

---

## 23. What NOT to Build Initially

Do not introduce these unless a concrete requirement appears:

- Kubernetes
- ECS
- Kafka
- Redis
- Elasticsearch
- RabbitMQ
- Microservices
- Multiple databases
- Complex event-driven infrastructure
- Load balancers
- Multi-region deployment

Start with:

```text
Next.js
+
Spring Boot
+
PostgreSQL
+
Cloudflare
+
Railway
```

---

## 24. MVP Scope

### Required

- [ ] Domain
- [ ] Cloudflare DNS
- [ ] Next.js frontend
- [ ] Spring Boot backend
- [ ] PostgreSQL
- [ ] Admin authentication
- [ ] Recruitment CRUD
- [ ] State/category/organization/qualification relationships
- [ ] Individual recruitment page
- [ ] Latest jobs page
- [ ] State pages
- [ ] Category pages
- [ ] Search
- [ ] SEO metadata
- [ ] Sitemap
- [ ] Canonical URLs
- [ ] Official notification/apply links
- [ ] Update timestamps
- [ ] Status/date-based filtering
- [ ] Database backup

### Later

- [ ] Results
- [ ] Admit cards
- [ ] Answer keys
- [ ] Exam dates
- [ ] Advanced search
- [ ] Automated notification extraction
- [ ] AI-assisted content generation
- [ ] Automated cache invalidation
- [ ] Analytics dashboards
- [ ] Affiliate integrations
- [ ] Direct advertising
- [ ] More advanced scaling

---

## 25. Development Philosophy

The application should be simple enough for one developer to operate.

Prioritize:

1. Correct data model
2. Clean URL architecture
3. SEO
4. Fast publishing
5. Reliability
6. Security
7. Caching
8. Low cost
9. Observability
10. Scaling only when justified by evidence

The main business risk is **not infrastructure capacity**.

The main risk is failing to acquire useful organic traffic.

Therefore, avoid spending weeks on infrastructure that does not improve publishing, SEO, user experience, reliability, or measurable traffic.
