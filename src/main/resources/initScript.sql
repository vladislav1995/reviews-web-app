CREATE SCHEMA IF NOT EXISTS apollo_federation;

SET search_path TO apollo_federation;

CREATE TABLE IF NOT EXISTS apollo_federation.review(
    id SERIAL PRIMARY KEY,
    text VARCHAR(100) NOT NULL,
    userName VARCHAR (100) NOT NULL,
    filmId INT,
    userId INT
);