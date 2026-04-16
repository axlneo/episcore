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

    private static final Logger log = AppLogger.getLogger(ScoreSeeder.class);

    private final ScoreService scoreService;

    public ScoreSeeder(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    public void seed(List<UUID> playerIds, List<UUID> gameIds) {
        log.info("Démarrage du seeder Scores — cible : 2000 scores");
        if (playerIds.isEmpty() || gameIds.isEmpty()) {
            log.warning("Impossible de seeder : aucun player ou game en base !");
            return;
        }
        // TODO
        throw new UnsupportedOperationException("seed() — À implémenter");
    }
}
