create table products
(
    id int GENERATED BY DEFAULT AS IDENTITY primary key,
    name varchar(30) null,
    description varchar(150) null,
    price decimal null,
    date_of_creation TIMESTAMP default CURRENT_TIMESTAMP null,
    date_of_modification TIMESTAMP default CURRENT_TIMESTAMP null
);

CREATE TABLE currency (
    id int GENERATED BY DEFAULT AS IDENTITY primary key,
    value varchar(20) null
);

create table description
(
    id int GENERATED BY DEFAULT AS IDENTITY primary key,
    language varchar(150) null
);

create table product_currencies
(
    product_id int not null,
    currency_id int not null,
	foreign key (product_id) references products (id),
    foreign key (currency_id) references currency (id)
);

create table product_descriptions
(
    product_id int not null,
    description_id int not null,
	foreign key (product_id) references products (id),
	foreign key (description_id) references description (id)
);
