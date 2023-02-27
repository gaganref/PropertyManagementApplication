drop table if exists tenant cascade;

drop table if exists tenancy_info cascade;

drop table if exists house cascade;

drop table if exists landlord cascade;


create schema if not exists public;

create table landlord
(
    landlord_id bigserial,
    email_id    varchar(255) not null,
    name        varchar(255) not null,
    phone_no    varchar(255) not null,
    primary key (landlord_id)
);

create table house
(
    house_id    bigserial,
    city        varchar(255)  not null,
    flat_no     integer,
    house_no    integer       not null,
    postcode    varchar(255)  not null,
    street      varchar(250)  not null,
    cost        numeric(6, 2) not null,
    no_of_rooms integer       not null,
    landlord    bigint        not null,
    primary key (house_id),
    constraint fk8rxrr8iupkmamwuhnvib7nv0j
        foreign key (landlord) references landlord
);

alter table house
    add constraint house_flat_no_check
        check ((flat_no >= 1) AND (flat_no <= 1200));

alter table house
    add constraint house_house_no_check
        check ((house_no >= 1) AND (house_no <= 3000));

alter table house
    add constraint house_no_of_rooms_check
        check ((no_of_rooms >= 1) AND (no_of_rooms <= 10));

create table tenancy_info
(
    tenancy_info_id bigserial,
    end_date        date not null,
    start_date      date not null,
    house           bigint,
    primary key (tenancy_info_id),
    constraint fko5xo9xt7fk4ge2x36266q1yn1
        foreign key (house) references house
);

create table tenant
(
    tenant_id    bigserial,
    email_id     varchar(255) not null,
    name         varchar(255) not null,
    phone_no     varchar(255) not null,
    city         varchar(255) not null,
    flat_no      integer,
    house_no     integer      not null,
    postcode     varchar(255) not null,
    street       varchar(250) not null,
    tenancy_info bigint,
    primary key (tenant_id),
    constraint fk96ccd3s24tpdwya7tf9ue4vrn
        foreign key (tenancy_info) references tenancy_info
);

alter table tenant
    add constraint tenant_flat_no_check
        check ((flat_no >= 1) AND (flat_no <= 1200));

alter table tenant
    add constraint tenant_house_no_check
        check ((house_no >= 1) AND (house_no <= 3000));


insert into public.landlord (landlord_id, email_id, name, phone_no) values (3, 'sukumar@gmail.com', 'Sukumar', '7789065432');
insert into public.landlord (landlord_id, email_id, name, phone_no) values (5, 'James@gmail.com', 'Maxwell James', '7776554321');
insert into public.landlord (landlord_id, email_id, name, phone_no) values (6, 'Patel@gmail.com', 'Jathin Patel', '7776554327');
insert into public.landlord (landlord_id, email_id, name, phone_no) values (7, 'Willy@gmail.com', 'Dawid Willy', '7789654320');
insert into public.landlord (landlord_id, email_id, name, phone_no) values (8, 'Wick@gmail.com', 'John Wick', '7998654321');
insert into public.landlord (landlord_id, email_id, name, phone_no) values (9, 'Chand@gmail.com', 'Chandler James', '7866543219');
insert into public.landlord (landlord_id, email_id, name, phone_no) values (10, 'GateG@gmail.com', 'Glean Gates', '7876543212');
insert into public.landlord (landlord_id, email_id, name, phone_no) values (11, 'Ancy@gmail.com', 'Ancy Faulker', '7889997654');
insert into public.landlord (landlord_id, email_id, name, phone_no) values (2, 'NAncy@gmail.com', 'NAncy William', '7665432189');
insert into public.landlord (landlord_id, email_id, name, phone_no) values (4, 'Samworth@gmail.com', 'Steve Samworth', '7987654321');
insert into public.landlord (landlord_id, email_id, name, phone_no) values (12, 'Johnson@gmail.com', 'Scarlet Johnson', '788999666');
insert into public.landlord (landlord_id, email_id, name, phone_no) values (13, 'Mark@gmail.com', 'Chris Mark', '778965555');


