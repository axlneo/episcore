package com.esgi.episcore.game.exception;
import java.util.UUID;

public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException(UUID id)      { super("Game introuvable avec l'id : " + id); }
    public GameNotFoundException(String title) { super("Game introuvable avec le titre : " + title); }
}
