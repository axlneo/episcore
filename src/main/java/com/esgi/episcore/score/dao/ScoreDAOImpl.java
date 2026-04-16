package com.esgi.episcore.score.dao;

import com.esgi.episcore.config.DatabaseConnection;
import com.esgi.episcore.score.dto.CreateScoreDTO;
import com.esgi.episcore.score.dto.ScoreDTO;
import com.esgi.episcore.util.AppLogger;
import com.esgi.episcore.util.Page;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
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

    @Override public ScoreDTO save(CreateScoreDTO dto) {

        Connection con = null;
        PreparedStatement pstmt = null;
        ScoreDTO score = null;

        String request = "INSERT INTO scores (player_id, game_id, points, duration_seconds, played_at) "
                        + "VALUES ('?', '?', ?, ?, '?')";

        try {
            con = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            log.log(Level.SEVERE, "La connection à la base de donnée a échoué.");
        }

        try {

            pstmt = con.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);
            pstmt.setObject(1, dto.getPlayerId());
            pstmt.setObject(2, dto.getPlayerId());
            pstmt.setInt(3, dto.getPoints());
            pstmt.setInt(4, dto.getDurationSeconds());
            pstmt.setObject(5,LocalDateTime.now());

            pstmt.executeUpdate();
            pstmt.getGeneratedKeys().next();
            long id = pstmt.getGeneratedKeys().getLong(1);

            request = "SELECT * FROM scores "
                    + "JOIN players ON score.player_id = players.id "
                    + "JOIN games ON score.game_id = games.id "
                    + "WHERE scores.id=? ";

            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                score = mapRow(rs);
            }

        } catch (SQLException e) {
            log.log(Level.SEVERE, "La création du statement a échoué.");
        }

        return score;

    }

    @Override public Optional<ScoreDTO> findById(UUID id) {
        
        Connection con = null;
        PreparedStatement pstmt = null;

        String request = "SELECT * FROM scores "
                        + "JOIN players ON score.player_id = players.id "
                        + "JOIN games ON score.game_id = games.id "
                        + "WHERE scores.id=?";

        try {
            con = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            log.log(Level.SEVERE, "La connection à la base de donnée a échoué.");
        }

        try {

            pstmt = con.prepareStatement(request);
            pstmt.setObject(1, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return Optional.ofNullable(mapRow(rs));
            }

        } catch (SQLException e) {
            log.log(Level.SEVERE, "La création du statement a échoué.");
        }

        return Optional.empty();

    }

    @Override public Page<ScoreDTO> findAll(int page, int size) {
        
        Connection con = null;
        PreparedStatement pstmt = null;

        String request = "SELECT * FROM scores "
                        + "JOIN players ON score.player_id = players.id "
                        + "JOIN games ON score.game_id = games.id "
                        + " LIMIT ? OFFSET ?";

        try {
            con = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            log.log(Level.SEVERE, "La connection à la base de donnée a échoué.");
        }

        try {

            pstmt = con.prepareStatement(request);
            pstmt.setInt(1, size);
            pstmt.setInt(2, page * size);

            ResultSet rs = pstmt.executeQuery();
            List<ScoreDTO> entries = new ArrayList<>();
            while (rs.next()) {
                entries.add(mapRow(rs));
            }

            return new Page<ScoreDTO>(entries, page, size, entries.size());

        } catch (SQLException e) {
            log.log(Level.SEVERE, "La création du statement a échoué.");
        }

        return new Page<ScoreDTO>(new ArrayList<>(), page, size, 0);

    }

    @Override public Page<ScoreDTO> findByPlayerId(UUID playerId, int page, int size) {

        Connection con = null;
        PreparedStatement pstmt = null;

        String request = "SELECT * FROM scores "
                        + "JOIN players ON score.player_id = players.id "
                        + "JOIN games ON score.game_id = games.id "
                        + "WHERE scores.player_id=? "
                        + "LIMIT ? OFFSET ?";

        try {
            con = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            log.log(Level.SEVERE, "La connection à la base de donnée a échoué.");
        }

        try {

            pstmt = con.prepareStatement(request);
            pstmt.setObject(1, playerId);
            pstmt.setInt(2, size);
            pstmt.setInt(3, page * size);

            ResultSet rs = pstmt.executeQuery();
            List<ScoreDTO> entries = new ArrayList<>();
            while (rs.next()) {
                entries.add(mapRow(rs));
            }

            return new Page<ScoreDTO>(entries, page, size, entries.size());

        } catch (SQLException e) {
            log.log(Level.SEVERE, "La création du statement a échoué.");
        }

        return new Page<ScoreDTO>(new ArrayList<>(), page, size, 0);

    }

    @Override public Page<ScoreDTO> findByGameId(UUID gameId, int page, int size) {

        Connection con = null;
        PreparedStatement pstmt = null;

        String request = "SELECT * FROM scores "
                        + "JOIN players ON score.player_id = players.id "
                        + "JOIN games ON score.game_id = games.id "
                        + "WHERE scores.game_id=? "
                        + "LIMIT ? OFFSET ?";

        try {
            con = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            log.log(Level.SEVERE, "La connection à la base de donnée a échoué.");
        }

        try {

            pstmt = con.prepareStatement(request);
            pstmt.setObject(1, gameId);
            pstmt.setInt(2, size);
            pstmt.setInt(3, page * size);

            ResultSet rs = pstmt.executeQuery();
            List<ScoreDTO> entries = new ArrayList<>();
            while (rs.next()) {
                entries.add(mapRow(rs));
            }

            return new Page<ScoreDTO>(entries, page, size, entries.size());

        } catch (SQLException e) {
            log.log(Level.SEVERE, "La création du statement a échoué.");
        }

        return new Page<ScoreDTO>(new ArrayList<>(), page, size, 0);

    }

    @Override public List<ScoreDTO> getTopScores(UUID gameId, int limit) {

        Connection con = null;
        PreparedStatement pstmt = null;

        String request = "SELECT * FROM scores "
                        + "JOIN players ON score.player_id = players.id "
                        + "JOIN games ON score.game_id = games.id "
                        + "SORT BY scores.points DESC "
                        + "LIMIT ?";

        try {
            con = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            log.log(Level.SEVERE, "La connection à la base de donnée a échoué.");
        }

        try {

            pstmt = con.prepareStatement(request);
            pstmt.setInt(1, limit);

            ResultSet rs = pstmt.executeQuery();
            List<ScoreDTO> entries = new ArrayList<>();
            while (rs.next()) {
                entries.add(mapRow(rs));
            }

            return entries;

        } catch (SQLException e) {
            log.log(Level.SEVERE, "La création du statement a échoué.");
        }

        return new ArrayList<>();

    }

    @Override public long count() {
        
        Connection con = null;
        PreparedStatement pstmt = null;

        String request = "SELECT COUNT(*) FROM scores";

        try {
            con = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            log.log(Level.SEVERE, "La connection à la base de donnée a échoué.");
        }

        try {

            pstmt = con.prepareStatement(request);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getLong(1);
            }

        } catch (SQLException e) {
            log.log(Level.SEVERE, "La création du statement a échoué.");
        }

        return 0;

    }

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
