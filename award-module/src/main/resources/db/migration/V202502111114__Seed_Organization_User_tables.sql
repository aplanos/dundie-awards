DO $$
    BEGIN
        WITH pikashu AS (
            INSERT INTO organizations (name) VALUES ('Pikashu') RETURNING id
        ), squanchy AS (
            INSERT INTO organizations (name) VALUES ('Squanchy') RETURNING id
        )
        INSERT INTO employees (first_name, last_name, organization_id) VALUES
            ('John', 'Doe', (SELECT id FROM pikashu)),
            ('Jane', 'Smith', (SELECT id FROM pikashu)),
            ('Creed', 'Braton', (SELECT id FROM pikashu)),
            ('Michael', 'Scott', (SELECT id FROM squanchy)),
            ('Dwight', 'Schrute', (SELECT id FROM squanchy)),
            ('Jim', 'Halpert', (SELECT id FROM squanchy)),
            ('Pam', 'Beesley', (SELECT id FROM squanchy));

    END $$;
