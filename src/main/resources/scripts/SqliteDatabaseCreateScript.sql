CREATE TABLE dictionary_of_person_status (id integer PRIMARY KEY, name char (50));
create table dictionary_of_service_status (id integer PRIMARY KEY AUTOINCREMENT, name char (50));
CREATE TABLE dictionary_of_city (id integer PRIMARY KEY AUTOINCREMENT, name char (50));
CREATE TABLE dictionary_of_state (id integer PRIMARY KEY AUTOINCREMENT, name char (50));
create table address (
	id integer not null PRIMARY KEY AUTOINCREMENT,
	city_id integer not null,
	post_code char(6) not null,
	street char(50) not null,
	state_id integer not null,
	foreign key(state_id) references dictionary_of_state(id)
);

create table person (
	id integer not null PRIMARY KEY AUTOINCREMENT,
	login char(50) unique,
	password char(50),
	first_name char(50) not null,
	last_name char(50) not null,
	person_status_id integer,
	address_id integer,
	phone_number char(15),
	email char(50),
	nip char(15),
	regon char(20),
	foreign key(person_status_id) references dictionary_of_person_status(id),
	foreign key(address_id) references address(id)
);

create table service (
	id integer not null primary key autoincrement,
	client_id integer not null,
	service_name char(255) not null,
	info char(255) not null,
	date_of_order date not null,
	date_of_receipt date,
	service_status_id integer not null,
	assigned_person_id int
);
	
create table login_history (
	id integer not null primary key autoincrement,
	person_id integer not null,
	login_date datetime not null,
	foreign key(person_id) references person(id)
);

create table department (
	id integer not null primary key autoincrement,
	address_id int not null,
	foreign key(address_id) references address(id)
);

create table facture (
	id integer not null primary key autoincrement,
	facture blob not null
);

create table service_history(
	id integer not null primary key autoincrement,
	client_id integer not null,
	service_name char(255) not null,
	info char(255) not null,
	date_of_order date not null,
	date_of_receipt date not null,
	department_id integer not null,
	assigned_person_id int,
	facture_id integer not null,
	foreign key(client_id) references person(id),
	foreign key(assigned_person_id) references person(id),
	foreign key(department_id) references department(id),
	foreign key(facture_id) references facture(id)
);

create table history_of_service_changes (
	id integer not null primary key autoincrement,
	date_of_change date not null,
	service_id integer not null,
	wpis char(255),
	foreign key(service_id) references service_history (id)
);

create table enviroment (
	id integer not null primary key autoincrement,
	category char(255) not null,
	producent char(255) not null,
	model char(255) not null,
	price double not null,
	items integer not null
);