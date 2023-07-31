DROP TABLE IF EXISTS documents;
DROP TABLE IF EXISTS apostille;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS language_rate;
DROP TABLE IF EXISTS translator;
DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS users;


CREATE TABLE users
(
    id  		SERIAL PRIMARY KEY,
    user_name  		VARCHAR		         NOT NULL,
    user_password 	VARCHAR			     NOT NULL,
    enabled  		BOOLEAN DEFAULT TRUE NOT NULL
);
CREATE UNIQUE INDEX users_unique_name_idx ON users (user_name);

CREATE TABLE user_role
(
    user_id INTEGER      NOT NULL,
    role    VARCHAR		 NOT NULL,
    CONSTRAINT user_role_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE
);

CREATE TABLE orders (
	id 		SERIAL 		PRIMARY KEY,
	customer_name 	VARCHAR 	NOT NULL,
	customer_phone 	VARCHAR,
	customer_email 	VARCHAR,
	prepaid 		INTEGER     NOT NULL,
	surcharge 		INTEGER,
	summary_cost 	INTEGER,
	creation_date 	DATE 		DEFAULT NOW(),
	delivery_date 	DATE,
	status 			VARCHAR DEFAULT 'IN_WORK',
	note TEXT
);

CREATE TABLE translator (
	id SERIAL PRIMARY KEY,
	name VARCHAR NOT NULL,
	email VARCHAR NOT NULL,
	phone_number VARCHAR,
	available BOOLEAN DEFAULT TRUE
);

CREATE TABLE language_rate (
    translator_id INTEGER REFERENCES translator(id) ON DELETE CASCADE,
    id SERIAL PRIMARY KEY,
    language VARCHAR,
    common_rate INTEGER,
    hard_rate INTEGER,
    signs FLOAT,
    CONSTRAINT translator_language_idx UNIQUE (translator_id,language)
);

CREATE TABLE documents (
	order_id INTEGER REFERENCES orders(id) ON DELETE CASCADE NOT NULL,
	id SERIAL PRIMARY KEY,
	document_language VARCHAR NOT NULL,
	hard_complexity BOOLEAN DEFAULT FALSE,
	office_rate INTEGER NOT NULL,
	signs_number FLOAT,
	notarization INTEGER DEFAULT 0,
	office_cost INTEGER NOT NULL,
	translator_id INTEGER REFERENCES translator(id),
	translator_rate VARCHAR,
	translator_tax INTEGER
);

CREATE TABLE apostille (
	order_id INTEGER REFERENCES orders(id) ON DELETE CASCADE NOT NULL,
	id SERIAL PRIMARY KEY,
	title VARCHAR,
	submission_country VARCHAR,
	submission_department VARCHAR,
	apostille_cost INTEGER
);

