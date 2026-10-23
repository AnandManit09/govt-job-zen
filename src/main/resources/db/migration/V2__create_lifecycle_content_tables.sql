CREATE TABLE recruitment_results (
    id BIGSERIAL PRIMARY KEY,
    recruitment_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    document_url VARCHAR(500),
    release_date DATE,
    published BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    published_at TIMESTAMP,
    CONSTRAINT fk_recruitment_results_recruitment FOREIGN KEY (recruitment_id) REFERENCES recruitments(id)
);

CREATE TABLE recruitment_admit_cards (
    id BIGSERIAL PRIMARY KEY,
    recruitment_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    document_url VARCHAR(500),
    release_date DATE,
    published BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    published_at TIMESTAMP,
    CONSTRAINT fk_recruitment_admit_cards_recruitment FOREIGN KEY (recruitment_id) REFERENCES recruitments(id)
);

CREATE TABLE recruitment_answer_keys (
    id BIGSERIAL PRIMARY KEY,
    recruitment_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    document_url VARCHAR(500),
    release_date DATE,
    published BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    published_at TIMESTAMP,
    CONSTRAINT fk_recruitment_answer_keys_recruitment FOREIGN KEY (recruitment_id) REFERENCES recruitments(id)
);

CREATE INDEX idx_recruitment_results_recruitment ON recruitment_results (recruitment_id);
CREATE INDEX idx_recruitment_results_published ON recruitment_results (published);
CREATE INDEX idx_recruitment_results_slug ON recruitment_results (slug);
CREATE INDEX idx_recruitment_admit_cards_recruitment ON recruitment_admit_cards (recruitment_id);
CREATE INDEX idx_recruitment_admit_cards_published ON recruitment_admit_cards (published);
CREATE INDEX idx_recruitment_admit_cards_slug ON recruitment_admit_cards (slug);
CREATE INDEX idx_recruitment_answer_keys_recruitment ON recruitment_answer_keys (recruitment_id);
CREATE INDEX idx_recruitment_answer_keys_published ON recruitment_answer_keys (published);
CREATE INDEX idx_recruitment_answer_keys_slug ON recruitment_answer_keys (slug);
