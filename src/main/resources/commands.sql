drop table if exists users;
drop table if exists user_roles;
drop table if exists requests;
drop table if exists request_statuses;
drop table if exists contragents;
drop table if exists equipments;

create table user_roles
(
    role_id    SERIAL
        constraint user_roles_pk
            primary key,
    value VARCHAR not null
);

create table users
(
    user_id   bigserial
        constraint users_pk
            primary key,
    user_name varchar,
    user_role bigint
        constraint users_user_rules_role_id_fk
            references user_roles
);

start transaction;
INSERT INTO public.user_roles (value)
VALUES ('ADMIN'),
       ('COORDINATOR'),
       ('ENGINEER'),
       ('GUEST');


commit;

create table contragents
(
    contragent_id  BIGSERIAL
        constraint contragents_pk
            primary key,
    contragent_name VARCHAR not null
);

start transaction;
INSERT INTO public.contragents (contragent_name)
VALUES ('Страйк'),
       ('ПБФ'),
       ('Engy'),
       ('Hendz');


commit;


create table equipments
(
    equipment_id  BIGSERIAL not null
        constraint equipments_pk
            primary key,
    model         VARCHAR   not null,
    serial_number VARCHAR
);

create table request_statuses
(

    status_id      bigserial
        constraint request_statuses_pk
            primary key,
    request_status jsonb not null
);

start transaction;
INSERT INTO public.request_statuses (request_status)
VALUES ('{"en": "assigned", "ru": "назначена"}'),
       ('{"en": "on departure", "ru": "в выезде"}'),
       ('{"en": "closed", "ru": "закрыта"}'),
       ('{"en": "false departure", "ru": "Ложный выезд"}'),
       ('{"en": "cancelled", "ru": "отменено"}');


commit;




create table requests
(
    request_id            bigserial
        constraint requests_pk
            primary key,
    req_number            varchar,
    customer              varchar,
    customer_phone        varchar,
    customer_address      text,

    equipment_transferred bigint
        constraint requests_equipments_equipment_id_fk_2
            references equipments,
    equipment_taken       bigint
        constraint requests_equipments_equipment_id_fk
            references equipments,
    sla                   date,
    completion_date       date,
    comment               text,
    contragent            bigint
        constraint requests_contragents_contragent_id_fk
            references contragents,
    status                bigint not null
        constraint requests_request_statuses_status_id_fk
            references request_statuses

);

