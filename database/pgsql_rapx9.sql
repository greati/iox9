CREATE USER rapx9 WITH PASSWORD '123';

CREATE TABLE entity (
	identifier VARCHAR(50) PRIMARY KEY,
	registration_date DATE NOT NULL,
	model VARCHAR(50),
	brand VARCHAR(50),
	color VARCHAR(50),
	situationCode VARCHAR(20),
	situation BOOLEAN,
	yearModel INTEGER,
	sinespDate TIMESTAMP,
	price DECIMAL,
	fipeDate TIMESTAMP
);

SELECT * FROM entity

CREATE TABLE io_record (
	identifier_entity VARCHAR(50) NOT NULL,
	instant TIMESTAMP NOT NULL,
	io_type INT NOT NULL,
	PRIMARY KEY (identifier_entity, instant),
	FOREIGN KEY (identifier_entity) REFERENCES entity(identifier)
);

GRANT ALL PRIVILEGES ON entity TO rapx9;
GRANT ALL PRIVILEGES ON io_record TO rapx9;