package com.esgi.episcore.player.dto;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO de lecture — exposé à l'IHM.
 * ⚠️  Pas d'email — donnée sensible non exposée.
 */
public final class PlayerDTO {

    private final UUID          id;
    private final String        username;
    private final int           level;
    private final int           xp;
    private final LocalDateTime createdAt;

    public PlayerDTO(UUID id, String username, int level, int xp, LocalDateTime createdAt) {
        this.id        = id;
        this.username  = username;
        this.level     = level;
        this.xp        = xp;
        this.createdAt = createdAt;
    }

    public UUID          getId()        { return id; }
    public String        getUsername()  { return username; }
    public int           getLevel()     { return level; }
    public int           getXp()        { return xp; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    @Override
    public String toString() {
        return String.format("PlayerDTO{id=%s, username='%s', level=%d}", id, username, level);
    }
}
