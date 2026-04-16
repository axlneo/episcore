package com.esgi.episcore;

import com.esgi.episcore.game.dao.GameDAOImpl;
import com.esgi.episcore.game.seeder.GameSeeder;
import com.esgi.episcore.game.service.GameServiceImpl;
import com.esgi.episcore.player.dao.PlayerDAOImpl;
import com.esgi.episcore.player.seeder.PlayerSeeder;
import com.esgi.episcore.player.service.PlayerServiceImpl;
import com.esgi.episcore.score.dao.ScoreDAOImpl;
import com.esgi.episcore.score.seeder.ScoreSeeder;
import com.esgi.episcore.score.service.ScoreServiceImpl;
import com.esgi.episcore.util.AppLogger;

import java.util.logging.Logger;

/**
 * Point d'entrée commun — décommenter les blocs au fur et à mesure.
 */
public class Main {

    private static final Logger log = AppLogger.getLogger(Main.class);

    public static void main(String[] args) {

        log.info("=== EpiScore démarré ===");

        // ── Instanciation des couches ────────────────────────────────
        var playerDAO     = new PlayerDAOImpl();
        var playerService = new PlayerServiceImpl(playerDAO);

        var gameDAO     = new GameDAOImpl();
        var gameService = new GameServiceImpl(gameDAO);

        var scoreDAO     = new ScoreDAOImpl();
        var scoreService = new ScoreServiceImpl(scoreDAO);

        // ── CHECKPOINT 1 — tester save() + findById() ───────────────
        // Décommenter pour tester :
        // var dto = new com.esgi.episcore.player.dto.CreatePlayerDTO("test_user", "test@esgi.fr");
        // var saved = playerService.createPlayer(dto);
        // log.info("Créé : " + saved);
        // var found = playerService.getById(saved.getId());
        // log.info("Trouvé : " + found);

        // ── CHECKPOINT 2 — seeders ───────────────────────────────────
        // new PlayerSeeder(playerService).seed();
        // new GameSeeder(gameService).seed();

        // ── CHECKPOINT 2 — pagination ────────────────────────────────
        // var page0 = playerService.getAll(0, 10);
        // log.info("Page 0 : " + page0);
        // var page1 = playerService.getAll(1, 10);
        // log.info("Page 1 : " + page1);

        // ── CHECKPOINT 3 — stats ─────────────────────────────────────
        // playerService.printStats();
        // gameService.printStats();
        // scoreService.printStats();

        // ── CHECKPOINT 3 — score seeder (après players + games) ──────
        // var playerIds = playerDAO.findAll(0,500).getContent().stream()
        //     .map(p -> p.getId()).toList();
        // var gameIds = gameDAO.findAll(0,50).getContent().stream()
        //     .map(g -> g.getId()).toList();
        // new ScoreSeeder(scoreService).seed(playerIds, gameIds);

        log.info("=== EpiScore terminé ===");
    }
}
