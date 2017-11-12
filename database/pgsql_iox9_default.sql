CREATE USER iox9 WITH PASSWORD '123';

CREATE TABLE entity (
	identifier VARCHAR(50) PRIMARY KEY,
	registration_date DATE NOT NULL
);

CREATE TABLE io_record (
	identifier_entity VARCHAR(50) NOT NULL,
	instant TIMESTAMP NOT NULL,
	io_type INT NOT NULL,
	PRIMARY KEY (identifier_entity, instant),
	FOREIGN KEY (identifier_entity) REFERENCES entity(identifier)
);
