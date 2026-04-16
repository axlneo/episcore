package com.esgi.episcore.score.dao;

import com.esgi.episcore.config.DatabaseConnection;
import com.esgi.episcore.score.dto.CreateScoreDTO;
import com.esgi.episcore.score.dto.ScoreDTO;
import com.esgi.episcore.util.AppLogger;
import com.esgi.episcore.util.Page;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * TODO Groupe 3 — Implémenter toutes les méthodes.
 *
 * Point important : ScoreDTO est enrichi (playerUsername + gameTitle).
 * Utilisez un JOIN dans vos requêtes SELECT :
 *
 *   SELECT s.id, p.username, g.title, s.points, s.duration_seconds, s.played_at
 *   FROM scores s
 *   JOIN players p ON s.player_id = p.id
 *   JOIN games   g ON s.game_id   = g.id
 *   WHERE ...
 *
 * getTopScores() :
 *   ... ORDER BY s.points DESC LIMIT ?
 */
public class ScoreDAOImpl implements ScoreDAO {

    private static final Logger log = AppLogger.getLogger(ScoreDAOImpl.class);

    @Override public ScoreDTO           save(CreateScoreDTO dto)                         { throw new UnsupportedOperationException("À implémenter"); }
    @Override public Optional<ScoreDTO> findById(UUID id)                                { throw new UnsupportedOperationException("À implémenter"); }
    @Override public Page<ScoreDTO>     findAll(int page, int size)                      { throw new UnsupportedOperationException("À implémenter"); }
    @Override public Page<ScoreDTO>     findByPlayerId(UUID playerId, int page, int size){ throw new UnsupportedOperationException("À implémenter"); }
    @Override public Page<ScoreDTO>     findByGameId(UUID gameId, int page, int size)    { throw new UnsupportedOperationException("À implémenter"); }
    @Override public List<ScoreDTO>     getTopScores(UUID gameId, int limit)             { throw new UnsupportedOperationException("À implémenter"); }
    @Override public long               count()                                          { throw new UnsupportedOperationException("À implémenter"); }

    private ScoreDTO mapRow(ResultSet rs) throws SQLException {
        return new ScoreDTO(
            UUID.fromString(rs.getString("id")),
            rs.getString("username"),      // alias du JOIN players
            rs.getString("title"),         // alias du JOIN games
            rs.getInt("points"),
            rs.getInt("duration_seconds"),
            rs.getTimestamp("played_at").toLocalDateTime()
        );
    }
}
