package com.secure.notes_api.note;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import com.secure.notes_api.user.User;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;
import java.time.Instant;

@Entity
@Table(name = "notes")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    // JPA requires a no-args constructor
    protected Note() {
    }

    public Note(String title, String content, User owner) {
        this.title = title;
        this.content = content;
        this.owner = owner;
        this.createdAt = Instant.now();
    }



    // getters
    public User getOwner() { return owner; }
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public Instant getCreatedAt() { return createdAt; }

    // setters (only for the fields that should change)
    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
}