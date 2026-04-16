package com.esgi.episcore.game.seeder;

import com.esgi.episcore.game.dto.CreateGameDTO;
import com.esgi.episcore.game.service.GameService;
import com.esgi.episcore.util.AppLogger;

import java.util.logging.Logger;

/**
 * TODO Groupe 2 — Générer 50 jeux fictifs.
 *
 *   Idées de titres : "EpiCraft", "Legend of ESGI", "Turbo Race 3000"...
 *   Genres à varier : ACTION, RPG, SPORT, PUZZLE, FPS, STRATEGY
 *   maxPlayers : entre 1 et 64
 *
 *   Logger la progression tous les 10 jeux.
 */
public class GameSeeder {

    private static final Logger log = AppLogger.getLogger(GameSeeder.class);

    private final GameService gameService;

    public GameSeeder(GameService gameService) {
        this.gameService = gameService;
    }

    public void seed() {
        log.info("Démarrage du seeder Games — cible : 50 jeux");
        // TODO
        throw new UnsupportedOperationException("seed() — À implémenter");
    }
}
