create table "Landlord"
(
    "Landlord_ID" bigint not null
        primary key,
    "Name"        text,
    "Email_ID"    text,
    "Phone_NO"    bigint
);

alter table "Landlord"
    owner to postgres;

create table "House"
(
);

alter table "House"
    owner to postgres;

create table "Tenant"
(
);

alter table "Tenant"
    owner to postgres;

create table "TenancyInfo"
(
);

alter table "TenancyInfo"
    owner to postgres;

create table "Address"
(
);

alter table "Address"
    owner to postgres;