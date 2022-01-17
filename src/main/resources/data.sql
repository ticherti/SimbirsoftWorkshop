INSERT INTO users (login, password, role)
VALUES ('login', '$2a$12$UuhbNaOleqvVGc66J22l5eXVzazATWQGQfnnS2Dhsifx5b7Ox.7xG', 'ADMINISTRATOR'),
       ('login2', 'password2', 'MODERATOR'),
       ('login3', '$2a$12$YrLwsgf2uUN60ufyxwjsluQ9OoU69yqkeN9tXCRpXAOPNzZtUrPlS', 'USER');

INSERT INTO rooms (is_private, name, creator_id)
VALUES ('false', 'Room1', 1),
       ('false', 'Room2', 2);

INSERT INTO messages (content, room_id, user_id)
VALUES ('Hey, you', 1, 1),
       ('I am here in the cold, getting lonely, getting cold', 1, 2),
       ('Can you feel me?', 1, 1),
       ('How is first there?', 2, 2),
       ('I am a big strong she-wolf, how awesome my huge paws!', 2, 1),
       ('Admin password for "login" is "password"', 2, 1);

INSERT INTO rooms_users (room_id, user_id)
VALUES (2, 1),
       (2, 2),
       (1, 3),
       (1, 1);