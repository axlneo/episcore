package com.esgi.episcore.score.seeder;

import com.esgi.episcore.score.dto.CreateScoreDTO;
import com.esgi.episcore.score.service.ScoreService;
import com.esgi.episcore.util.AppLogger;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * TODO Groupe 3 — Générer 2000 scores fictifs.
 *
 *   ⚠️  Vous avez besoin de player_ids et game_ids existants en base.
 *       Récupérez-les depuis votre DAO (ou demandez les listes aux groupes 1 et 2).
 *
 *   Exemple :
 *     List<UUID> playerIds = playerDAO.findAll(0, 500).getContent()
 *                                     .stream().map(PlayerDTO::getId).toList();
 *     List<UUID> gameIds   = gameDAO.findAll(0, 50).getContent()
 *                                   .stream().map(GameDTO::getId).toList();
 *
 *   Ensuite boucler 2000 fois avec combinaisons aléatoires.
 *   Logger toutes les 200 insertions.
 */
public class ScoreSeeder {

    private static final int TARGET_SCORES = 2000;
    private static final int LOG_BATCH_SIZE = 200;

    private static final Logger log = AppLogger.getLogger(ScoreSeeder.class);

    private final ScoreService scoreService;
    private final Random random = new Random();

    public ScoreSeeder(ScoreService scoreService) {
        if (scoreService == null) {
            throw new IllegalArgumentException("scoreService ne peut pas être null");
        }
        this.scoreService = scoreService;
    }

    public void seed(List<UUID> playerIds, List<UUID> gameIds) {
        log.info("Démarrage du seeder Scores — cible : 2000 scores");
        if (playerIds == null || gameIds == null) {
            log.warning("Impossible de seeder : listes playerIds/gameIds nulles !");
            return;
        }
        if (playerIds.isEmpty() || gameIds.isEmpty()) {
            log.warning("Impossible de seeder : aucun player ou game en base !");
            return;
        }

        for (int i = 1; i <= TARGET_SCORES; i++) {
            UUID playerId = playerIds.get(random.nextInt(playerIds.size()));
            UUID gameId = gameIds.get(random.nextInt(gameIds.size()));

            CreateScoreDTO dto = new CreateScoreDTO(
                playerId,
                gameId,
                random.nextInt(10_001),
                30 + random.nextInt(3_571)
            );

            scoreService.addScore(dto);

            if (i % LOG_BATCH_SIZE == 0) {
                log.info("Scores seedés : " + i + "/" + TARGET_SCORES);
            }
        }

        log.info("Seeder Scores terminé : " + TARGET_SCORES + " scores insérés");
    }
}
