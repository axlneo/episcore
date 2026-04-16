package com.esgi.episcore.player.exception;

import java.util.UUID;

public class PlayerNotFoundException extends RuntimeException {
    public PlayerNotFoundException(UUID id) {
        super("Player introuvable avec l'id : " + id);
    }
    public PlayerNotFoundException(String username) {
        super("Player introuvable avec le username : " + username);
    }
}
