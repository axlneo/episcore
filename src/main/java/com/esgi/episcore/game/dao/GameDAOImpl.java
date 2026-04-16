package com.esgi.episcore.game.dao;

import com.esgi.episcore.config.DatabaseConnection;
import com.esgi.episcore.game.dto.CreateGameDTO;
import com.esgi.episcore.game.dto.GameDTO;
import com.esgi.episcore.game.model.Genre;
import com.esgi.episcore.util.AppLogger;
import com.esgi.episcore.util.Page;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * TODO Groupe 2 — Implémenter toutes les méthodes.
 * Même structure que PlayerDAOImpl : try-with-resources, logs, mapRow().
 */
public class GameDAOImpl implements GameDAO {

    private static final Logger log = AppLogger.getLogger(GameDAOImpl.class);

    @Override
    public GameDTO save(CreateGameDTO dto) {
        throw new UnsupportedOperationException("save() — À implémenter");
    }

    @Override
    public Optional<GameDTO> findById(UUID id) {
        throw new UnsupportedOperationException("findById() — À implémenter");
    }

    @Override
    public Page<GameDTO> findAll(int page, int size) {
        throw new UnsupportedOperationException("findAll() — À implémenter");
    }

    @Override
    public Page<GameDTO> findAllActive(int page, int size) {
        // TODO  WHERE is_active = true
        throw new UnsupportedOperationException("findAllActive() — À implémenter");
    }

    @Override
    public Page<GameDTO> findByGenre(Genre genre, int page, int size) {
        // TODO  WHERE genre = ?
        throw new UnsupportedOperationException("findByGenre() — À implémenter");
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("count() — À implémenter");
    }

    @Override
    public boolean deactivate(UUID id) {
        // TODO  UPDATE games SET is_active = false WHERE id = ?
        throw new UnsupportedOperationException("deactivate() — À implémenter");
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
