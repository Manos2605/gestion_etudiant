package main.java.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DatabaseConnection {
    //MODIFIEZ LE MOT DE PASSE SI NÉCESSAIRE
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "gestion_etudiants";
    private static final String USER = "root";
    private static final String PASSWORD = "";  // Mettez votre mot de passe MySQL ici
    
    public static boolean testerConnexion() {
        System.out.println("=== TEST DE CONNEXION MYSQL ===");
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✓ Driver MySQL chargé");
            
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✓ Connexion réussie !");
            System.out.println("  Serveur: localhost:3306");
            System.out.println("  Utilisateur: " + USER);
            
            conn.close();
            return true;
            
        } catch (ClassNotFoundException e) {
            System.err.println("✗ Driver MySQL non trouvé !");
            return false;
        } catch (SQLException e) {
            System.err.println("✗ Erreur de connexion !");
            System.err.println("  Détails: " + e.getMessage());
            return false;
        }
    }
    
    public static boolean creerBaseDeDonnees() {
        System.out.println("\n=== CRÉATION DE LA BASE DE DONNÉES ===");
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
            System.out.println("✓ Base de données '" + DB_NAME + "' créée");
            return true;
            
        } catch (SQLException e) {
            System.err.println("✗ Erreur: " + e.getMessage());
            return false;
        }
}


public static boolean creerTable() {
    System.out.println("\n=== CRÉATION DE LA TABLE ===");
    
    try (Connection conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
         Statement stmt = conn.createStatement()) {
        
        String sql = "CREATE TABLE IF NOT EXISTS etudiants (" +
                    "matricule VARCHAR(20) PRIMARY KEY," +
                    "nom VARCHAR(100) NOT NULL," +
                    "classe VARCHAR(50) NOT NULL)";
        
        stmt.executeUpdate(sql);
        System.out.println("✓ Table 'etudiants' créée");
        return true;
        
    } catch (SQLException e) {
        System.err.println("✗ Erreur: " + e.getMessage());
        return false;
    }
}

public static Connection getConnection() throws SQLException {
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
    } catch (ClassNotFoundException e) {
        throw new SQLException("Driver MySQL non trouvé", e);
    }
}


}