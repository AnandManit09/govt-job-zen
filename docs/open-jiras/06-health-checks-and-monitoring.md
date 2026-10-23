# Story 06: Health checks and monitoring

## Summary
As a platform operator, I want health and readiness endpoints so that I can confirm the application is healthy and properly connected to its dependencies before traffic is allowed.

## Background
The backend currently has no explicit health or readiness contract. This is required for release confidence, deployment gates, and operational monitoring.

## User Story
As a developer or operator, I need health endpoints so that I can verify the application and its database connectivity before or during deployment.

## Scope
In scope:
- `/actuator/health`
- readiness/liveness endpoints
- database connectivity check
- basic monitoring metadata for deployment tooling

Out of scope:
- advanced dashboards or alerts
- metrics platform setup
- distributed tracing beyond standard app monitoring

## Functional Requirements
1. The application exposes a health endpoint for dependency status.
2. The health endpoint reflects database connectivity and application status.
3. Readiness and liveness endpoints are available for deployment checks.
4. The app fails safely and returns clear status information, not silent errors.

## Technical Requirements
- Add Spring Boot Actuator and required configuration.
- Expose only the minimum necessary endpoints publicly.
- Ensure health checks do not leak sensitive operational information.
- Document how operators use these endpoints during deploy and rollback.

## Acceptance Criteria
1. `/actuator/health` returns 200 when the app is healthy.
2. If the database is unavailable, the health endpoint reflects an unhealthy state.
3. Readiness and liveness endpoints can be used by deployment tooling.
4. The endpoint output remains minimal and safe to expose publicly.

## Definition of Done
- Health and readiness endpoints are active and tested.
- Documentation explains their purpose and expected response.
- Build and deployment process includes a health check gate.

## Test Plan
### Unit tests
- should report UP status when dependencies are healthy
- should report DOWN status when DB access fails

### Integration tests
- `/actuator/health` returns 200 for a healthy app
- database outage is reported without crashing the app

## Code Review Checklist
- Is the actuator exposure level appropriate for the environment?
- Are health checks limited to safe information?
- Is the configuration explicit and not broad or overexposed?
- Does the monitoring setup support deployment automation?

## QA Notes
QA should validate both the healthy state and failure state to ensure the monitoring endpoints accurately reflect the system’s true runtime state.
