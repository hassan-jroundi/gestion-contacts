-- CREATE SCHEMA  IF NOT EXISTS gcdb;

CREATE TABLE  IF NOT EXISTS   contacts (
	id INT PRIMARY KEY NOT NULL,
	nom VARCHAR(50) NOT NULL,
	prenom VARCHAR(50) NOT NULL,
	adresse VARCHAR(100) NOT NULL,
	numero_tva VARCHAR(50),
	type_contact VARCHAR(2) NOT NULL
);

CREATE TABLE  IF NOT EXISTS   entreprises (
	id INT PRIMARY KEY NOT NULL,
	adresse VARCHAR(100) NOT NULL,
	numero_tva VARCHAR(50) NOT NULL
);

CREATE TABLE  IF NOT EXISTS   contact_entreprise (
	id INT PRIMARY KEY NOT NULL,
	id_contact INT NOT NULL,
	id_entreprise INT NOT NULL,

	FOREIGN KEY (id_contact) REFERENCES contacts(id),
	FOREIGN KEY (id_entreprise) REFERENCES entreprises(id)
);

INSERT INTO entreprises (id, adresse, numero_tva) VALUES (1, 'PARIS', 'FR32123456789');
INSERT INTO entreprises (id, adresse, numero_tva) VALUES (2, 'PARIS', 'FR32123452535');
INSERT INTO entreprises (id, adresse, numero_tva) VALUES (3, 'LYON', 'FR32123456562');

INSERT INTO contacts (id, nom, prenom, adresse, numero_tva, type_contact) VALUES (1, 'SMITH', 'JOHN', 'PARIS', 'FR32125632547', 'F');
INSERT INTO contacts (id, nom, prenom, adresse, numero_tva, type_contact) VALUES (2, 'GARCIA', 'ERIC', 'LYON', null, 'E');

INSERT INTO contact_entreprise (id, id_contact, id_entreprise) VALUES (1, 1, 1);
INSERT INTO contact_entreprise (id, id_contact, id_entreprise) VALUES (2, 1, 2);
INSERT INTO contact_entreprise (id, id_contact, id_entreprise) VALUES (3, 2, 3);
