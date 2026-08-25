# Sample data for testing the backend

This file contains ready-to-use sample payloads for posting recruitment records through Postman or curl.

## Base URL

```text
http://localhost:8080
```

## Admin credentials

Default admin user from application properties:

```text
Username: admin
Password: admin123
```

## 1) Create a recruitment via curl

### Curl example 1

```bash
curl -u admin:admin123 -X POST http://localhost:8080/api/admin/recruitments \
  -H "Content-Type: application/json" \
  -d '{
    "title": "SSC CGL 2026",
    "slug": "ssc-cgl-2026",
    "description": "Staff Selection Commission Combined Graduate Level recruitment for graduates.",
    "organizationName": "SSC",
    "stateName": "All India",
    "notificationDate": "2026-01-15",
    "applicationStartDate": "2026-02-01",
    "applicationEndDate": "2026-02-28",
    "vacancies": 1200,
    "qualificationDetails": "Bachelor\'s degree from a recognized university",
    "ageLimit": "18-32 years",
    "salary": "Level-7 pay matrix",
    "applicationFee": "Rs. 100",
    "selectionProcess": "Tier I, Tier II, Document Verification",
    "officialNotificationUrl": "https://ssc.nic.in",
    "officialWebsiteUrl": "https://ssc.nic.in",
    "applyUrl": "https://example.com/apply/ssc-cgl-2026",
    "status": "UPCOMING",
    "published": true,
    "featured": true,
    "categoryNames": ["Central Government", "Staff Selection"],
    "qualificationNames": ["Graduate"]
  }'
```

### Curl example 2

```bash
curl -u admin:admin123 -X POST http://localhost:8080/api/admin/recruitments \
  -H "Content-Type: application/json" \
  -d '{
    "title": "UPSC Civil Services 2026",
    "slug": "upsc-civil-services-2026",
    "description": "Union Public Service Commission UPSC Civil Services recruitment notification.",
    "organizationName": "UPSC",
    "stateName": "All India",
    "notificationDate": "2026-02-10",
    "applicationStartDate": "2026-03-01",
    "applicationEndDate": "2026-03-31",
    "vacancies": 861,
    "qualificationDetails": "Graduate in any discipline",
    "ageLimit": "21-32 years",
    "salary": "Rs. 56100-177500",
    "applicationFee": "Rs. 100",
    "selectionProcess": "Prelims, Mains, Interview",
    "officialNotificationUrl": "https://upsc.gov.in",
    "officialWebsiteUrl": "https://upsc.gov.in",
    "applyUrl": "https://example.com/apply/upsc-civil-services-2026",
    "status": "ACTIVE",
    "published": true,
    "featured": true,
    "categoryNames": ["Civil Services", "Central Government"],
    "qualificationNames": ["Graduate"]
  }'
```

### Curl example 3

```bash
curl -u admin:admin123 -X POST http://localhost:8080/api/admin/recruitments \
  -H "Content-Type: application/json" \
  -d '{
    "title": "MP Police Constable Recruitment 2026",
    "slug": "mp-police-constable-recruitment-2026",
    "description": "Madhya Pradesh Police recruitment for constable posts.",
    "organizationName": "MP Police",
    "stateName": "Madhya Pradesh",
    "notificationDate": "2026-04-05",
    "applicationStartDate": "2026-04-20",
    "applicationEndDate": "2026-05-10",
    "vacancies": 4500,
    "qualificationDetails": "12th pass from recognized board",
    "ageLimit": "18-28 years",
    "salary": "Level-3 pay scale",
    "applicationFee": "Rs. 250",
    "selectionProcess": "Written Exam, Physical Test, Document Verification",
    "officialNotificationUrl": "https://example.com/mp-police-constable",
    "officialWebsiteUrl": "https://example.com/mp-police",
    "applyUrl": "https://example.com/apply/mp-police-constable-2026",
    "status": "UPCOMING",
    "published": true,
    "featured": false,
    "categoryNames": ["Police", "State Government"],
    "qualificationNames": ["12th"]
  }'
```

### Curl example 4

