package com.esgi.episcore.game.dto;

import com.esgi.episcore.game.model.Genre;
import java.time.LocalDateTime;
import java.util.UUID;

/** DTO de lecture — exposé à l'IHM. */
public final class GameDTO {

    private final UUID          id;
    private final String        title;
    private final Genre         genre;
    private final int           maxPlayers;
    private final boolean       isActive;
    private final LocalDateTime createdAt;

    public GameDTO(UUID id, String title, Genre genre,
                   int maxPlayers, boolean isActive, LocalDateTime createdAt) {
        this.id         = id;
        this.title      = title;
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

    @Override
    public String toString() {
        return String.format("GameDTO{id=%s, title='%s', genre=%s}", id, title, genre);
    }
}