insert into public.house (house_id, city, flat_no, house_no, postcode, street, cost, no_of_rooms, landlord) values (3, 'Glassgow', null, 80, 'GE4 3GE', 'Glacer', 455.00, 5, 3);
insert into public.house (house_id, city, flat_no, house_no, postcode, street, cost, no_of_rooms, landlord) values (4, 'Leicester', null, 34, 'LE1 1GE', 'samworth Way', 765.00, 4, 4);
insert into public.house (house_id, city, flat_no, house_no, postcode, street, cost, no_of_rooms, landlord) values (2, 'Hinckley', null, 34, 'LE6 5TT', 'Jacob', 500.00, 5, 2);
insert into public.house (house_id, city, flat_no, house_no, postcode, street, cost, no_of_rooms, landlord) values (6, 'Leicester', 33, 123, 'LE9 5TT', 'Charles', 500.00, 3, 5);
insert into public.house (house_id, city, flat_no, house_no, postcode, street, cost, no_of_rooms, landlord) values (7, 'Glassgow', 7, 56, 'GE4 7GE', 'Ggrassmore', 250.00, 2, 6);
insert into public.house (house_id, city, flat_no, house_no, postcode, street, cost, no_of_rooms, landlord) values (8, 'Kingston', 22, 243, 'KE3 6RT', 'Kirbell', 1111.00, 1, 11);
insert into public.house (house_id, city, flat_no, house_no, postcode, street, cost, no_of_rooms, landlord) values (5, 'Leicester', null, 32, 'LE3 1DE', 'samworth Way', 545.00, 5, 4);
insert into public.house (house_id, city, flat_no, house_no, postcode, street, cost, no_of_rooms, landlord) values (9, 'Leicester', null, 55, 'LE6 5TI', 'Lining ', 445.00, 4, 10);
insert into public.house (house_id, city, flat_no, house_no, postcode, street, cost, no_of_rooms, landlord) values (10, 'Leicester', 3, 23, 'LE11 1GE', 'THreeway', 333.00, 3, 9);
insert into public.house (house_id, city, flat_no, house_no, postcode, street, cost, no_of_rooms, landlord) values (11, 'Glassgow', null, 534, 'GG4 6DD', 'Shaded', 566.00, 5, 7);
insert into public.house (house_id, city, flat_no, house_no, postcode, street, cost, no_of_rooms, landlord) values (12, 'Leicester', null, 99, 'LE22 2GE', 'Thirdmell', 565.00, 4, 8);
insert into public.house (house_id, city, flat_no, house_no, postcode, street, cost, no_of_rooms, landlord) values (13, 'Glassgow', null, 765, 'GG2 3IY', 'flexing', 555.00, 4, 8);
insert into public.house (house_id, city, flat_no, house_no, postcode, street, cost, no_of_rooms, landlord) values (14, 'Leicester', 5, 22, 'LE3 8DE', 'Milton', 235.00, 1, 10);
insert into public.house (house_id, city, flat_no, house_no, postcode, street, cost, no_of_rooms, landlord) values (15, 'Glassgow', 4, 8, 'GG2 5IK', 'Georges', 325.00, 1, 5);
insert into public.house (house_id, city, flat_no, house_no, postcode, street, cost, no_of_rooms, landlord) values (16, 'Glassgow', null, 85, 'GG43 6KJ', 'Nirghton', 250.00, 10, 4);
insert into public.house (house_id, city, flat_no, house_no, postcode, street, cost, no_of_rooms, landlord) values (17, 'Glassgow', 8, 124, 'GE4 3QW', 'Stark', 555.00, 5, 12);
insert into public.house (house_id, city, flat_no, house_no, postcode, street, cost, no_of_rooms, landlord) values (18, 'Leicester', null, 83, 'LE3 1DP', 'Saintalbens', 575.00, 5, 13);


