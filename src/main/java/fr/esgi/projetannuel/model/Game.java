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

    @Column(columnDefinition="text", nullable = true)
    private String code = "";

    @Column
    boolean isGameOver = false;

    @Column
    int numberOfTurn = 3;

    @Column
    int timer = 25;

    @OneToOne
    private Exercise exercise;

    @OneToOne
    private Chat chat;

    @OneToMany
    private List<UserInGame> usersInGame = new ArrayList<>();

    @OneToMany
    private List<Compilation> compilations = new ArrayList<>();

    public Game() {}

    public Game(String id, Exercise exercise) {
        this.id = id;
        this.exercise = exercise;
    }

    public Game(String id, List<Compilation> compilations) {
        this.id = id;
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

    public void setGameOver(boolean isGameOver) {
        this.isGameOver = isGameOver;
    }

    public List<Compilation> getCompilations() {
        return compilations;
    }

    public void setCompilations(List<Compilation> compilations) {
        this.compilations = compilations;
    }

    public int getNumberOfTurn() {
        return numberOfTurn;
    }

    public void setNumberOfTurn(int numberOfTurn) {
        this.numberOfTurn = numberOfTurn;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
