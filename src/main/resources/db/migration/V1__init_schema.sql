CREATE SCHEMA IF NOT EXISTS app;

CREATE TABLE app.discount_cards (
                                    id SERIAL PRIMARY KEY,
                                    number INTEGER,
                                    discount_amount NUMERIC
);

CREATE TABLE app.checks (
                            uuid UUID PRIMARY KEY,
                            creation_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            discount_card_id INTEGER,
                            FOREIGN KEY (discount_card_id) REFERENCES app.discount_cards(id)
);

CREATE TABLE app.items (
                           uuid UUID PRIMARY KEY,
                           check_uuid UUID REFERENCES app.checks(uuid) ON DELETE CASCADE,
                           quantity INTEGER,
                           price NUMERIC,
                           discount NUMERIC,
                           total NUMERIC
);

CREATE INDEX idx_items_check_uuid ON app.items(check_uuid);

CREATE TABLE app.products (
                              id SERIAL PRIMARY KEY,
                              name VARCHAR(255),
                              price NUMERIC,
                              wholesale BOOLEAN
);