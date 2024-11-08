create table liquid_intake (
    id bigint not null auto_increment,
    datetime DATETIME DEFAULT CURRENT_TIMESTAMP not null,
    person_id varchar(30) not null,
    liquid varchar(50) not null,
    amount integer not null,
    healthy bit,
    primary key (id)
);
