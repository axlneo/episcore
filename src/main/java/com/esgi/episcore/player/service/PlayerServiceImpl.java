package com.esgi.episcore.player.service;

import com.esgi.episcore.player.dao.PlayerDAO;
import com.esgi.episcore.player.dto.CreatePlayerDTO;
import com.esgi.episcore.player.dto.PlayerDTO;
import com.esgi.episcore.player.exception.PlayerAlreadyExistsException;
import com.esgi.episcore.player.exception.PlayerNotFoundException;
import com.esgi.episcore.util.AppLogger;
import com.esgi.episcore.util.Page;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * TODO Groupe 1 — Implémenter la logique métier :
 *
 *   createPlayer() :
 *     1. Vérifier via playerDAO.findByUsername() que le username n'existe pas
 *     2. Lever PlayerAlreadyExistsException si doublon
 *     3. Déléguer la création au DAO
 *
 *   getById() :
 *     1. Appeler playerDAO.findById()
 *     2. Lever PlayerNotFoundException si Optional.empty()
 *
 *   printStats() :
 *     - Afficher le count total
 *     - Afficher les 3 meilleurs joueurs
 */
public class PlayerServiceImpl implements PlayerService {

    private static final Logger log = AppLogger.getLogger(PlayerServiceImpl.class);

    private final PlayerDAO playerDAO;

    public PlayerServiceImpl(PlayerDAO playerDAO) {
        if (playerDAO == null) throw new IllegalArgumentException("playerDAO ne peut pas être null");
        this.playerDAO = playerDAO;
    }

    @Override
    public PlayerDTO createPlayer(CreatePlayerDTO dto) {
        if (dto == null) throw new IllegalArgumentException("dto ne peut pas être null");

        log.info("PlayerService.createPlayer(username=" + dto.getUsername() + ")");

        if (playerDAO.findByUsername(dto.getUsername()).isPresent()) {
            throw new PlayerAlreadyExistsException(dto.getUsername());
        }
        if (playerDAO.findByEmail(dto.getEmail()).isPresent()) {
            throw new PlayerAlreadyExistsException("email", dto.getEmail());
        }

        return playerDAO.save(dto);
    }

    @Override
    public PlayerDTO getById(UUID id) {
        if (id == null) throw new IllegalArgumentException("id ne peut pas être null");

        log.info("PlayerService.getById(id=" + id + ")");
        return playerDAO.findById(id).orElseThrow(() -> new PlayerNotFoundException(id));
    }

    @Override
    public Page<PlayerDTO> getAll(int page, int size) {
        log.info("PlayerService.getAll(page=" + page + ", size=" + size + ")");
        return playerDAO.findAll(page, size);
    }

    @Override
    public List<PlayerDTO> getTopPlayers(int limit) {
        if (limit <= 0) throw new IllegalArgumentException("limit doit être > 0");
        log.info("PlayerService.getTopPlayers(limit=" + limit + ")");
        return playerDAO.findTopPlayersByXp(limit);
    }

    @Override
    public PlayerDTO setXp(UUID id, int xp) {
        if (id == null) throw new IllegalArgumentException("id ne peut pas être null");
        if (xp < 0) throw new IllegalArgumentException("xp doit être >= 0, reçu : " + xp);

        int level = computeLevelFromXp(xp);
        log.info("PlayerService.setXp(id=" + id + ", xp=" + xp + ", level=" + level + ")");
        return playerDAO.updateProgress(id, xp, level);
    }

    @Override
    public void printStats() {
        long total = playerDAO.count();
        List<PlayerDTO> top3 = playerDAO.findTopPlayersByXp(3);

        log.info("=== PLAYER STATS ===");
        log.info("Total players: " + total);
        log.info("Top 3 (xp): " + top3);
    }

    private int computeLevelFromXp(int xp) {
        // Règle simple et stable pour le TP :
        // 0-999 => level 1, 1000-1999 => level 2, etc.
        return (xp / 1000) + 1;
    }
}
