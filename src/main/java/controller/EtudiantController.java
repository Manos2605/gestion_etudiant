package main.java.controller;

import main.java.model.Etudiant;
import main.java.service.EtudiantService;
import java.util.List;

public class EtudiantController {

    private EtudiantService service = new EtudiantService();

    public void ajouter(String matricule, String nom, String prenom, String classe) {
        service.ajouter(new Etudiant(matricule, nom, classe));
    }

    public void supprimer(Etudiant etudiant) throws Exception {
        service.supprimer(etudiant);
    }

    public void modifier(Etudiant etudiant, String matricule, String nom, String classe) {
        service.modifier(etudiant, matricule, nom, classe);
    }

    public List<Etudiant> getTous() {
        return service.getTous();
    }

    public Etudiant rechercher(String matricule) {
        return service.rechercherParMatricule(matricule);
    }
}
