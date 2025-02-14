DO $$
    BEGIN
        CREATE TABLE quartz_jobs (
             id BIGSERIAL PRIMARY KEY,
             job_name VARCHAR(255) NOT NULL UNIQUE,
             cron_expression VARCHAR(255) NOT NULL,
             job_status VARCHAR(255) NOT NULL,
             created_at TIMESTAMP DEFAULT  NOW() NOT NULL,
             updated_at TIMESTAMP DEFAULT NOW() NOT NULL
        );
    END $$;
