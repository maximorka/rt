CREATE TABLE customer(
id IDENTITY PRIMARY KEY,
name VARCHAR(100) NOT NULL,
address VARCHAR(200) NOT NULL
);

CREATE TABLE company(
id IDENTITY PRIMARY KEY,
name VARCHAR(100) NOT NULL,
address VARCHAR(200) NOT NULL
);
CREATE TABLE developer(
id IDENTITY PRIMARY KEY,
name VARCHAR(100) NOT NULL,
surname VARCHAR(200) NOT NULL,
age INTEGER NOT NULL ,
sex VARCHAR(10) NOT NULL,
company_id BIGINT,
salary INTEGER,
FOREIGN KEY (company_id) REFERENCES company(id),
CONSTRAINT sex_enum CHECK (sex IN ('male','famale','unknown')),
CONSTRAINT age_period CHECK (age BETWEEN 1 AND 130)
);

CREATE TABLE project(
id IDENTITY PRIMARY KEY,
name VARCHAR(100) NOT NULL,
description VARCHAR(200),
company_id BIGINT,
customer_id BIGINT,
cost INTEGER,
date Date,
FOREIGN KEY (company_id) REFERENCES company(id),
FOREIGN KEY (customer_id) REFERENCES customer(id)
);


CREATE TABLE skills(
id IDENTITY PRIMARY KEY,
kategory VARCHAR(100) NOT NULL,
level VARCHAR(100) not NULL,
CONSTRAINT level_enum CHECK(level IN ('junior','middle', 'senior'))
);

--settinds
CREATE TABLE developer_project(
develop_id BIGINT NOT NULL,
project_id BIGINT NOT NULL,
PRIMARY KEY (develop_id, project_id),
FOREIGN KEY (develop_id) REFERENCES developer(id) ON DELETE CASCADE,
FOREIGN KEY (project_id) REFERENCES project(id) ON DELETE CASCADE
);


CREATE TABLE developer_skills(
dev_id BIGINT NOT NULL,
skills_id BIGINT NOT NULL,
PRIMARY KEY (dev_id, skills_id),
FOREIGN KEY (dev_id) REFERENCES developer(id) ON DELETE CASCADE,
FOREIGN KEY (skills_id) REFERENCES skills(id) ON DELETE CASCADE
);

