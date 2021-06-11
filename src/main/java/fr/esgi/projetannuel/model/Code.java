package fr.esgi.projetannuel.model;

import fr.esgi.projetannuel.enumeration.Status;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Code {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private String id;

    @Column(columnDefinition="text", nullable = false)
    private String input;

    @Column(columnDefinition="text", nullable = true)
    private String output;

/*    @OneToOne
    @JoinColumn(nullable = true)
    private User user;*/

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private Status status = Status.UNCOMPILED;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = true)
    private LocalDateTime updatedAt;

    @Column(nullable = true)
    private LocalDateTime compiledAt;

    public Code() {
        this.createdAt = LocalDateTime.now();
    }

    public Code(String input) {
        this.input = input;
        this.createdAt = LocalDateTime.now();
    }

    public Code(String input, String output, Status status) {
        this.input = input;
        this.output = output;
        this.status = status;

        this.createdAt = LocalDateTime.now();
        this.compiledAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCompiledAt() {
        return compiledAt;
    }

    public void setCompiledAt(LocalDateTime compiledAt) {
        this.compiledAt = compiledAt;
    }
}
