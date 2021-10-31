package edu.school21.chat.models;

import java.util.List;
import java.util.Objects;

public class User {
    private static  long                    lastId = 1;
    private         Long                    id;
    private         String                  login;
    private         String                  password;
    private         List<Chatroom>          createdRooms;
    private         List<Chatroom>          joinedRooms;

    public User(String login, String password) {
        this.id = User.lastId++;
        this.login = login;
        this.password = password;
    };

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public List<Chatroom> getCreatedRooms() {
        return createdRooms;
    }

    public List<Chatroom> getJoinedRooms() {
        return joinedRooms;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCreatedRooms(List<Chatroom> createdRooms) {
        this.createdRooms = createdRooms;
    }

    public void setJoinedRooms(List<Chatroom> joinedRooms) {
        this.joinedRooms = joinedRooms;
    }

    public void addNewJoinedRoom(Chatroom newJoinedRoom) {
        this.joinedRooms.add(newJoinedRoom);
    }

    public void addNewCreatedRoom(Chatroom newCreatedRoom) {
        this.createdRooms.add(newCreatedRoom);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return id.equals(user.id) && login.equals(user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", createdRooms=" + createdRooms +
                ", joinedRooms=" + joinedRooms +
                '}';
    }
}