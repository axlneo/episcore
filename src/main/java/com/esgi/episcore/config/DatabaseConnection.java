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

    // Supabase exige TLS : sslmode=require
    private static final String URL      = "jdbc:postgresql://aws-1-eu-central-1.pooler.supabase.com:5432/postgres?sslmode=require";
    // Avec le pooler, le username est souvent suffixé par le project ref
    private static final String USER     = "postgres.riuaahhwyxpacpeyzmzx";
    private static final String PASSWORD = "Soltane010203@";

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
