--liquibase formatted sql

--changeset system:1 dbms:postgresql
CREATE TABLE doppelkopf_games
(
	id       UUID PRIMARY KEY,
	game     JSONB NOT NULL
);
