package com.esgi.episcore.player.exception;

public class PlayerAlreadyExistsException extends RuntimeException {
    public PlayerAlreadyExistsException(String username) {
        super("Un player avec le username '" + username + "' existe déjà");
    }

    public PlayerAlreadyExistsException(String field, String value) {
        super("Un player avec le " + field + " '" + value + "' existe déjà");
    }
}
