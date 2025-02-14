DO $$
    BEGIN
        CREATE TABLE organizations (
           id SERIAL PRIMARY KEY,
           name VARCHAR(255) NOT NULL
        );

        CREATE TABLE employees (
           id SERIAL PRIMARY KEY,
           first_name VARCHAR(255) NOT NULL,
           last_name VARCHAR(255) NOT NULL,
           dundie_awards BIGINT DEFAULT 0,
           organization_id BIGINT NOT NULL,
           CONSTRAINT fk_organization FOREIGN KEY (organization_id) REFERENCES organizations(id)
        );
END $$;
