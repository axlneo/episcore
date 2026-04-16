package com.esgi.episcore.player.service;

import com.esgi.episcore.player.dto.CreatePlayerDTO;
import com.esgi.episcore.player.dto.PlayerDTO;
import com.esgi.episcore.util.Page;

import java.util.List;
import java.util.UUID;

/**
 * Logique métier Players.
 * Toutes les opérations passent par ici — jamais d'accès direct au DAO depuis Main.
 */
public interface PlayerService {

    /**
     * Crée un joueur.
     * @throws com.esgi.episcore.player.exception.PlayerAlreadyExistsException si username déjà pris
     */
    PlayerDTO createPlayer(CreatePlayerDTO dto);

    /**
     * Retourne un joueur par ID.
     * @throws com.esgi.episcore.player.exception.PlayerNotFoundException si inconnu
     */
    PlayerDTO getById(UUID id);

    /** Retourne tous les joueurs paginés. */
    Page<PlayerDTO> getAll(int page, int size);

    /** Retourne les N meilleurs joueurs par XP. */
    List<PlayerDTO> getTopPlayers(int limit);

    /** Met à jour l'XP d'un joueur (et recalcule son level). */
    PlayerDTO setXp(UUID id, int xp);

    /** Affiche en console des statistiques globales. */
    void printStats();
}
