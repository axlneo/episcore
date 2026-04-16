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
        log.info("PlayerDAO.save(username=" + dto.getUsername() + ")");
        final String sql = """
            INSERT INTO players (username, email, level, xp)
            VALUES (?, ?, ?, ?)
            RETURNING id, username, level, xp, created_at
            """;

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, dto.getUsername());
            ps.setString(2, dto.getEmail());
            ps.setInt(3, 1);
            ps.setInt(4, 0);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new SQLException("INSERT players n'a retourné aucune ligne");
                }
                return mapRow(rs);
            }
        } catch (SQLException e) {
            log.severe("Erreur SQL dans save(): " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<PlayerDTO> findById(UUID id) {
        log.info("PlayerDAO.findById(id=" + id + ")");
        final String sql = "SELECT id, username, level, xp, created_at FROM players WHERE id = ?";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    log.warning("Player introuvable (id=" + id + ")");
                    return Optional.empty();
                }
                return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            log.severe("Erreur SQL dans findById(): " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<PlayerDTO> findByUsername(String username) {
        log.info("PlayerDAO.findByUsername(username=" + username + ")");
        final String sql = "SELECT id, username, level, xp, created_at FROM players WHERE username = ?";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    log.warning("Player introuvable (username=" + username + ")");
                    return Optional.empty();
                }
                return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            log.severe("Erreur SQL dans findByUsername(): " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<PlayerDTO> findByEmail(String email) {
        log.info("PlayerDAO.findByEmail(email=" + email + ")");
        final String sql = "SELECT id, username, level, xp, created_at FROM players WHERE email = ?";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    log.warning("Player introuvable (email=" + email + ")");
                    return Optional.empty();
                }
                return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            log.severe("Erreur SQL dans findByEmail(): " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Page<PlayerDTO> findAll(int page, int size) {
        log.info("PlayerDAO.findAll(page=" + page + ", size=" + size + ")");
        final String sql = """
            SELECT id, username, level, xp, created_at
            FROM players
            ORDER BY created_at DESC
            LIMIT ? OFFSET ?
            """;

        final int offset = page * size;
        final List<PlayerDTO> items = new ArrayList<>();

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, size);
            ps.setInt(2, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    items.add(mapRow(rs));
                }
            }

            long total = count();
            return new Page<>(items, page, size, total);
        } catch (SQLException e) {
            log.severe("Erreur SQL dans findAll(): " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PlayerDTO> findTopPlayersByXp(int limit) {
        log.info("PlayerDAO.findTopPlayersByXp(limit=" + limit + ")");
        final String sql = """
            SELECT id, username, level, xp, created_at
            FROM players
            ORDER BY xp DESC, created_at ASC
            LIMIT ?
            """;

        final List<PlayerDTO> items = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    items.add(mapRow(rs));
                }
            }
            return items;
        } catch (SQLException e) {
            log.severe("Erreur SQL dans findTopPlayersByXp(): " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public long count() {
        log.info("PlayerDAO.count()");
        final String sql = "SELECT COUNT(*) AS total FROM players";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (!rs.next()) return 0L;
            return rs.getLong("total");
        } catch (SQLException e) {
            log.severe("Erreur SQL dans count(): " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public PlayerDTO updateProgress(UUID id, int xp, int level) {
        log.info("PlayerDAO.updateProgress(id=" + id + ", xp=" + xp + ", level=" + level + ")");
        final String sql = """
            UPDATE players
            SET xp = ?, level = ?
            WHERE id = ?
            RETURNING id, username, level, xp, created_at
            """;

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, xp);
            ps.setInt(2, level);
            ps.setObject(3, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    log.warning("Mise à jour impossible, player introuvable (id=" + id + ")");
                    throw new RuntimeException("Player introuvable (id=" + id + ")");
                }
                return mapRow(rs);
            }
        } catch (SQLException e) {
            log.severe("Erreur SQL dans updateProgress(): " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(UUID id) {
        log.info("PlayerDAO.deleteById(id=" + id + ")");
        final String sql = "DELETE FROM players WHERE id = ?";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setObject(1, id);
            int updated = ps.executeUpdate();
            if (updated == 0) {
                log.warning("Suppression impossible, player introuvable (id=" + id + ")");
            }
            return updated > 0;
        } catch (SQLException e) {
            log.severe("Erreur SQL dans deleteById(): " + e.getMessage());
            throw new RuntimeException(e);
        }
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
