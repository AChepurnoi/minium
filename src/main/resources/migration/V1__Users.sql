CREATE TABLE users (
  id       SERIAL PRIMARY KEY NOT NULL,
  login    TEXT               NOT NULL,
  password TEXT               NOT NULL,
  role     TEXT               NOT NULL,
  active   BOOLEAN DEFAULT FALSE
);

