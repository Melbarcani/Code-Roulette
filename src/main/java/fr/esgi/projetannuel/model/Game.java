package fr.esgi.projetannuel.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Game {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private String id;

    @OneToOne
    private Exercise exercise;

    @OneToOne
    private Chat chat;

    @OneToMany
    private List<UserInGame> usersInGame = new ArrayList<>();

    @OneToMany
    private List<Compilation> compilations = new ArrayList<>();

    @Column
    boolean isGameOver = false;

    public Game() {}

    public Game(String id, Exercise exercise) {
        this.id = id;
        this.exercise = exercise;
    }

    public Game(String id, List<Compilation> compilations) {
        this.id = id;
        this.exercise = exercise;
        this.compilations = compilations;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public List<UserInGame> getUsersInGame() {
        return usersInGame;
    }

    public void setUsersInGame(List<UserInGame> usersInGame) {
        this.usersInGame = usersInGame;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public List<Compilation> getCompilations() {
        return compilations;
    }

    public void setCompilations(List<Compilation> compilations) {
        this.compilations = compilations;
    }
}
