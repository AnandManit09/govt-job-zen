# Backend + PostgreSQL Specification

## 1. Goal

Build a structured content management backend for a government jobs/exam information portal.

Recommended stack:

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL
- Flyway/Liquibase for migrations
- Spring Security for admin authentication
- Railway for initial hosting

The backend is not just a generic blog API. It is the content/data system that powers multiple public views.

---

## 2. Core Principle

A recruitment is a **data entity**, not an HTML page.

Example:

```text
One database record:
SSC CGL Recruitment 2026
```

Can appear at:

```text
/jobs/ssc-cgl-recruitment-2026
/latest-government-jobs
/states/...
/categories/...
/organizations/ssc
/qualification/graduate
```

Do not duplicate the recruitment record for every listing page.

---

## 3. Suggested Domain Model

### Recruitment

Core fields:

```text
id
title
slug
organization_id
state_id

notification_date
application_start_date
application_end_date

vacancies
qualification_details
age_limit
salary
application_fee
selection_process
exam_date

description/content

official_notification_url
official_website_url
apply_url

status

created_at
updated_at
published_at
```

Potential additional fields:

```text
meta_title
meta_description
canonical_url
featured
last_verified_at
```

Avoid storing derived fields that can be calculated unless there is a performance reason.

---

## 4. Organization

```text
organization
---------------
id
name
slug
description
website_url
created_at
updated_at
```

Examples:

```text
SSC
MPESB
UPPSC
RRB
IBPS
SBI
```

---

## 5. State

```text
state
---------------
id
name
slug
```

Examples:

```text
madhya-pradesh
uttar-pradesh
bihar
rajasthan
```

---

## 6. Category

```text
category
---------------
id
name
slug
```

Examples:

```text
banking
railway
teaching
police
defence
engineering
psu
```

A recruitment may belong to multiple categories.

Therefore prefer a join table:

```text
recruitment_category
--------------------
recruitment_id
category_id
```

---

## 7. Qualification

```text
qualification
---------------
id
name
slug
```

Examples:

```text
10th
12th
iti
diploma
graduate
btech
bcom
```

Use a join table:

```text
recruitment_qualification
-------------------------
recruitment_id
qualification_id
```

A recruitment may accept multiple qualifications.

---

## 8. Results / Admit Cards / Answer Keys

Do not force every recruitment lifecycle event into one giant recruitment table.

Use related entities where the information becomes complex.

Possible structure:

```text
recruitment
    |
    +-- result
    +-- admit_card
    +-- answer_key
    +-- exam_event
```

For MVP, simple nullable relationships can be acceptable.

Design for the following public content types:

```text
Job / Recruitment
Result
Admit Card
Answer Key
Exam Date
```

---

## 9. Status

A simple status enum can be:

```text
UPCOMING
ACTIVE
EXTENDED
CLOSED
RESULT_DECLARED
```

However, avoid using status as the only source of truth for lifecycle.

For example, `application_end_date` should be available for queries.

Potential later architecture:

```text
Recruitment
  |
  +-- ApplicationPeriod
  +-- ExamEvent
  +-- AdmitCard
  +-- AnswerKey
  +-- Result
```

This is more extensible.

---

## 10. Date-Based Behavior

Example:

```text
application_end_date = 2026-08-25
```

Active listing query:

```sql
application_end_date >= CURRENT_DATE
```

plus appropriate status/business rules.

When the date passes:

- Stop showing the recruitment in active application lists.
- Keep its individual page online.
- Allow it to appear in historical/search results.
- Show "Application Closed".

If the deadline is extended:

```text
application_end_date = 2026-09-10
status = EXTENDED
```

Keep the same slug and URL.

---

## 11. Database Constraints

Use database-level constraints where appropriate.

Examples:

- Unique slug.
- Unique organization slug.
- Unique state slug.
- Unique category slug.
- Unique qualification slug.
- Foreign keys.
- NOT NULL for required fields.
- Check constraints for valid date relationships where practical.

