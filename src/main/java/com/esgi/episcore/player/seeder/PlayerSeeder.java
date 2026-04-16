package com.esgi.episcore.player.seeder;

import com.esgi.episcore.player.dto.CreatePlayerDTO;
import com.esgi.episcore.player.service.PlayerService;
import com.esgi.episcore.util.AppLogger;

import java.util.Random;
import java.util.UUID;
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
        if (playerService == null) throw new IllegalStateException("playerService ne peut pas être null");

        long existing = playerService.getAll(0, 1).getTotalElements();
        if (existing > 0) {
            // Si la base est déjà remplie mais que tout est à 0 XP (cas courant),
            // on "backfill" des XP pour rendre les stats/top3 parlants.
            var top1 = playerService.getTopPlayers(1);
            if (!top1.isEmpty() && top1.getFirst().getXp() == 0) {
                log.info("Backfill XP: base déjà remplie (" + existing + " joueurs) mais XP=0, mise à jour en cours...");
                backfillXpForAllPlayers(existing);
            } else {
                log.info("Seeder Players ignoré : la table contient déjà " + existing + " joueur(s).");
            }
            return;
        }

        String[] firstNames = {"alex", "sam", "lina", "yanis", "ines", "tom", "maya", "lucas", "nina", "adam"};
        String[] lastNames  = {"martin", "dupont", "bernard", "moreau", "lambert", "faure", "henry", "rousseau", "blanc", "garnier"};
        Random rnd = new Random();

        // Permet de relancer le seeder sans collisions (emails/usernames uniques).
        String runId = UUID.randomUUID().toString().replace("-", "").substring(0, 2);

        int created = 0;
        for (int i = 1; i <= 500; i++) {
            String base = firstNames[rnd.nextInt(firstNames.length)] + "_" + lastNames[rnd.nextInt(lastNames.length)];
            // Username doit respecter ^[a-zA-Z0-9_]{3,20}$ (TP) :
            // on construit un identifiant court mais unique (20 max).
            String prefix = base.toLowerCase();
            if (prefix.length() > 14) prefix = prefix.substring(0, 14);
            String username = String.format("%s_%s%03d", prefix, runId, i); // 14 + 1 + 2 + 3 = 20

            String email = "player" + runId + "_" + i + "@esgi.fr";

            try {
                var createdPlayer = playerService.createPlayer(new CreatePlayerDTO(username, email));
                // Donner des XP variés pour un Top 3 réaliste.
                int xp = rnd.nextInt(25_000); // 0..24999
                playerService.setXp(createdPlayer.getId(), xp);
                created++;
            } catch (RuntimeException ex) {
                // ex: username/email déjà existant (si relancé) ou autre souci — on continue
                if (i % 100 == 0) {
                    log.warning("Erreur pendant seed (i=" + i + "): " + ex.getMessage());
                }
            }

            if (i % 100 == 0) {
                log.info("Progression seeder Players: " + i + "/500 (créés: " + created + ")");
            }
        }

        log.info("Seeder Players terminé. Créés: " + created + "/500");
    }

    private void backfillXpForAllPlayers(long existing) {
        Random rnd = new Random();
        int updated = 0;

        int page = 0;
        int size = 200;
        while (updated < existing) {
            var p = playerService.getAll(page, size);
            if (p.getContent().isEmpty()) break;

            for (var player : p.getContent()) {
                int xp = rnd.nextInt(25_000);
                try {
                    playerService.setXp(player.getId(), xp);
                    updated++;
                } catch (RuntimeException ex) {
                    // On continue : le backfill est best-effort.
                }
            }

            page++;
            if (updated % 200 == 0) {
                log.info("Backfill XP: " + updated + "/" + existing);
            }
        }

        log.info("Backfill XP terminé. MAJ: " + updated + "/" + existing);
    }
}
