DROP TABLE IF EXISTS url_checks;
DROP TABLE IF EXISTS urls;



CREATE TABLE urls (
    id SERIAL PRIMARY KEY NOT NULL,
    url VARCHAR NOT NULL,
    created_at TIMESTAMP
);

CREATE TABLE url_checks
(
    id SERIAL PRIMARY KEY NOT NULL,
    url_id SERIAL REFERENCES urls (id),
    status_code INT,
    h1 VARCHAR,
    title VARCHAR,
    description VARCHAR,
    created_at TIMESTAMP
);