insert into public.tenancy_info (tenancy_info_id, end_date, start_date, house) values (4, '2022-12-31', '2021-12-01', 3);
insert into public.tenancy_info (tenancy_info_id, end_date, start_date, house) values (5, '2023-09-01', '2021-09-01', 7);
insert into public.tenancy_info (tenancy_info_id, end_date, start_date, house) values (6, '2023-12-01', '2022-12-01', 4);
insert into public.tenancy_info (tenancy_info_id, end_date, start_date, house) values (7, '2023-12-01', '2022-12-01', 6);
insert into public.tenancy_info (tenancy_info_id, end_date, start_date, house) values (9, '2023-12-01', '2022-12-01', 18);
insert into public.tenancy_info (tenancy_info_id, end_date, start_date, house) values (10, '2023-09-01', '2022-09-01', 17);
insert into public.tenancy_info (tenancy_info_id, end_date, start_date, house) values (11, '2024-01-01', '2022-01-01', 16);
insert into public.tenancy_info (tenancy_info_id, end_date, start_date, house) values (12, '2024-01-01', '2022-12-01', 4);
insert into public.tenancy_info (tenancy_info_id, end_date, start_date, house) values (13, '2025-12-01', '2022-12-01', 2);
insert into public.tenancy_info (tenancy_info_id, end_date, start_date, house) values (14, '2025-12-01', '2022-12-01', 8);
insert into public.tenancy_info (tenancy_info_id, end_date, start_date, house) values (15, '2024-08-02', '2021-03-02', 8);
insert into public.tenancy_info (tenancy_info_id, end_date, start_date, house) values (16, '2023-08-01', '2022-08-01', 12);
insert into public.tenancy_info (tenancy_info_id, end_date, start_date, house) values (17, '2023-09-01', '2022-09-01', 13);


