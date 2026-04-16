package com.esgi.episcore.player.exception;

public class PlayerAlreadyExistsException extends RuntimeException {
    public PlayerAlreadyExistsException(String username) {
        super("Un player avec le username '" + username + "' existe déjà");
    }
}
