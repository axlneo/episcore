package com.esgi.episcore.player.dao;

import com.esgi.episcore.player.dto.CreatePlayerDTO;
import com.esgi.episcore.player.dto.PlayerDTO;
import com.esgi.episcore.util.Page;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Contrat d'accès aux données pour les Players.
 * Toutes les méthodes lancent une RuntimeException wrappant SQLException.
 */
public interface PlayerDAO {

    /** Persiste un nouveau player et retourne son DTO complet. */
    PlayerDTO save(CreatePlayerDTO dto);

    /** Retourne un player par son ID, ou Optional.empty() s'il n'existe pas. */
    Optional<PlayerDTO> findById(UUID id);

    /** Retourne un player par son username, ou Optional.empty(). */
    Optional<PlayerDTO> findByUsername(String username);

    /** Retourne un player par son email, ou Optional.empty(). */
    Optional<PlayerDTO> findByEmail(String email);

    /** Retourne tous les players paginés. */
    Page<PlayerDTO> findAll(int page, int size);

    /** Retourne les N joueurs avec le plus d'XP. */
    List<PlayerDTO> findTopPlayersByXp(int limit);

    /** Nombre total de players en base. */
    long count();

    /**
     * Met à jour la progression d'un joueur (xp + level) et retourne le DTO mis à jour.
     * (Le calcul du level doit rester côté Service.)
     */
    PlayerDTO updateProgress(UUID id, int xp, int level);

    /** Supprime un player par son ID. Retourne true si supprimé. */
    boolean deleteById(UUID id);
}
