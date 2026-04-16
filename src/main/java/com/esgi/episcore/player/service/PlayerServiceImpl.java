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
        // TODO
        throw new UnsupportedOperationException("createPlayer() — À implémenter");
    }

    @Override
    public PlayerDTO getById(UUID id) {
        // TODO
        throw new UnsupportedOperationException("getById() — À implémenter");
    }

    @Override
    public Page<PlayerDTO> getAll(int page, int size) {
        // TODO — déléguer au DAO
        throw new UnsupportedOperationException("getAll() — À implémenter");
    }

    @Override
    public List<PlayerDTO> getTopPlayers(int limit) {
        // TODO
        throw new UnsupportedOperationException("getTopPlayers() — À implémenter");
    }

    @Override
    public void printStats() {
        // TODO
        //   long total = playerDAO.count();
        //   List<PlayerDTO> top3 = playerDAO.findTopPlayersByXp(3);
        //   log.info("=== PLAYER STATS === Total: " + total);
        throw new UnsupportedOperationException("printStats() — À implémenter");
    }
}
