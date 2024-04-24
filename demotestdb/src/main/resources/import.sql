-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;

create table Person (id integer not null, email varchar(255), firstname varchar(255), grade varchar(255), person_uuid varchar(255), surname varchar(255), primary key (id));
create table Reservation (id integer not null, endTime timestamp(6), reservationDate date, startTime timestamp(6), personId integer, roomId integer, primary key (id));
create table Room (id integer not null, description varchar(255), name varchar(255), primary key (id));
create sequence person_seq start with 1 increment by 1;
create sequence reservation_seq start with 1 increment by 1;
create sequence room_seq start with 1 increment by 1;
alter table if exists Reservation add constraint FK6dxcc1u2ugq8lfsqe24gien04 foreign key (personId) references Person;
alter table if exists Reservation add constraint FKb28gu6q7ewrw11wxj026rcbco foreign key (roomId) references Room;


insert into Person (id, email, firstname, grade, surname, person_uuid)
values (nextval('person_seq'),'s.stoeger@students.htl-leonding.ac.at', 'Sophie', '4AHITM', 'Stöger', '5c3c1ee8-c1a0-4b4d-9ac8-b14bed6ce6bc');


insert into Room (id, description, name)
values (nextval('room_seq'),'Das Fotostudio steht für Schüler der Medientechnik zur verfügung um an Foto- und Videoprojekten zu arbeiten','Fotostudio');
