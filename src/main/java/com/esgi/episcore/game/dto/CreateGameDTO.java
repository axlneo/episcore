package com.esgi.episcore.game.dto;

import com.esgi.episcore.game.model.Genre;

/** DTO de création — fail-fast dans le constructeur. */
public final class CreateGameDTO {

    private final String title;
    private final Genre  genre;
    private final int    maxPlayers;

    public CreateGameDTO(String title, String genre, int maxPlayers) {
        if (title == null || title.trim().length() < 2 || title.length() > 100)
            throw new IllegalArgumentException("title invalide : " + title);
        if (maxPlayers < 1 || maxPlayers > 100)
            throw new IllegalArgumentException("maxPlayers doit être entre 1 et 100, reçu : " + maxPlayers);

        this.title      = title.trim();
        this.genre      = Genre.from(genre);    // lance une exception si genre invalide
        this.maxPlayers = maxPlayers;
    }

    public String getTitle()      { return title; }
    public Genre  getGenre()      { return genre; }
    public int    getMaxPlayers() { return maxPlayers; }
}
