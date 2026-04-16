package com.esgi.episcore.game.service;

import com.esgi.episcore.game.dao.GameDAO;
import com.esgi.episcore.game.dto.CreateGameDTO;
import com.esgi.episcore.game.dto.GameDTO;
import com.esgi.episcore.game.exception.GameAlreadyExistsException;
import com.esgi.episcore.game.exception.GameNotFoundException;
import com.esgi.episcore.game.model.Genre;
import com.esgi.episcore.util.AppLogger;
import com.esgi.episcore.util.Page;

import java.util.UUID;
import java.util.logging.Logger;

/**
 * TODO Groupe 2 :
 *   createGame()    → vérifier unicité du titre, sinon GameAlreadyExistsException
 *   getById()       → Optional → GameNotFoundException si absent
 *   deactivateGame()→ logger WARNING + appeler gameDAO.deactivate()
 *   printStats()    → count total, nb actifs, liste des genres disponibles
 */
public class GameServiceImpl implements GameService {

    private static final Logger log = AppLogger.getLogger(GameServiceImpl.class);

    private final GameDAO gameDAO;

    public GameServiceImpl(GameDAO gameDAO) {
        if (gameDAO == null) throw new IllegalArgumentException("gameDAO ne peut pas être null");
        this.gameDAO = gameDAO;
    }

    @Override public GameDTO       createGame(CreateGameDTO dto) { throw new UnsupportedOperationException("À implémenter"); }
    @Override public GameDTO       getById(UUID id)              { throw new UnsupportedOperationException("À implémenter"); }
    @Override public Page<GameDTO> getAll(int page, int size)    { throw new UnsupportedOperationException("À implémenter"); }
    @Override public Page<GameDTO> getActiveGames(int p, int s)  { throw new UnsupportedOperationException("À implémenter"); }
    @Override public Page<GameDTO> getByGenre(Genre g, int p, int s) { throw new UnsupportedOperationException("À implémenter"); }
    @Override public void          deactivateGame(UUID id)       { throw new UnsupportedOperationException("À implémenter"); }
    @Override public void          printStats()                  { throw new UnsupportedOperationException("À implémenter"); }
}
