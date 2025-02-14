DO $$
    BEGIN
        CREATE TABLE IF NOT EXISTS internal_user (
             id SERIAL PRIMARY KEY,
             firstname VARCHAR(255),
             lastname VARCHAR(255),
             username VARCHAR(255) NOT NULL UNIQUE,
             email VARCHAR(255) NOT NULL UNIQUE,
             password_hash VARCHAR(255) NOT NULL,
             created_at TIMESTAMPTZ DEFAULT NOW() NOT NULL,
             created_by INTEGER,
             updated_at TIMESTAMPTZ DEFAULT NOW() NOT NULL,
             updated_by INTEGER,
             deleted_at TIMESTAMPTZ,
             CONSTRAINT fk_created_by FOREIGN KEY (created_by) REFERENCES internal_user(id),
             CONSTRAINT fk_updated_by FOREIGN KEY (updated_by) REFERENCES internal_user(id)
        );

        CREATE UNIQUE INDEX IF NOT EXISTS idx_user_email ON internal_user (email);
        CREATE UNIQUE INDEX IF NOT EXISTS idx_user_username ON internal_user (username);

        INSERT INTO internal_user (email, username, password_hash, created_at, created_by, updated_at, updated_by)
        VALUES ('admin@dundieawards.com', 'admin@dundieawards.com', '$2a$10$fhwS7d1WhhZqkmZpcx4vduF8dkMX7kjjLKwtL.YzAH0xi6A9NDyVq', NOW(), NULL, NOW(), NULL)
        ON CONFLICT (email) DO NOTHING;

    END $$;
