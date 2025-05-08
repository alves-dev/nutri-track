create table nutrient_weight (
    id bigint not null auto_increment,
    datetime DATETIME DEFAULT CURRENT_TIMESTAMP not null,
    person_id varchar(30) not null,
    meal varchar(20) not null,
    nutrient_type varchar(20) not null,
    weight integer,
    primary key (id)
);