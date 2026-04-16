package com.esgi.episcore.game.exception;

public class GameAlreadyExistsException extends RuntimeException {
    public GameAlreadyExistsException(String title) {
        super("Un game avec le titre '" + title + "' existe déjà");
    }
}
