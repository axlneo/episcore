package com.esgi.episcore.game.dao;

import com.esgi.episcore.config.DatabaseConnection;
import com.esgi.episcore.game.dto.CreateGameDTO;
import com.esgi.episcore.game.dto.GameDTO;
import com.esgi.episcore.game.model.Genre;
import com.esgi.episcore.util.AppLogger;
import com.esgi.episcore.util.Page;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameDAOImpl implements GameDAO {
    private static final Logger log = AppLogger.getLogger(GameDAOImpl.class);

    @Override
    public GameDTO save(CreateGameDTO dto) {
        log.info("Sauvegarde du jeu : " + dto.getTitle());
        String sql = "INSERT INTO games (title, genre, max_players) VALUES (?, ?, ?) RETURNING *";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, dto.getTitle());
            pstmt.setString(2, dto.getGenre().name());
            pstmt.setInt(3, dto.getMaxPlayers());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Erreur lors de la sauvegarde du jeu", e);
        }
        return null;
    }

    @Override
    public Optional<GameDTO> findById(UUID id) {
        log.info("Recherche du jeu par ID : " + id);
        String sql = "SELECT * FROM games WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setObject(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Erreur findById", e);
        }
        log.warning("Jeu non trouvé : " + id);
        return Optional.empty();
    }

    @Override
    public Page<GameDTO> findAll(int page, int size) {
        return getPagedResults("SELECT * FROM games ORDER BY created_at DESC", page, size);
    }

    @Override
    public Page<GameDTO> findAllActive(int page, int size) {
        return getPagedResults("SELECT * FROM games WHERE is_active = true ORDER BY title", page, size);
    }

    @Override
    public Page<GameDTO> findByGenre(Genre genre, int page, int size) {
        String sql = "SELECT * FROM games WHERE genre = ? ORDER BY title";
        List<GameDTO> content = new ArrayList<>();
        long total = countByGenre(genre);

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql + " LIMIT ? OFFSET ?")) {
            pstmt.setString(1, genre.name());
            pstmt.setInt(2, size);
            pstmt.setInt(3, page * size);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) content.add(mapRow(rs));
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Erreur findByGenre", e);
        }
        return new Page<>(content, page, size, total);
    }

    @Override
    public boolean deactivate(UUID id) {
        log.warning("Désactivation du jeu : " + id);
        String sql = "UPDATE games SET is_active = false WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setObject(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Erreur deactivate", e);
            return false;
        }
    }

    @Override
    public long count() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM games")) {
            if (rs.next()) return rs.getLong(1);
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Erreur count", e);
        }
        return 0;
    }

    private long countByGenre(Genre genre) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) FROM games WHERE genre = ?")) {
            pstmt.setString(1, genre.name());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return rs.getLong(1);
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Erreur countByGenre", e);
        }
        return 0;
    }

    private Page<GameDTO> getPagedResults(String baseSql, int page, int size) {
        List<GameDTO> content = new ArrayList<>();
        long total = count();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(baseSql + " LIMIT ? OFFSET ?")) {
            pstmt.setInt(1, size);
            pstmt.setInt(2, page * size);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) content.add(mapRow(rs));
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, "Erreur pagination", e);
        }
        return new Page<>(content, page, size, total);
    }

    private GameDTO mapRow(ResultSet rs) throws SQLException {
        return new GameDTO(
                UUID.fromString(rs.getString("id")),
                rs.getString("title"),
                Genre.from(rs.getString("genre")),
                rs.getInt("max_players"),
                rs.getBoolean("is_active"),
                rs.getTimestamp("created_at").toLocalDateTime()
        );
    }
}