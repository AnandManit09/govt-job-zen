CREATE INDEX idx_recruitments_published_published_at
    ON recruitments (published, published_at DESC);

CREATE INDEX idx_recruitments_published_state_published_at
    ON recruitments (published, state_id, published_at DESC);

CREATE INDEX idx_recruitments_status_published_at
    ON recruitments (status, published_at DESC);

CREATE INDEX idx_recruitment_categories_category_id
    ON recruitment_categories (category_id);

CREATE INDEX idx_recruitment_categories_recruitment_id
    ON recruitment_categories (recruitment_id);

CREATE INDEX idx_recruitment_qualifications_qualification_id
    ON recruitment_qualifications (qualification_id);

CREATE INDEX idx_recruitment_qualifications_recruitment_id
    ON recruitment_qualifications (recruitment_id);

CREATE INDEX idx_recruitment_results_published_published_at
    ON recruitment_results (published, published_at DESC);

CREATE INDEX idx_recruitment_admit_cards_published_published_at
    ON recruitment_admit_cards (published, published_at DESC);

CREATE INDEX idx_recruitment_answer_keys_published_published_at
    ON recruitment_answer_keys (published, published_at DESC);
