package com.esgi.episcore.player.dao;

import com.esgi.episcore.config.DatabaseConnection;
import com.esgi.episcore.player.dto.CreatePlayerDTO;
import com.esgi.episcore.player.dto.PlayerDTO;
import com.esgi.episcore.util.AppLogger;
import com.esgi.episcore.util.Page;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Implémentation JDBC de PlayerDAO.
 *
 * TODO Groupe 1 — Implémenter toutes les méthodes :
 *   1. save()              → INSERT INTO players ...
 *   2. findById()          → SELECT * FROM players WHERE id = ?
 *   3. findByUsername()    → SELECT * FROM players WHERE username = ?
 *   4. findAll(page, size) → SELECT * FROM players LIMIT ? OFFSET ?  +  count()
 *   5. findTopPlayersByXp  → SELECT * FROM players ORDER BY xp DESC LIMIT ?
 *   6. count()             → SELECT COUNT(*) FROM players
 *   7. deleteById()        → DELETE FROM players WHERE id = ?
 *
 * Rappels :
 *   - Toujours utiliser try-with-resources pour Connection, PreparedStatement, ResultSet
 *   - Logger INFO au début de chaque méthode, WARNING si not found, SEVERE si SQLException
 *   - NE PAS mettre de logique métier ici (calcul de level, unicité username → Service)
 */
public class PlayerDAOImpl implements PlayerDAO {

    private static final Logger log = AppLogger.getLogger(PlayerDAOImpl.class);

    @Override
    public PlayerDTO save(CreatePlayerDTO dto) {
        // TODO
        throw new UnsupportedOperationException("save() — À implémenter");
    }

    @Override
    public Optional<PlayerDTO> findById(UUID id) {
        // TODO
        throw new UnsupportedOperationException("findById() — À implémenter");
    }

    @Override
    public Optional<PlayerDTO> findByUsername(String username) {
        // TODO
        throw new UnsupportedOperationException("findByUsername() — À implémenter");
    }

    @Override
    public Page<PlayerDTO> findAll(int page, int size) {
        // TODO  SELECT * FROM players LIMIT ? OFFSET ?
        // int offset = page * size;
        throw new UnsupportedOperationException("findAll() — À implémenter");
    }

    @Override
    public List<PlayerDTO> findTopPlayersByXp(int limit) {
        // TODO  SELECT * FROM players ORDER BY xp DESC LIMIT ?
        throw new UnsupportedOperationException("findTopPlayersByXp() — À implémenter");
    }

    @Override
    public long count() {
        // TODO  SELECT COUNT(*) FROM players
        throw new UnsupportedOperationException("count() — À implémenter");
    }

    @Override
    public boolean deleteById(UUID id) {
        // TODO  DELETE FROM players WHERE id = ?
        throw new UnsupportedOperationException("deleteById() — À implémenter");
    }

    // ── helper ───────────────────────────────────────────────
    /**
     * Convertit un ResultSet en PlayerDTO.
     * À appeler après rs.next().
     */
    private PlayerDTO mapRow(ResultSet rs) throws SQLException {
        return new PlayerDTO(
            UUID.fromString(rs.getString("id")),
            rs.getString("username"),
            rs.getInt("level"),
            rs.getInt("xp"),
            rs.getTimestamp("created_at").toLocalDateTime()
        );
    }
}