insert into public.tenant (tenant_id, email_id, name, phone_no, city, flat_no, house_no, postcode, street, tenancy_info) values (2, 'Reddy@gmail.com', 'JaydevReddy', '7890654321', 'Leicester', 9, 97, 'LE2 1FF', 'Jarrom', 4);
insert into public.tenant (tenant_id, email_id, name, phone_no, city, flat_no, house_no, postcode, street, tenancy_info) values (3, 'gkgk@gamail.com', 'Girish', '778906666', 'Leicester', 8, 55, 'LE2 1FF', 'Jarrom', 4);
insert into public.tenant (tenant_id, email_id, name, phone_no, city, flat_no, house_no, postcode, street, tenancy_info) values (4, 'Shal@gmail.com', 'Shalini', '7665444444', 'Leicester', 7, 55, 'LE2 1FF', 'Jarrom', 4);
insert into public.tenant (tenant_id, email_id, name, phone_no, city, flat_no, house_no, postcode, street, tenancy_info) values (5, 'Jiya@gmail.com', 'Jiya', '7890900000', 'Leicester', 8, 55, 'LE2 1FF', 'Jarrom', 4);
insert into public.tenant (tenant_id, email_id, name, phone_no, city, flat_no, house_no, postcode, street, tenancy_info) values (6, 'ST@gmail.com', 'Steve', '7777788888', 'London', 7, 8, 'LO9 9KJ', 'LInking', 5);
insert into public.tenant (tenant_id, email_id, name, phone_no, city, flat_no, house_no, postcode, street, tenancy_info) values (7, 'D@gmail.com', 'Dolittle Jarry', '7777755555', 'London', 7, 4, 'LO9 8GG', 'HARRY', 5);
insert into public.tenant (tenant_id, email_id, name, phone_no, city, flat_no, house_no, postcode, street, tenancy_info) values (8, 'J@gmail.com', 'James', '7888909987', 'Sheffield', 7, 98, 'SH5 7HH', 'Stanlee', 4);
insert into public.tenant (tenant_id, email_id, name, phone_no, city, flat_no, house_no, postcode, street, tenancy_info) values (9, 'Leol@gmail.com', 'Jeol', '778889999', 'Leicester', 6, 123, 'LE2 1HH', 'Laron Park', 6);
insert into public.tenant (tenant_id, email_id, name, phone_no, city, flat_no, house_no, postcode, street, tenancy_info) values (10, 'Sharon@gmail.com', 'Sharon', '7778999876', 'Leicester', null, 22, 'LO9 9KJ', 'Stanlee', 7);
insert into public.tenant (tenant_id, email_id, name, phone_no, city, flat_no, house_no, postcode, street, tenancy_info) values (11, 'Taron@gmail.com', 'Taron', '7765444556', 'Leicester', null, 55, 'LE2 1GE', 'HARRY', 9);
insert into public.tenant (tenant_id, email_id, name, phone_no, city, flat_no, house_no, postcode, street, tenancy_info) values (12, 'Travor@gmail.com', 'Travor', '7788890002', 'London', null, 45, 'LO9 8GG', 'erleder', 10);
insert into public.tenant (tenant_id, email_id, name, phone_no, city, flat_no, house_no, postcode, street, tenancy_info) values (13, 'Kevin@gmail.com', 'Kevin', '7766544332', 'Glassgow', 6, 55, 'GE6 7TT', 'Ben james', 11);
insert into public.tenant (tenant_id, email_id, name, phone_no, city, flat_no, house_no, postcode, street, tenancy_info) values (14, 'Harry@gmail.com', 'Harry', '7272737455', 'Leicester', 2, 35, 'LO9 8GG', 'Jacob', 12);
insert into public.tenant (tenant_id, email_id, name, phone_no, city, flat_no, house_no, postcode, street, tenancy_info) values (15, 'Jeenny@gmail.com', 'LENARDOJENNY', '7766554433', 'London', null, 135, 'LO9 8GG', 'Charles', 13);
insert into public.tenant (tenant_id, email_id, name, phone_no, city, flat_no, house_no, postcode, street, tenancy_info) values (16, 'GARON@gmail.com', 'GARON', '7788990000', 'Leicester', null, 45, 'LE2 1FF', 'Jacob', 6);
insert into public.tenant (tenant_id, email_id, name, phone_no, city, flat_no, house_no, postcode, street, tenancy_info) values (17, 'SAM@gmail.com', 'SAMUEL', '7766554444', 'Leicester', null, 76, 'LE2 1FF', 'Jarrom', 7);
insert into public.tenant (tenant_id, email_id, name, phone_no, city, flat_no, house_no, postcode, street, tenancy_info) values (18, 'JORDAN@gmail.com', 'JORDAN', '7766558899', 'London', null, 76, 'LO9 9KJ', 'Jacob', 9);
insert into public.tenant (tenant_id, email_id, name, phone_no, city, flat_no, house_no, postcode, street, tenancy_info) values (19, 'Leol@gmail.com', 'LENARDO', '788899999', 'London', null, 97, 'LE2 1GE', 'Jarrom', 10);
insert into public.tenant (tenant_id, email_id, name, phone_no, city, flat_no, house_no, postcode, street, tenancy_info) values (20, 'WARNER@gmail.com', 'WARNER', '7766112244', 'Leicester', null, 55, 'LO9 9KJ', 'Laron Park', 14);
insert into public.tenant (tenant_id, email_id, name, phone_no, city, flat_no, house_no, postcode, street, tenancy_info) values (21, 'DANNY@gmail.com', 'DANNY', '7766111122', 'Leicester', null, 76, 'LO9 9KJ', 'Jarrom', 4);
insert into public.tenant (tenant_id, email_id, name, phone_no, city, flat_no, house_no, postcode, street, tenancy_info) values (22, 'FAREE@gmail.com', 'FARRE', '777777777', 'London', null, 75, 'LO9 8GG', 'Jarrom', 6);
insert into public.tenant (tenant_id, email_id, name, phone_no, city, flat_no, house_no, postcode, street, tenancy_info) values (23, 'GAGAN@gmail.com', 'GAGAN', '7778889990', 'Glassgow', null, 75, 'GE6 7TT', 'Laron Park', 10);
insert into public.tenant (tenant_id, email_id, name, phone_no, city, flat_no, house_no, postcode, street, tenancy_info) values (24, 'SAM@gmail.com', 'Steve Samworth', '7777788888', 'Leicester', null, 55, 'LO9 9KJ', 'Stanlee', 16);
