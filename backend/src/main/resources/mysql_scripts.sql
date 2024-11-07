create database product_db;

use product_db;

create table user(
	id int primary key auto_increment,
	email varchar(500) not null unique,
	password varchar(500) not null
);

create table category(
	id int primary key auto_increment,
    name varchar(500) not null,
    description varchar(1500) not null
);

create table product_category(
	id int primary key auto_increment,
	category_id int,
    product_id int
);

create table product(
	id int primary key auto_increment,
    name varchar(500) not null,
    description varchar(1500) not null,
    price decimal(10, 2) not null
);

alter table product_category add constraint fk_category_product_category foreign key (category_id) references category(id) on delete cascade;
alter table product_category add constraint fk_product_product_category foreign key (product_id) references product(id) on delete cascade;