package com.esgi.episcore.game.model;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entité Game — immuable sauf isActive (un jeu peut être désactivé).
 *
 * Règles métier (fail-fast dans le constructeur) :
 *   - title      : 2-100 caractères, non null
 *   - genre      : valeur de l'enum Genre
 *   - maxPlayers : 1 à 100
 */
public final class Game {

    private final UUID          id;
    private final String        title;
    private final Genre         genre;
    private final int           maxPlayers;
    private       boolean       isActive;
    private final LocalDateTime createdAt;

    public Game(UUID id, String title, Genre genre,
                int maxPlayers, boolean isActive, LocalDateTime createdAt) {

        if (id == null)    throw new IllegalArgumentException("id ne peut pas être null");
        if (title == null || title.trim().length() < 2 || title.length() > 100)
            throw new IllegalArgumentException("title invalide : doit faire 2-100 chars — reçu : " + title);
        if (genre == null) throw new IllegalArgumentException("genre ne peut pas être null");
        if (maxPlayers < 1 || maxPlayers > 100)
            throw new IllegalArgumentException("maxPlayers doit être entre 1 et 100, reçu : " + maxPlayers);
        if (createdAt == null) throw new IllegalArgumentException("createdAt ne peut pas être null");

        this.id         = id;
        this.title      = title.trim();
        this.genre      = genre;
        this.maxPlayers = maxPlayers;
        this.isActive   = isActive;
        this.createdAt  = createdAt;
    }

    public UUID          getId()         { return id; }
    public String        getTitle()      { return title; }
    public Genre         getGenre()      { return genre; }
    public int           getMaxPlayers() { return maxPlayers; }
    public boolean       isActive()      { return isActive; }
    public LocalDateTime getCreatedAt()  { return createdAt; }

    /** Désactive ce jeu — appelé par le Service. */
    public void deactivate() { this.isActive = false; }

    @Override
    public String toString() {
        return String.format("Game{id=%s, title='%s', genre=%s, active=%b}", id, title, genre, isActive);
    }
}
