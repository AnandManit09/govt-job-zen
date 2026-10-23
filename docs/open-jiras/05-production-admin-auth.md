# Story 05: Production-ready admin authentication

## Summary
As an application operator, I want admin authentication to be environment-safe and deployment-ready so that credentials are not hardcoded and the platform is secure enough for real use.

## Background
The project currently uses basic in-memory admin credentials for local development. This is acceptable for early MVP work but not appropriate for deployment or shared environments.

## User Story
As a system administrator, I need secure and configurable admin credentials so that only authorized users can manage job content in production.

## Scope
In scope:
- environment-based config for admin credentials
- secure default configuration pattern
- role structure or admin-only authorization model
- documentation for deployment and rotation

Out of scope:
- full OAuth provider integration
- social login or SSO federation
- frontend authentication UX

## Functional Requirements
1. Admin credentials are not embedded in source code or checked into repositories.
2. The app supports secure configuration via environment variables or external secret management.
3. Unauthorized access is rejected with clear authentication failures.
4. The authentication setup remains compatible with existing admin endpoints.

## Technical Requirements
- Replace or wrap the in-memory user config with environment-backed configuration.
- Ensure secrets are not logged or exposed in startup output.
- Keep the authentication model minimal but production-safe.
- Document expected environment variables and sample values.

## Acceptance Criteria
1. The backend reads admin credentials from a secure external source instead of hardcoded values.
2. The app still enforces admin authentication on protected routes.
3. A valid admin user can access admin endpoints.
4. An invalid user cannot access the admin APIs.
5. Deployment documentation clearly explains how to configure admin auth for production.

## Definition of Done
- Authentication configuration is production-safe.
- Docs exist for local and deployment environment setup.
- Security review confirms no credentials are left in source-controlled files.

## Test Plan
### Unit tests
- should load admin username/password from configured environment values
- should deny access with invalid credentials
- should allow access with valid credentials

### Integration tests
- `GET /api/admin/recruitments` with valid credentials returns 200
- same endpoint with no credentials returns 401
- invalid credentials return 401/403

## Code Review Checklist
- Are secrets handled outside source control?
- Is the security configuration clear and intentionally limited?
- Are admin routes protected at the correct layer?
- Does the implementation avoid unnecessary complexity?

## QA Notes
QA should verify the behavior against both valid and invalid credentials and ensure the app can boot without exposing secret values in logs.
