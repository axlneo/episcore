package com.esgi.episcore.score.exception;
import java.util.UUID;

public class ScoreNotFoundException extends RuntimeException {
    public ScoreNotFoundException(UUID id) { super("Score introuvable avec l'id : " + id); }
}
