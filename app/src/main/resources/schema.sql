DROP TABLE IF EXISTS url_checks;
DROP TABLE IF EXISTS urls;



CREATE TABLE urls (
    id SERIAL PRIMARY KEY,
    url VARCHAR NOT NULL,
    created_at TIMESTAMP
);

CREATE TABLE url_checks
(
    id SERIAL PRIMARY KEY,
    url_id SERIAL REFERENCES urls (id),
    status_code INT NOT NULL,
    h1 VARCHAR NOT NULL,
    title VARCHAR NOT NULL,
    description VARCHAR NOT NULL,
    created_at TIMESTAMP NOT NULL
);



