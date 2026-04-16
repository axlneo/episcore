package com.esgi.episcore.game.dao;

import com.esgi.episcore.game.dto.CreateGameDTO;
import com.esgi.episcore.game.dto.GameDTO;
import com.esgi.episcore.game.model.Genre;
import com.esgi.episcore.util.Page;

import java.util.Optional;
import java.util.UUID;

public interface GameDAO {
    GameDTO             save(CreateGameDTO dto);
    Optional<GameDTO>   findById(UUID id);
    Page<GameDTO>       findAll(int page, int size);
    Page<GameDTO>       findAllActive(int page, int size);
    Page<GameDTO>       findByGenre(Genre genre, int page, int size);
    long                count();
    Optional<GameDTO>   findByTitle(String title);
    /** Met is_active = false pour ce jeu. */
    boolean             deactivate(UUID id);
}
