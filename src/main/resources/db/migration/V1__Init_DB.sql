-- create sequence hibernate_sequence start 1 increment 1;
create sequence hibernate_sequence;
alter sequence hibernate_sequence owner to "user";

create sequence messages_id_seq;
alter sequence messages_id_seq owner to "user";

create sequence rooms_id_seq;
alter sequence rooms_id_seq owner to "user";

create sequence users_id_seq;
alter sequence users_id_seq owner to "user";

create table messages (
                          id  bigserial not null,
                          content varchar(2048) not null,
                          date_time timestamp default now(),
                          room_id int8 not null,
                          user_id int8 not null,
                          primary key (id)
);

create table rooms (
                       id  bigserial not null,
                       is_private boolean not null,
                       name varchar(20) not null,
                       creator_id int8,
                       primary key (id)
);

create table rooms_users (
                             room_id int8 not null,
                             user_id int8 not null,
                             primary key (room_id, user_id)
);
create table users (
                       id  bigserial not null,
                       end_ban_time TIMESTAMP DEFAULT NOW(),
                       is_active boolean default true not null,
                       login varchar(30) not null,
                       password varchar(90) not null,
                       role varchar(255) default USER not null,
                       primary key (id)
);

alter table rooms
    add constraint room_idx unique (name);

alter table users
    add constraint user_idx unique (login);

alter table messages
    add constraint message_room_fk
        foreign key (room_id)
            references rooms;

alter table messages
    add constraint message_user_fk
        foreign key (user_id)
            references users;

alter table rooms
    add constraint room_user_fk
        foreign key (creator_id)
            references users;

alter table rooms_users
    add constraint rooms_users_user_fk
        foreign key (user_id)
            references users;

alter table rooms_users
    add constraint rooms_users_room_fk
        foreign key (room_id)
            references rooms;