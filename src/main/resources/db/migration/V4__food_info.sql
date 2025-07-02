CREATE TABLE food_info (
    name VARCHAR(30) NOT NULL,               
    `key` VARCHAR(30) NOT NULL,           
    value VARCHAR(30) NOT NULL,
    PRIMARY KEY (name, `key`, value)
);