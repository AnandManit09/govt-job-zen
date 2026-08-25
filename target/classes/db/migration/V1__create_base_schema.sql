CREATE TABLE organizations (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    website_url VARCHAR(500),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE states (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE qualifications (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE recruitments (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL UNIQUE,
    organization_id BIGINT NOT NULL,
    state_id BIGINT,
    notification_date DATE,
    application_start_date DATE,
    application_end_date DATE,
    vacancies INTEGER,
    qualification_details TEXT,
    age_limit VARCHAR(255),
    salary TEXT,
    application_fee VARCHAR(255),
    selection_process TEXT,
    description TEXT,
    official_notification_url VARCHAR(500),
    official_website_url VARCHAR(500),
    apply_url VARCHAR(500),
    status VARCHAR(50) NOT NULL,
    published BOOLEAN NOT NULL DEFAULT FALSE,
    featured BOOLEAN NOT NULL DEFAULT FALSE,
    meta_title VARCHAR(255),
    meta_description TEXT,
    canonical_url VARCHAR(500),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    published_at TIMESTAMP,
    CONSTRAINT fk_recruitments_org FOREIGN KEY (organization_id) REFERENCES organizations(id),
    CONSTRAINT fk_recruitments_state FOREIGN KEY (state_id) REFERENCES states(id)
);

CREATE TABLE recruitment_categories (
    recruitment_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    PRIMARY KEY (recruitment_id, category_id),
    CONSTRAINT fk_rc_recruitment FOREIGN KEY (recruitment_id) REFERENCES recruitments(id),
    CONSTRAINT fk_rc_category FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE recruitment_qualifications (
    recruitment_id BIGINT NOT NULL,
    qualification_id BIGINT NOT NULL,
    PRIMARY KEY (recruitment_id, qualification_id),
    CONSTRAINT fk_rq_recruitment FOREIGN KEY (recruitment_id) REFERENCES recruitments(id),
    CONSTRAINT fk_rq_qualification FOREIGN KEY (qualification_id) REFERENCES qualifications(id)
);

CREATE INDEX idx_recruitments_slug ON recruitments (slug);
CREATE INDEX idx_recruitments_status ON recruitments (status);
CREATE INDEX idx_recruitments_published ON recruitments (published);
CREATE INDEX idx_recruitments_published_at ON recruitments (published_at);
CREATE INDEX idx_recruitments_end_date ON recruitments (application_end_date);
CREATE INDEX idx_organizations_slug ON organizations (slug);
CREATE INDEX idx_states_slug ON states (slug);
CREATE INDEX idx_categories_slug ON categories (slug);
CREATE INDEX idx_qualifications_slug ON qualifications (slug);

INSERT INTO organizations (name, slug, description, website_url) VALUES
('SSC', 'ssc', 'Staff Selection Commission', 'https://ssc.nic.in'),
('UPSC', 'upsc', 'Union Public Service Commission', 'https://upsc.gov.in'),
('IBPS', 'ibps', 'Institute of Banking Personnel Selection', 'https://ibps.in'),
('RRB', 'rrb', 'Railway Recruitment Boards', 'https://indianrailways.gov.in'),
('MP Police', 'mp-police', 'Madhya Pradesh Police', 'https://example.com/mp-police');

INSERT INTO states (name, slug) VALUES
('All India', 'all-india'),
('Madhya Pradesh', 'madhya-pradesh'),
('Uttar Pradesh', 'uttar-pradesh'),
('Bihar', 'bihar'),
('Rajasthan', 'rajasthan');

INSERT INTO categories (name, slug) VALUES
('Central Government', 'central-government'),
('Banking', 'banking'),
('Railway', 'railway'),
('Police', 'police'),
('State Government', 'state-government');

INSERT INTO qualifications (name, slug) VALUES
('Graduate', 'graduate'),
('12th', '12th'),
('Diploma', 'diploma'),
('ITI', 'iti'),
('B.Tech', 'btech');

