package fr.esgi.projetannuel.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity(name = "userInGame")
public class UserInGame {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "userInGame_id", updatable = false, nullable = false)
    private String id;

    @OneToOne
    private User user;

    public UserInGame() {};

    public UserInGame(String id, User user) {
        this.id = id;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
