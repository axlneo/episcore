package com.esgi.episcore.game.service;

import com.esgi.episcore.game.dto.CreateGameDTO;
import com.esgi.episcore.game.dto.GameDTO;
import com.esgi.episcore.game.model.Genre;
import com.esgi.episcore.util.Page;

import java.util.UUID;

public interface GameService {
    GameDTO       createGame(CreateGameDTO dto);
    GameDTO       getById(UUID id);
    Page<GameDTO> getAll(int page, int size);
    Page<GameDTO> getActiveGames(int page, int size);
    Page<GameDTO> getByGenre(Genre genre, int page, int size);
    void          deactivateGame(UUID id);
    void          printStats();
}
