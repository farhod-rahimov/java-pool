create database ex01;

-- \c ex01;
-- first create database ex01 and then connect to it

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    login VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL);
  
CREATE TABLE chat_rooms (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(100) NOT NULL,
    owner BIGSERIAL REFERENCES users(id) NOT NULL);

CREATE TABLE messages (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    author BIGSERIAL REFERENCES users(id),
    room BIGSERIAL REFERENCES chat_rooms(id),
    text text NOT NULL,
    date timestamp NOT NULL);

CREATE TABLE chat_rooms_of_users (
    user_id BIGSERIAL REFERENCES users(id) NOT NULL,
    chat_room_id BIGSERIAL REFERENCES chat_rooms(id) NOT NULL);

-- -- КТО В КАКИХ ГРУППАХ СОСТОИТ
-- SELECT u.id as user_id, u.login, cr.name as chat_room_name, cr.id as chat_room_id, cr.owner as owner_id FROM users u
-- left join chat_rooms_of_users crou on crou.user_id=u.id
-- left join chat_rooms cr on cr.id=crou.chat_room_id

-- СПИСОК ВСЕХ СООБЩЕНИЙ В КАЖДОЙ ГРУППЕ
-- SELECT u.id as user_id, u.login, cr.name, m.text, m.date, cr.owner  FROM users u
-- left join messages m on u.id = m.author
-- left join chat_rooms cr on cr.id=m.room  ORDER BY u.login;