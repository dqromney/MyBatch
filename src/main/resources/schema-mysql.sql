DROP TABLE IF EXISTS people;

CREATE TABLE people (
  person_id BIGINT NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(20) NULL,
  last_name VARCHAR(20) NULL,
  PRIMARY KEY (person_id),
  UNIQUE INDEX person_id_UNIQUE (person_id ASC)
);
