package com.esgi.episcore.player.seeder;

import com.esgi.episcore.player.dto.CreatePlayerDTO;
import com.esgi.episcore.player.service.PlayerService;
import com.esgi.episcore.util.AppLogger;

import java.util.Random;
import java.util.logging.Logger;

/**
 * Génère 500 joueurs fictifs en base.
 *
 * TODO Groupe 1 :
 *   1. Créer une boucle for de 500 itérations
 *   2. Générer username unique : "player_" + i  (ou noms aléatoires)
 *   3. Générer email unique : "player" + i + "@esgi.fr"
 *   4. Appeler playerService.createPlayer(dto)
 *   5. Logger la progression toutes les 100 insertions (log.info)
 *   6. Gérer les erreurs avec un try/catch (certains usernames peuvent déjà exister)
 *
 * Bonus — batch JDBC pour de meilleures performances :
 *   Utiliser PreparedStatement + addBatch() + executeBatch()
 *   plutôt que d'appeler save() une fois par joueur.
 */
public class PlayerSeeder {

    private static final Logger log = AppLogger.getLogger(PlayerSeeder.class);

    private final PlayerService playerService;

    public PlayerSeeder(PlayerService playerService) {
        this.playerService = playerService;
    }

    public void seed() {
        log.info("Démarrage du seeder Players — cible : 500 joueurs");
        // TODO
        throw new UnsupportedOperationException("seed() — À implémenter");
    }
}
