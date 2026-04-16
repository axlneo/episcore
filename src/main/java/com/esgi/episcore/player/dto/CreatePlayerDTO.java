package com.esgi.episcore.player.dto;

import java.util.regex.Pattern;

/**
 * DTO de création — reçu pour créer un nouveau joueur.
 * Fail-fast : validation dans le constructeur.
 */
public final class CreatePlayerDTO {

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{3,20}$");
    private static final Pattern EMAIL_PATTERN    = Pattern.compile("^[^@]+@[^@]+\\.[^@]+$");

    private final String username;
    private final String email;

    public CreatePlayerDTO(String username, String email) {
        if (username == null || !USERNAME_PATTERN.matcher(username).matches())
            throw new IllegalArgumentException("username invalide : " + username);
        if (email == null || !EMAIL_PATTERN.matcher(email).matches())
            throw new IllegalArgumentException("email invalide : " + email);

        this.username = username;
        this.email    = email;
    }

    public String getUsername() { return username; }
    public String getEmail()    { return email; }
}
