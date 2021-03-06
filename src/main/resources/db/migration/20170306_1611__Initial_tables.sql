-- CREATE SCHEMA `quotes` DEFAULT CHARACTER SET utf8 ;
DROP TABLE ticker IF EXISTS;
CREATE TABLE ticker
(
    id BIGINT(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    shared_id INT(11),
    dataset_code VARCHAR(30),
    database_code VARCHAR(50),
    name TEXT,
    description LONGTEXT,
    refreshed_at DATETIME,
    newest_available_date DATE,
    oldest_available_date DATE,
    column_names VARCHAR(500),
    frequency VARCHAR(30),
    type VARCHAR(25),
    premium TINYINT(1),
    database_id BIGINT(20)
);
CREATE UNIQUE INDEX ticker_id_uindex ON ticker (id);

DROP TABLE data IF EXISTS;
CREATE TABLE data
(
    id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    symbol VARCHAR(20),
    date DATE,
    open DOUBLE,
    high DOUBLE,
    low DOUBLE,
    close DOUBLE,
    volume BIGINT(20),
    ex_dividend DOUBLE,
    split_ratio DOUBLE,
    adj_open DOUBLE,
    adj_high DOUBLE,
    adj_low DOUBLE,
    adj_close DOUBLE,
    adj_volume BIGINT(20)
);
CREATE INDEX data_date_index ON data (date);
CREATE UNIQUE INDEX data_id_uindex ON data (id);
CREATE INDEX data_symbol_index ON data (symbol);