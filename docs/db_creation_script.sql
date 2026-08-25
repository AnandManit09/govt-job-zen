Create Database govt_job_zen;

CREATE USER myapp_user WITH PASSWORD 'myapp_password';

GRANT ALL PRIVILEGES ON DATABASE govt_job_zen TO myapp_user;

GRANT ALL ON SCHEMA public TO myapp_user;

SELECT datname FROM govt_job_zen;
