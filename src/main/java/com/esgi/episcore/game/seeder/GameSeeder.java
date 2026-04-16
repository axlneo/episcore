package com.esgi.episcore.game.seeder;

import com.esgi.episcore.game.dto.CreateGameDTO;
import com.esgi.episcore.game.exception.GameAlreadyExistsException;
import com.esgi.episcore.game.model.Genre;
import com.esgi.episcore.game.service.GameService;
import com.esgi.episcore.util.AppLogger;

import java.util.Random;
import java.util.logging.Logger;

/**
 * Génère 50 jeux fictifs via le Service.
 */
public class GameSeeder {

    private static final Logger log = AppLogger.getLogger(GameSeeder.class);
    private static final int TARGET_GAMES = 50;

    private final GameService gameService;

    public GameSeeder(GameService gameService) {
        this.gameService = gameService;
    }

    public void seed() {
        log.info("Démarrage du seeder Games — cible : " + TARGET_GAMES + " jeux");

        String[] prefixes = {"Epi", "Legend of", "Turbo", "Call of", "World of", "Cyber", "Super", "Age of", "Dark", "League of"};
        String[] suffixes = {"Craft", "ESGI", "Race 3000", "Duty", "Warcraft", "Punk", "Mario", "Empires", "Souls", "Legends"};

        Genre[] genres = Genre.values();
        Random random = new Random();
        int insertedCount = 0;

        for (int i = 1; i <= TARGET_GAMES; i++) {
            // Combinaison aléatoire + index pour garantir l'unicité
            String randomTitle = prefixes[random.nextInt(prefixes.length)] + " " +
                    suffixes[random.nextInt(suffixes.length)] + " " + i;

            String randomGenre = genres[random.nextInt(genres.length)].name();
            int maxPlayers = random.nextInt(64) + 1; // Entre 1 et 64

            try {
                CreateGameDTO dto = new CreateGameDTO(randomTitle, randomGenre, maxPlayers);
                gameService.createGame(dto);
                insertedCount++;
            } catch (GameAlreadyExistsException e) {
                log.warning("Doublon ignoré lors du seeding : " + randomTitle);
            } catch (Exception e) {
                log.severe("Erreur lors de la création du jeu " + randomTitle + " : " + e.getMessage());
            }

            // Logger la progression tous les 10 jeux
            if (i % 10 == 0) {
                log.info("Progression Seeder Games : " + i + " / " + TARGET_GAMES + " traités...");
            }
        }

        log.info("Seeding terminé ! " + insertedCount + " jeux ont été insérés en base de données.");
    }
}