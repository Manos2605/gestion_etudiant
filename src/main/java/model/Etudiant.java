package main.java.model;

public class Etudiant {
    private String matricule;
    private String nom;
    private String classe;

    public Etudiant(String matricule, String nom, String classe) {
        this.matricule = matricule;
        this.nom = nom;
        this.classe = classe;
    }

    public String getMatricule() {
        return matricule;
    }

    public String getNom() {
        return nom;
    }

    public String getClasse() {
        return classe;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    
}
