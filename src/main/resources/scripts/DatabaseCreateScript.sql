create table dictionary_of_person_status (
	id int not null,
	name varchar(50),
	primary key(id)
);

create table dictionary_of_service_status (
	id int not null auto_increment,
	name varchar(50),
	primary key(id)
);

create table dictionary_of_city (
	id int not null auto_increment,
	name varchar(50),
	primary key(id)
);

create table dictionary_of_state (
	id int not null auto_increment,
	name varchar(50),
	primary key(id)
);

create table address (
	id int not null auto_increment,
	city_id int not null,
	post_code varchar(6) not null,
	street varchar(50) not null,
	state_id int not null,

	primary key(id),
	foreign key(state_id) references dictionary_of_state(id)
);

create table person (
	id int not null auto_increment,
	login varchar(50) not null,
	password varchar(50) not null,
	first_name varchar(50) not null,
	last_name varchar(50) not null,
	person_status_id int not null,
	address_id int not null,
	phone_number varchar(15),
	email varchar(50),
	nip varchar(15),
	regon varchar(20),
	
	primary key(id),
	foreign key(person_status_id) references dictionary_of_service_status(id),
	foreign key(address_id) references address(id)
);

create table service (
	id int not null auto_increment,
	client_id int not null,
	service_name varchar(255) not null,
	info varchar(255) not null,
	date_of_order date not null,
	date_of_receipt date,

	primary key(id)
);
	
create table login_history (
	id int not null auto_increment,
	person_id int not null,
	login_date datetime not null,

	primary key(id),
	foreign key(person_id) references person(id)
);

create table department (
	id int not null auto_increment,
	adress_id int not null,

	primary key(id),
	foreign key(adress_id) references address(id)
);

create table facture (
	id int not null auto_increment,
	facture_id blob not null,

	primary key(id)
);

create table service_history(
	id int not null auto_increment,
	person_id int not null,
	service_name varchar(255) not null,
	date_of_order date not null,
	date_of_receipt date not null,
	department_id int not null,
	facture_id int not null,

	primary key(id),
	foreign key(department_id) references department(id),
	foreign key(facture_id) references facture(id)
);

create table history_of_service_changes (
	id int not null auto_increment,
	date_of_change date not null,
	service_id int not null,
	wpis varchar(255),

	primary key(id),
	foreign key(service_id) references service_history (id)
);
