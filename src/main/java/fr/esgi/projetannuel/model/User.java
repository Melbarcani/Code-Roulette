package fr.esgi.projetannuel.model;

import fr.esgi.projetannuel.enumeration.Role;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "profile")
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "profile_id", updatable = false, nullable = false)
    private String id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false)
    private Integer elo = 1000;

    @Column(nullable = false)
    private Integer gamesPlayed = 0;

    @Column(nullable = false)
    private Integer gamesWon = 0;

    @Column(nullable = false)
    private Integer compilationCompleted = 0;

    @Column(nullable = false)
    private Integer eloProblems = 1000;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @Column(nullable = false)
    private Boolean isInQueue = false;

    private String lobbyId;

    public User() {}

    public User(String id, String firstName, String lastName, String email, String userName, String password, LocalDate birthDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.birthDate = birthDate;
        this.isInQueue = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public void setElo(Integer elo) {
        this.elo = elo;
    }

    public Integer getEloProblems() {
        return eloProblems;
    }

    public void setEloProblems(Integer eloProblems) {
        this.eloProblems = eloProblems;
    }

    public Boolean getInQueue() {
        return isInQueue;
    }

    public void setInQueue(Boolean inQueue) {
        isInQueue = inQueue;
    }

    public String getLobbyId() {
        return lobbyId;
    }

    public void setLobbyId(String lobbyId) {
        this.lobbyId = lobbyId;
    }

    public Integer getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(Integer gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public Integer getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(Integer gamesWon) {
        this.gamesWon = gamesWon;
    }

    public Integer getCompilationCompleted() {
        return compilationCompleted;
    }

    public void setCompilationCompleted(Integer compilationCompleted) {
        this.compilationCompleted = compilationCompleted;
    }
}
