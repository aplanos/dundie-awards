DO $$
    BEGIN

        CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

        CREATE TABLE activities (
            id UUID PRIMARY KEY,
            started_at TIMESTAMP,
            type VARCHAR(255) NOT NULL,
            status VARCHAR(255) NOT NULL,
            context JSONB,
            log TEXT,
            organization_id BIGINT NOT NULL,
            created_at TIMESTAMPTZ DEFAULT NOW() NOT NULL,
            updated_at TIMESTAMPTZ DEFAULT NOW(),
            CONSTRAINT fk_organization FOREIGN KEY (organization_id) REFERENCES organizations(id)
        );
    END $$;
