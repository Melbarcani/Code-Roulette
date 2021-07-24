package fr.esgi.projetannuel.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Message {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private String id;

    @Column(columnDefinition="text", nullable = false)
    private String text;

    @Column(nullable = true)
    private String type;

    @Column(nullable = true)
    private LocalDateTime createdAt;

    @OneToOne
    private User user;

    public Message() {}

    public Message(String text, String type, User user){
        this.text = text;
        this.type = type;
        this.user = user;
        this.createdAt = LocalDateTime.now();
    }

    public Message(String id, String text, String type, User user) {
        this.id = id;
        this.text = text;
        this.type = type;
        this.user = user;
        this.createdAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String formatMessageToChat() {
        return String.format("%s : %s", this.user.getUserName(), this.text);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
