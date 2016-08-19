insert into dictionary_of_person_status(id,name) values(1,'default');
insert into dictionary_of_person_status(id,name) values(2,'owner');
insert into dictionary_of_person_status(id,name) values(3,'serviceman');
insert into dictionary_of_person_status(id,name) values(4,'seller');
insert into dictionary_of_person_status(id,name) values(5,'client');

insert into dictionary_of_service_status(name) values('default');
insert into dictionary_of_city(name) values('default');
insert into dictionary_of_state(name) values('default');
insert into address(city_id,post_code,street,state_id) values(1,'default','default',1);
INSERT INTO `sm`.`person` (`login`,`password`,`first_name`,`last_name`,`status_id`,`address_id`,`phone_number`,`email`,`nip`,`regon`)
					VALUES('default','default','default','default',1,1,'default','default','default','default');
