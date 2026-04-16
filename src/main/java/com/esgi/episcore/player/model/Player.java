package com.esgi.episcore.player.model;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Entité Player — immuable sauf level/xp qui évoluent en jeu.
 *
 * Règles métier (fail-fast dans le constructeur) :
 *   - username : 3-20 caractères alphanumériques/underscores
 *   - email    : format valide
 *   - level    : >= 1
 *   - xp       : >= 0
 */
public final class Player {

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{3,20}$");
    private static final Pattern EMAIL_PATTERN    = Pattern.compile("^[^@]+@[^@]+\\.[^@]+$");

    private final UUID          id;
    private final String        username;
    private final String        email;
    private       int           level;
    private       int           xp;
    private final LocalDateTime createdAt;

    public Player(UUID id, String username, String email,
                  int level, int xp, LocalDateTime createdAt) {

        if (id == null)       throw new IllegalArgumentException("id ne peut pas être null");
        if (username == null || !USERNAME_PATTERN.matcher(username).matches())
            throw new IllegalArgumentException("username invalide : doit faire 3-20 chars alphanumériques — reçu : " + username);
        if (email == null || !EMAIL_PATTERN.matcher(email).matches())
            throw new IllegalArgumentException("email invalide : " + email);
        if (level < 1)  throw new IllegalArgumentException("level doit être >= 1, reçu : " + level);
        if (xp < 0)     throw new IllegalArgumentException("xp doit être >= 0, reçu : " + xp);
        if (createdAt == null) throw new IllegalArgumentException("createdAt ne peut pas être null");

        this.id        = id;
        this.username  = username;
        this.email     = email;
        this.level     = level;
        this.xp        = xp;
        this.createdAt = createdAt;
    }

    // ── getters ──────────────────────────────────────────────
    public UUID          getId()        { return id; }
    public String        getUsername()  { return username; }
    public String        getEmail()     { return email; }
    public int           getLevel()     { return level; }
    public int           getXp()        { return xp; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    /**
     * Met à jour le niveau en fonction de l'XP actuel.
     * Appelé par le Service après modification de l'XP.
     * Règle : level = (xp / 100) + 1
     */
    public void refreshLevel() {
        this.level = (this.xp / 100) + 1;
    }

    @Override
    public String toString() {
        return String.format("Player{id=%s, username='%s', level=%d, xp=%d}", id, username, level, xp);
    }
}
