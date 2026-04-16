package com.esgi.episcore.util;

import java.io.IOException;
import java.util.logging.*;

/**
 * Fabrique de loggers configurés pour EpiScore.
 *
 * Niveaux attendus :
 *   INFO    → opérations normales (save, find, delete réussis)
 *   WARNING → données manquantes, entité non trouvée
 *   SEVERE  → exception SQL, erreur inattendue
 */
public final class AppLogger {

    private static final String LOG_FILE = "episcore.log";

    private AppLogger() {}

    /**
     * Retourne un Logger configuré avec handler console ET fichier.
     *
     * @param clazz la classe appelante (ex: PlayerDAOImpl.class)
     * @return logger prêt à l'emploi
     */
    public static Logger getLogger(Class<?> clazz) {
        Logger logger = Logger.getLogger(clazz.getName());
        logger.setUseParentHandlers(false);

        if (logger.getHandlers().length == 0) {
            // Console
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.ALL);
            consoleHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(consoleHandler);

            // Fichier (optionnel — bonus)
            // try {
            //     FileHandler fileHandler = new FileHandler(LOG_FILE, true);
            //     fileHandler.setFormatter(new SimpleFormatter());
            //     logger.addHandler(fileHandler);
            // } catch (IOException e) {
            //     logger.warning("Impossible de créer le FileHandler : " + e.getMessage());
            // }
        }

        logger.setLevel(Level.ALL);
        return logger;
    }
}
