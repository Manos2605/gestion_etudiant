// EtudiantDAO.java
// This file will handle database operations for the Etudiant entity.
import model.Etudiant;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class EtudiantDAO {

    // connexion à la base de données
    public Connection getConnection() {
        Connection conn = null;
        try {
            // URL de connexion : jdbc:mysql://<hôte>:<port>/<nom_base>
            String url = "jdbc:mysql://localhost:3306/gestion_etudiants_db"; 
            String user = "root";     
            String password = "";      
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connexion réussie !");
        } catch (SQLException e) {
            System.out.println("Erreur de connexion : " + e.getMessage());
        }
        return conn;
    }

    public boolean ajouterEtudiant(Etudiant e) {
    boolean succes = false;
    String sql = "INSERT INTO etudiant (matricule, nom, prenom, filiere, niveau) VALUES (?, ?, ?, ?, ?)";

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, e.getMatricule());
        stmt.setString(2, e.getNom());
        stmt.setString(3, e.getPrenom());
        stmt.setString(4, e.getFiliere());
        stmt.setInt(5, e.getNiveau());

        int lignes = stmt.executeUpdate();  
        if (lignes > 0) {
            succes = true;
            System.out.println("Étudiant ajouté avec succès !");
        }
    } catch (SQLException ex) {
        System.out.println("Erreur lors de l'ajout : " + ex.getMessage());
    }
    return succes;
    }

    public boolean supprimerEtudiant(String matricule){
        boolean succes = false;
        String sql = "DELETE FROM etudiant WHERE matricule = ?";
        try(Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setString(1, matricule);
                 int lignes = stmt.executeUpdate();
                 if (lignes > 0){
                   succes = true;
                   System.out.println("Etudiant supprimer avec succes !"); 
                 }else {
                    System.out.println("Aucun etudiant trouver avec ce matricule !");
                 }

            }catch (SQLException ex) {
                System.out.println("Erreur lors de la suppression : " + ex.getMessage());
            }
            return succes;
    }

}
