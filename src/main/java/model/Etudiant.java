public class Etudiant {
    private int id;
    private String matricule;
    private String nom;
    private String prenom;
    private String filiere;
    private int niveau;

    public Etudiant(String matricule, String nom, String prenom, String filiere, int niveau){
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this. filiere = filiere;
        this.niveau = niveau;
    }
\\pour la manipulation des etudiants deja ds la base de donnee.
    public Etudiant(int id, String matricule, String nom, String prenom, String filiere, int niveau){
        this.id = id;
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this. filiere = filiere;
        this.niveau = niveau;
    }

    public int getId(){
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getMatricule() {
        return matricule;
    }
    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public String getFiliere() {
        return filiere;
    }
    public void setFiliere(String filiere) {
        this.filiere = filiere;
    }
    public int getNiveau() {
        return niveau;
    }
    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    @Override
    public String toString(){
        return "Etudiant" +
        "matricule= " + matricule +
        ", nom= " + nom +
        ", prenom= " + prenom +
        ", filiere= " + filiere +
        ", niveau= " + niveau;
    }
}