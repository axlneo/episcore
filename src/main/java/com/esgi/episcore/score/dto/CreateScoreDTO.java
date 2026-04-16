package com.esgi.episcore.score.dto;

import java.util.UUID;

/** DTO de création — fail-fast dans le constructeur. */
public final class CreateScoreDTO {

    private final UUID playerId;
    private final UUID gameId;
    private final int  points;
    private final int  durationSeconds;

    public CreateScoreDTO(UUID playerId, UUID gameId, int points, int durationSeconds) {
        if (playerId == null) throw new IllegalArgumentException("playerId ne peut pas être null");
        if (gameId == null)   throw new IllegalArgumentException("gameId ne peut pas être null");
        if (points < 0)       throw new IllegalArgumentException("points doit être >= 0");
        if (durationSeconds < 0) throw new IllegalArgumentException("durationSeconds doit être >= 0");

        this.playerId        = playerId;
        this.gameId          = gameId;
        this.points          = points;
        this.durationSeconds = durationSeconds;
    }

    public UUID getPlayerId()        { return playerId; }
    public UUID getGameId()          { return gameId; }
    public int  getPoints()          { return points; }
    public int  getDurationSeconds() { return durationSeconds; }
}