```bash
curl -u admin:admin123 -X POST http://localhost:8080/api/admin/recruitments \
  -H "Content-Type: application/json" \
  -d '{
    "title": "IBPS PO 2026",
    "slug": "ibps-po-2026",
    "description": "Institute of Banking Personnel Selection Probationary Officer recruitment notification.",
    "organizationName": "IBPS",
    "stateName": "All India",
    "notificationDate": "2026-05-18",
    "applicationStartDate": "2026-06-03",
    "applicationEndDate": "2026-06-22",
    "vacancies": 5200,
    "qualificationDetails": "Graduate in any discipline",
    "ageLimit": "20-30 years",
    "salary": "Rs. 48480-85950",
    "applicationFee": "Rs. 850",
    "selectionProcess": "Prelims, Mains, Interview",
    "officialNotificationUrl": "https://example.com/ibps-po-2026",
    "officialWebsiteUrl": "https://ibps.in",
    "applyUrl": "https://example.com/apply/ibps-po-2026",
    "status": "ACTIVE",
    "published": true,
    "featured": true,
    "categoryNames": ["Banking", "Central Government"],
    "qualificationNames": ["Graduate"]
  }'
```

### Curl example 5

```bash
curl -u admin:admin123 -X POST http://localhost:8080/api/admin/recruitments \
  -H "Content-Type: application/json" \
  -d '{
    "title": "RRB NTPC 2026",
    "slug": "rrb-ntpc-2026",
    "description": "Railway Recruitment Boards Non-Technical Popular Categories recruitment.",
    "organizationName": "RRB",
    "stateName": "All India",
    "notificationDate": "2026-07-01",
    "applicationStartDate": "2026-07-12",
    "applicationEndDate": "2026-08-02",
    "vacancies": 35000,
    "qualificationDetails": "12th pass / graduate as applicable",
    "ageLimit": "18-33 years",
    "salary": "Rs. 19900-65700",
    "applicationFee": "Rs. 250",
    "selectionProcess": "CBT 1, CBT 2, Skill Test, Document Verification",
    "officialNotificationUrl": "https://example.com/rrb-ntpc-2026",
    "officialWebsiteUrl": "https://indianrailways.gov.in",
    "applyUrl": "https://example.com/apply/rrb-ntpc-2026",
    "status": "UPCOMING",
    "published": true,
    "featured": false,
    "categoryNames": ["Railway", "Central Government"],
    "qualificationNames": ["12th", "Graduate"]
  }'
```

---

## 2) View latest published jobs

```bash
curl http://localhost:8080/api/public/recruitments/latest?limit=10
```

---

## 3) Postman setup

### Method

- POST

### URL

```text
http://localhost:8080/api/admin/recruitments
```

### Authorization

- Type: Basic Auth
- Username: `admin`
- Password: `admin123`

### Body

Use raw JSON with one of the payloads above.

Example body:

```json
{
  "title": "SSC CGL 2026",
  "slug": "ssc-cgl-2026",
  "description": "Staff Selection Commission Combined Graduate Level recruitment for graduates.",
  "organizationName": "SSC",
  "stateName": "All India",
  "notificationDate": "2026-01-15",
  "applicationStartDate": "2026-02-01",
  "applicationEndDate": "2026-02-28",
  "vacancies": 1200,
  "qualificationDetails": "Bachelor's degree from a recognized university",
  "ageLimit": "18-32 years",
  "salary": "Level-7 pay matrix",
  "applicationFee": "Rs. 100",
  "selectionProcess": "Tier I, Tier II, Document Verification",
  "officialNotificationUrl": "https://ssc.nic.in",
  "officialWebsiteUrl": "https://ssc.nic.in",
  "applyUrl": "https://example.com/apply/ssc-cgl-2026",
  "status": "UPCOMING",
  "published": true,
  "featured": true,
  "categoryNames": ["Central Government", "Staff Selection"],
  "qualificationNames": ["Graduate"]
}
```

---

## 4) Useful note

The app expects the database schema to already exist. If you are running the application with Flyway, the migration script in [src/main/resources/db/migration/V1__create_base_schema.sql](../src/main/resources/db/migration/V1__create_base_schema.sql) will create tables automatically on startup.
