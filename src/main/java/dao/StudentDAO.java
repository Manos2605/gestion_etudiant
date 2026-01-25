package main.java.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import main.java.model.Etudiant;
import main.java.util.DatabaseConnection;

public class StudentDAO {
    
    // Ajouter un étudiant
    public boolean ajouterEtudiant(Etudiant student) {
        String sql = "INSERT INTO etudiants (matricule, nom, classe) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, student.getMatricule());
            pstmt.setString(2, student.getNom());
            pstmt.setString(3, student.getClasse());
            
            int result = pstmt.executeUpdate();
            return result > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout: " + e.getMessage());
            return false;
        }
    }
    
    // Récupérer tous les étudiants
    public List<Etudiant> obtenirTousLesEtudiants() {
        List<Etudiant> students = new ArrayList<>();
        String sql = "SELECT * FROM etudiants ORDER BY matricule";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Etudiant student = new Etudiant(
                    rs.getString("matricule"),
                    rs.getString("nom"),
                    rs.getString("classe")
                );
                students.add(student);
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération: " + e.getMessage());
        }
        
        return students;
    }
    
    // Rechercher un étudiant par matricule
    public Etudiant rechercherParMatricule(String matricule) {
        String sql = "SELECT * FROM etudiants WHERE matricule = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, matricule);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Etudiant(
                    rs.getString("matricule"),
                    rs.getString("nom"),
                    rs.getString("classe")
                );
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche: " + e.getMessage());
        }
        
        return null;
    }
    
    // Supprimer un étudiant
    public boolean supprimerEtudiant(String matricule) {
        String sql = "DELETE FROM etudiants WHERE matricule = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, matricule);
            int result = pstmt.executeUpdate();
            return result > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression: " + e.getMessage());
            return false;
        }
    }

    // Modifier un étudiant
    public boolean modifierEtudiant(Etudiant student) {
        String sql = "UPDATE etudiants SET nom = ?, classe = ? WHERE matricule = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, student.getNom());
            pstmt.setString(2, student.getClasse());
            pstmt.setString(3, student.getMatricule());
            
            int result = pstmt.executeUpdate();
            return result > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification: " + e.getMessage());
            return false;
        }
    }
}