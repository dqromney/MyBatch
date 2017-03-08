
CREATE TABLE `securities`.`symbol` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `symbol` VARCHAR(45) NOT NULL COMMENT 'security symbol, i.e. IBM, AAPL, etc..',
  `type` INT(3) UNSIGNED NOT NULL COMMENT 'type: 1=stocks, 2=index, 3=futures, etc..',
  PRIMARY KEY (`id`));


CREATE TABLE `securities`.`daily` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Identifier',
  `symbol_id` INT NOT NULL,
  `date` DATE NOT NULL COMMENT 'date of quote',
  `open` DECIMAL(10) NULL COMMENT 'opening price',
  `high` DECIMAL(10) NULL COMMENT 'high of the day',
  `low` DECIMAL(10) NULL COMMENT 'low of the day',
  `close` DECIMAL(10) NULL COMMENT 'closing price',
  `volume` INT NULL DEFAULT 0 COMMENT 'volume for the day',
  `open_interest` INT NULL DEFAULT 0 COMMENT 'open interest for the day',
  `last_updated` DATETIME NOT NULL,
  PRIMARY KEY (`id`, `date`, `symbol_id`),
  INDEX `symbol_id_idx` (`symbol_id` ASC),
  CONSTRAINT `symbol_id`
    FOREIGN KEY (`symbol_id`)
    REFERENCES `securities`.`symbol` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);

-- INSERT INTO `securities`.`daily` (`symbol_id`, `date`, `open`, `high`, `low`, `close`, `volume`, `open_interest`, `last_updated`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);