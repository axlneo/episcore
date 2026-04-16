package com.esgi.episcore.score.model;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entité Score — entièrement immuable.
 *
 * Règles métier (fail-fast dans le constructeur) :
 *   - playerId / gameId : non null
 *   - points            : >= 0
 *   - durationSeconds   : >= 0
 */
public final class Score {

    private final UUID          id;
    private final UUID          playerId;
    private final UUID          gameId;
    private final int           points;
    private final int           durationSeconds;
    private final LocalDateTime playedAt;

    public Score(UUID id, UUID playerId, UUID gameId,
                 int points, int durationSeconds, LocalDateTime playedAt) {

        if (id == null)       throw new IllegalArgumentException("id ne peut pas être null");
        if (playerId == null) throw new IllegalArgumentException("playerId ne peut pas être null");
        if (gameId == null)   throw new IllegalArgumentException("gameId ne peut pas être null");
        if (points < 0)       throw new IllegalArgumentException("points doit être >= 0, reçu : " + points);
        if (durationSeconds < 0) throw new IllegalArgumentException("durationSeconds doit être >= 0");
        if (playedAt == null) throw new IllegalArgumentException("playedAt ne peut pas être null");

        this.id              = id;
        this.playerId        = playerId;
        this.gameId          = gameId;
        this.points          = points;
        this.durationSeconds = durationSeconds;
        this.playedAt        = playedAt;
    }

    public UUID          getId()              { return id; }
    public UUID          getPlayerId()        { return playerId; }
    public UUID          getGameId()          { return gameId; }
    public int           getPoints()          { return points; }
    public int           getDurationSeconds() { return durationSeconds; }
    public LocalDateTime getPlayedAt()        { return playedAt; }

    @Override
    public String toString() {
        return String.format("Score{id=%s, player=%s, game=%s, points=%d}", id, playerId, gameId, points);
    }
}
