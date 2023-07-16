DROP TABLE IF EXISTS documents;
DROP TABLE IF EXISTS apostille;
DROP TABLE IF EXISTS orders;
DROP TYPE IF EXISTS order_status;
DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS translator;

CREATE TABLE users
(
    user_id  		SERIAL PRIMARY KEY,
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
    FOREIGN KEY (user_id) REFERENCES USERS (user_id) ON DELETE CASCADE
);


CREATE TYPE order_status AS ENUM ('IN WORK', 'COMPLETED');

CREATE TABLE orders (
	order_id 		SERIAL 		PRIMARY KEY,
	customer_name 	VARCHAR 	NOT NULL,
	customer_phone 	VARCHAR,
	customer_email 	VARCHAR,
	prepaid 		INTEGER 	DEFAULT 0,
	surcharge 		INTEGER 	DEFAULT 0,
	summary_cost 	INTEGER 	DEFAULT 0,
	creation_date 	DATE 		DEFAULT NOW(),
	delivery_date 	DATE,
	status 			order_status DEFAULT 'IN WORK',
	note TEXT
);

CREATE TABLE documents (
	order_id INTEGER REFERENCES orders(order_id) ON DELETE CASCADE NOT NULL,
	document_id SERIAL PRIMARY KEY,
	document_language VARCHAR NOT NULL,
	office_rate INTEGER NOT NULL,
	signs_number INTEGER,
	notarization INTEGER,
	office_cost INTEGER NOT NULL,
	translator VARCHAR,
	translator_rate VARCHAR,
	translator_tax INTEGER
);

CREATE TABLE apostille (
	order_id INTEGER REFERENCES orders(order_id) ON DELETE CASCADE NOT NULL,
	apostille_id SERIAL PRIMARY KEY,
	title VARCHAR,
	submission_country VARCHAR,
	submission_department VARCHAR,
	apostlle_cost INTEGER
);

CREATE TABLE translator (
	translator_id SERIAL PRIMARY KEY,
	name VARCHAR NOT NULL,
	email VARCHAR NOT NULL,
	phone_number VARCHAR,
	language VARCHAR NOT NULL,
	translator_rate VARCHAR
);