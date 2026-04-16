package com.esgi.episcore.game.model;

/** Genres de jeux disponibles sur EpiScore. */
public enum Genre {
    ACTION, RPG, SPORT, PUZZLE, FPS, STRATEGY;

    /** Parsing depuis String avec message d'erreur explicite. */
    public static Genre from(String value) {
        try {
            return Genre.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Genre inconnu : '" + value + "'. Valeurs valides : ACTION, RPG, SPORT, PUZZLE, FPS, STRATEGY");
        }
    }
}
