package edu.school21.chat.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Message {
    private static  long            lastId = 1;
    private         Long            id;
    private         User            author;
    private         Chatroom        room;
    private         String          text;
    private         LocalDateTime   date;

    public Message(User author, Chatroom room, String text) {
        this.id = Message.lastId++;
        this.author = author;
        this.room = room;
        this.text = text;
        this.date = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public Chatroom getRoom() {
        return room;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setRoom(Chatroom room) {
        this.room = room;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message = (Message) o;
        return id.equals(message.id) && author.equals(message.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", author=" + author +
                ", room=" + room +
                ", text='" + text + '\'' +
                ", date=" + date +
                '}';
    }
}