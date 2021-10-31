INSERT INTO users(login, password) VALUES ('btammara', 'btammara_password');
INSERT INTO users(login, password) VALUES ('bob', 'bob_password');
INSERT INTO users(login, password) VALUES ('alex', 'alex_password');
INSERT INTO users(login, password) VALUES ('alice', 'alice_password');
INSERT INTO users(login, password) VALUES ('jack', 'jack_password');

INSERT INTO chat_rooms(name, owner) VALUES ('java-pool', '1'); 
INSERT INTO chat_rooms(name, owner) VALUES ('django-pool', '1');
INSERT INTO chat_rooms(name, owner) VALUES ('ocaml-pool', '2');
INSERT INTO chat_rooms(name, owner) VALUES ('swift-pool', '1');
INSERT INTO chat_rooms(name, owner) VALUES ('c-pool', '3');

INSERT INTO messages(author, room, text, date) VALUES ('1', '1', 'java-pool-text-btammara', '2021-08-31');
INSERT INTO messages(author, room, text, date) VALUES ('1', '2', 'django-pool-text-btammara', '2021-08-30');
INSERT INTO messages(author, room, text, date) VALUES ('2', '1', 'java-pool-text-bob', '2021-08-29');
INSERT INTO messages(author, room, text, date) VALUES ('2', '2', 'django-pool-text-bob', '2021-08-28');
INSERT INTO messages(author, room, text, date) VALUES ('4', '4', 'swift-pool-text-alice', '2021-08-27');
INSERT INTO messages(author, room, text, date) VALUES ('1', '3', 'ocaml-pool-text-btammara', '2021-08-26');

INSERT INTO chat_rooms_of_users(user_id, chat_room_id) VALUES ('1', '1');
INSERT INTO chat_rooms_of_users(user_id, chat_room_id) VALUES ('1', '2');
INSERT INTO chat_rooms_of_users(user_id, chat_room_id) VALUES ('2', '1');
INSERT INTO chat_rooms_of_users(user_id, chat_room_id) VALUES ('2', '2');
INSERT INTO chat_rooms_of_users(user_id, chat_room_id) VALUES ('3', '1');
INSERT INTO chat_rooms_of_users(user_id, chat_room_id) VALUES ('3', '2');
INSERT INTO chat_rooms_of_users(user_id, chat_room_id) VALUES ('4', '4');
