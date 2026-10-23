# Query performance and indexing summary

This project uses a pragmatic indexing strategy tuned for the main public and admin query patterns:

- Latest public feed queries filter on `published = true` and sort by `published_at DESC`.
- State and category filters use join tables and filter on the related slug values.
- Lifecycle endpoints fetch published records by recruitment and publication date.

## Indexes added

The migration in `V3__add_performance_indexes.sql` adds the following index coverage:

- `idx_recruitments_published_published_at` on `(published, published_at DESC)`
- `idx_recruitments_published_state_published_at` on `(published, state_id, published_at DESC)`
- `idx_recruitments_status_published_at` on `(status, published_at DESC)`
- `idx_recruitment_categories_category_id` and `idx_recruitment_categories_recruitment_id`
- `idx_recruitment_qualifications_qualification_id` and `idx_recruitment_qualifications_recruitment_id`
- `idx_recruitment_results_published_published_at`
- `idx_recruitment_admit_cards_published_published_at`
- `idx_recruitment_answer_keys_published_published_at`

## Why these indexes matter

These indexes reduce table-scan risk for the most common list and filtered endpoints, especially when the dataset grows beyond a small seed set. They are intentionally focused on the actual access patterns in the app so the schema stays maintainable and avoids unnecessary index bloat.

## Known future tuning areas

- Large category or state landing pages may benefit from additional materialized views or derived counts if query volume increases.
- Full-text search is intentionally out of scope for this story and should be addressed separately if the site grows beyond simple filtering.
- The join-table indexes are tuned for recruitment-to-category and recruitment-to-qualification filtering, which is the main risk area for N+1 and expensive join behavior.