Example:

```text
slug UNIQUE
```

Do not rely only on application-level uniqueness checks.

---

## 12. Indexing

Likely important indexes:

### Recruitment

```text
slug
organization_id
state_id
status
notification_date
application_start_date
application_end_date
published_at
created_at
```

Composite indexes should be based on real query patterns.

Potential examples:

```text
(state_id, application_end_date)
(organization_id, notification_date)
(status, published_at)
```

Do not add dozens of indexes blindly.

Use query plans once real data exists.

---

## 13. Search

Initial search should use PostgreSQL capabilities.

Do not add Elasticsearch/OpenSearch initially.

Search requirements:

- title
- organization
- state
- category
- qualification
- possibly description

Start simple.

If search scale becomes a real bottleneck, introduce a dedicated search system later.

---

## 14. API Design

Public API should be read-focused.

Examples:

```text
GET /api/jobs/{slug}
GET /api/jobs
GET /api/jobs/latest
GET /api/jobs/closing-soon

GET /api/states/{slug}
GET /api/categories/{slug}
GET /api/organizations/{slug}
GET /api/qualifications/{slug}

GET /api/results
GET /api/results/{slug}

GET /api/admit-cards
GET /api/admit-cards/{slug}

GET /api/answer-keys
GET /api/answer-keys/{slug}
```

Admin API:

```text
POST   /api/admin/jobs
PUT    /api/admin/jobs/{id}
DELETE /api/admin/jobs/{id}

POST   /api/admin/results
PUT    /api/admin/results/{id}
```

Exact REST design can be adjusted during implementation.

---

## 15. Admin Authentication

Public visitors do not need accounts.

Admin users do.

Recommended:

```text
Spring Security
+
secure authentication
+
role-based authorization
```

At minimum:

```text
ROLE_ADMIN
```

Do not expose write endpoints publicly.

---

## 16. Content Publishing Workflow

Recommended:

```text
Admin
  |
  v
Create draft
  |
  v
Validate structured fields
  |
  v
Preview
  |
  v
Publish
  |
  v
PostgreSQL
  |
  v
Invalidate/revalidate affected public pages
```

Future status:

```text
DRAFT
PUBLISHED
ARCHIVED
```

This can be separate from recruitment lifecycle status.

Do not confuse:

- Publication status
- Application status
- Exam/result lifecycle

---

## 17. Update Workflow

Example deadline extension:

```text
Admin opens recruitment
        |
        v
Changes application_end_date
        |
        v
Adds update note
        |
        v
Save
        |
        v
PostgreSQL
        |
        v
Cache invalidation event
        |
        v
Public page updated
```

The update should preserve:

- existing ID
- existing slug
- existing canonical URL

Do not create a new duplicate recruitment merely because a date changed.

---

## 18. Related Content

The backend should make it possible to retrieve related items.

Example:

```text
SSC CGL Recruitment
    |
    +-- Related SSC jobs
    +-- Related graduate jobs
    +-- Other active government jobs
    +-- SSC CGL admit card
    +-- SSC CGL answer key
    +-- SSC CGL result
```

This supports:

- internal linking
- pages/session
- user navigation
- SEO

---

## 19. Database Transaction Boundaries

When creating a recruitment and its relationships:

```text
Recruitment
Organization
State
Categories
Qualifications
```

should be created/updated transactionally where appropriate.

Do not leave partially-created records if a relationship insert fails.

---

## 20. Caching Interaction

Backend should not assume every public request reaches it.

Public pages may be cached by Cloudflare/Next.js.

Therefore:

- Public GET endpoints should be safe to repeat.
- Admin mutations should trigger cache invalidation/revalidation.
- API responses intended for public caching must not contain private information.
- Admin endpoints must not be publicly cached.

---

## 21. Database Backup

This is a non-negotiable requirement once real content exists.

Maintain:

- Automated backup
- Off-platform backup
- Restore procedure
- Periodic restore testing

