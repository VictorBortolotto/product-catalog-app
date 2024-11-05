create database product_db;

use product_db;

create table category(
	id int primary key auto_increment,
    name varchar(500) not null,
    description varchar(1500) not null
);

create table product_category(
	category_id int,
    product_id int,
    primary key (category_id, product_id)
);

create table product(
	id int primary key auto_increment,
    name varchar(500) not null,
    description varchar(1500) not null,
    price decimal(10, 2) not null
);

alter table product_category add constraint pk_category_product_category foreign key (category_id) references category(id);
alter table product_category add constraint pk_product_product_category foreign key (product_id) references product(id);