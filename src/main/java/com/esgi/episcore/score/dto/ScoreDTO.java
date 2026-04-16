package com.esgi.episcore.score.dto;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO de lecture enrichi — contient les noms lisibles du joueur et du jeu.
 * Produit via JOIN SQL dans ScoreDAOImpl.
 */
public final class ScoreDTO {

    private final UUID          id;
    private final String        playerUsername;   // JOIN players
    private final String        gameTitle;        // JOIN games
    private final int           points;
    private final int           durationSeconds;
    private final LocalDateTime playedAt;

    public ScoreDTO(UUID id, String playerUsername, String gameTitle,
                    int points, int durationSeconds, LocalDateTime playedAt) {
        this.id              = id;
        this.playerUsername  = playerUsername;
        this.gameTitle       = gameTitle;
        this.points          = points;
        this.durationSeconds = durationSeconds;
        this.playedAt        = playedAt;
    }

    public UUID          getId()              { return id; }
    public String        getPlayerUsername()  { return playerUsername; }
    public String        getGameTitle()       { return gameTitle; }
    public int           getPoints()          { return points; }
    public int           getDurationSeconds() { return durationSeconds; }
    public LocalDateTime getPlayedAt()        { return playedAt; }

    @Override
    public String toString() {
        return String.format("ScoreDTO{player='%s', game='%s', points=%d}", playerUsername, gameTitle, points);
    }
}
