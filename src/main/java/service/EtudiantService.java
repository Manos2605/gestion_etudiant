package main.java.service;
import java.util.ArrayList;
import main.java.model.Etudiant;

public class EtudiantService {
    
    ArrayList<Etudiant> etudiants = new ArrayList<>();

    public void ajouter(Etudiant etudiant) {
        etudiants.add(etudiant);
    }

    public void supprimer(Etudiant etudiant) throws Exception {
        if (!etudiants.remove(etudiant)) {
            throw new Exception("Étudiant non trouvé");
        }
    }

    public void modifier(Etudiant etudiant, String matricule, String nom, String prenom, String classe) {
        etudiant.setMatricule(matricule);
        etudiant.setNom(nom);
        etudiant.setPrenom(prenom);
        etudiant.setClasse(classe);
    }

    public ArrayList<Etudiant> getTous() {
        return etudiants;
    }
    public Etudiant rechercherParMatricule(String matricule) {
        for (Etudiant etudiant : etudiants) {
            if (etudiant.getMatricule().equals(matricule)) {
                return etudiant;
            }
        }
        return null;
    }
}
