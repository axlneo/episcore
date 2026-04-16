package com.esgi.episcore.game.service;

import com.esgi.episcore.game.dao.GameDAO;
import com.esgi.episcore.game.dto.CreateGameDTO;
import com.esgi.episcore.game.dto.GameDTO;
import com.esgi.episcore.game.exception.GameAlreadyExistsException;
import com.esgi.episcore.game.exception.GameNotFoundException;
import com.esgi.episcore.game.model.Genre;
import com.esgi.episcore.util.AppLogger;
import com.esgi.episcore.util.Page;

import java.util.Arrays;
import java.util.UUID;
import java.util.logging.Logger;

public class GameServiceImpl implements GameService {

    private static final Logger log = AppLogger.getLogger(GameServiceImpl.class);

    private final GameDAO gameDAO;

    public GameServiceImpl(GameDAO gameDAO) {
        if (gameDAO == null) throw new IllegalArgumentException("gameDAO ne peut pas être null");
        this.gameDAO = gameDAO;
    }

    @Override
    public GameDTO createGame(CreateGameDTO dto) {
        if (gameDAO.findByTitle(dto.getTitle()).isPresent()) {
            throw new GameAlreadyExistsException(dto.getTitle());
        }

        Page<GameDTO> allGames = gameDAO.findAll(0, 1000);
        boolean alreadyExists = allGames.getContent().stream()
                .anyMatch(g -> g.getTitle().equalsIgnoreCase(dto.getTitle()));

        if (alreadyExists) {
            throw new GameAlreadyExistsException(dto.getTitle());
        }

        return gameDAO.save(dto);
    }

    @Override
    public GameDTO getById(UUID id) {
        return gameDAO.findById(id).orElseThrow(() -> new GameNotFoundException(id));
    }

    @Override
    public Page<GameDTO> getAll(int page, int size) {
        return gameDAO.findAll(page, size);
    }

    @Override
    public Page<GameDTO> getActiveGames(int page, int size) {
        return gameDAO.findAllActive(page, size);
    }

    @Override
    public Page<GameDTO> getByGenre(Genre genre, int page, int size) {
        return gameDAO.findByGenre(genre, page, size);
    }

    @Override
    public void deactivateGame(UUID id) {
        // Lève une exception si le jeu n'existe pas
        GameDTO game = getById(id);

        log.warning("Désactivation demandée pour le jeu : " + game.getTitle() + " (" + id + ")");
        boolean success = gameDAO.deactivate(id);

        if (!success) {
            log.severe("Échec critique lors de la désactivation du jeu : " + id);
        }
    }

    @Override
    public void printStats() {
        long totalGames = gameDAO.count();

        System.out.println("\n=== STATISTIQUES DU CATALOGUE DE JEUX ===");
        System.out.println("Total de jeux enregistrés : " + totalGames);
        System.out.println("Genres disponibles        : " + Arrays.toString(Genre.values()));
        System.out.println("============================================\n");
    }
}