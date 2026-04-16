package com.esgi.episcore.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Fournit la connexion JDBC vers Supabase (PostgreSQL).
 * Classe utilitaire — ne pas instancier.
 *
 * ⚠️  Remplacer URL, USER et PASSWORD par les vraies valeurs.
 */
public final class DatabaseConnection {

    private static final String URL      = "jdbc:postgresql://[HOST]:5432/postgres";
    private static final String USER     = "postgres";
    private static final String PASSWORD = "[À_REMPLIR]";

    private DatabaseConnection() {}

    /**
     * Retourne une nouvelle connexion JDBC.
     * Toujours utiliser dans un try-with-resources pour la fermer automatiquement.
     *
     * @return une connexion ouverte
     * @throws SQLException si la connexion échoue
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