Do not rely on a free/temporary database tier as the sole copy.

Recommended conceptual flow:

```text
Railway PostgreSQL
       |
       v
Scheduled backup
       |
       v
Object storage / separate provider
```

---

## 22. PostgreSQL Hosting

Initial:

```text
Railway PostgreSQL
```

Advantages:

- Easy provisioning
- Same project as Spring Boot
- Simple connection variables
- No AWS RDS setup
- Low initial operational burden

When traffic/business grows, evaluate:

- Dedicated managed PostgreSQL
- AWS RDS
- Supabase/Postgres
- Other managed providers

Do not migrate merely because traffic increased; migrate when reliability, scale, cost, operational requirements, or backup requirements justify it.

---

## 23. Spring Boot Hosting

Initial:

```text
Railway
```

Application should be stateless where possible.

Do not store uploaded content on local application disk if it needs persistence.

Use environment variables for:

```text
DATABASE_URL
DATABASE_USERNAME
DATABASE_PASSWORD
ADMIN_SECRET
JWT/SESSION_SECRET
CLOUD/OBJECT_STORAGE credentials
```

Never commit secrets to Git.

---

## 24. Do Not Add Unnecessary Infrastructure

Initial backend should be:

```text
Spring Boot
   |
PostgreSQL
```

Do not add:

```text
Redis
Kafka
RabbitMQ
Elasticsearch
MongoDB
Kubernetes
```

unless a concrete requirement appears.

PostgreSQL is sufficient for:

- jobs
- state filtering
- category filtering
- qualification filtering
- organization filtering
- basic search
- sorting
- pagination
- lifecycle/status queries

---

## 25. Performance Requirements

Initial goals:

- Fast database queries.
- Pagination on large lists.
- No N+1 JPA queries.
- Proper indexes.
- Connection pooling.
- Avoid loading huge text/blob fields unnecessarily in listing queries.
- Use projections/DTOs where useful.
- Monitor slow queries.

A list page should not fetch every column and every relationship for thousands of jobs.

---

## 26. Content Integrity

Government notification information should be treated as authoritative-data-derived content.

Store official URLs.

Recommended fields:

```text
official_notification_url
official_website_url
apply_url
last_verified_at
```

Do not invent dates, vacancies, eligibility, or links.

If AI is used for extraction:

```text
PDF/official source
      |
      v
AI extraction
      |
      v
Admin verification
      |
      v
Publish
```

---

## 27. Future Automation

Potential future pipeline:

```text
Official government source
        |
        v
Notification detected
        |
        v
PDF/document extraction
        |
        v
AI structured extraction
        |
        v
Draft recruitment
        |
        v
Admin verification
        |
        v
Publish
        |
        v
Cache invalidation
        |
        v
Sitemap update
```

This is future functionality, not required for MVP.

---

## 28. Backend Development Order

Recommended implementation order:

1. Project skeleton
2. PostgreSQL connection
3. Flyway migrations
4. Organization
5. State
6. Category
7. Qualification
8. Recruitment
9. Relationships
10. CRUD/service layer
11. Public read APIs
12. Admin authentication
13. Admin write APIs
14. Search/filter/pagination
15. Results/admit-card/answer-key entities
16. Cache invalidation hooks
17. Backup automation
18. Monitoring

---

## 29. Backend Definition of Done

MVP backend is complete when:

- Recruitment can be created/edited through authenticated admin API.
- Recruitment has stable slug.
- Recruitment can be linked to state/category/organization/qualification.
- Public API can retrieve individual recruitment.
- Public API can retrieve latest jobs.
- Public API can retrieve jobs by state/category/organization/qualification.
- Closing-soon query works.
- Closed jobs remain accessible.
- Deadline extension updates the existing record.
- Admin writes are authenticated.
- Database migrations are version-controlled.
- Indexes exist for important query paths.
- Backup exists.
- No secrets are committed.
- Frontend can consume APIs cleanly.
