package main.java.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import main.java.dao.StudentDAO;
import main.java.model.Etudiant;

import java.awt.*;
import java.util.List;

public class StudentManagementFrame extends JFrame {

    private JTextField txtMatricule;
    private JTextField txtNom;
    private JTextField txtClasse;

    private JTable table;
    private DefaultTableModel tableModel;

    private StudentDAO studentDAO = new StudentDAO();

    // Bouton principal (Ajouter / Modifier)
    private JButton btnAjouterModifier;

    public StudentManagementFrame() {
        setTitle("Gestion des étudiants");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout(10, 10));

        // ---- FORMULAIRE ----
        JPanel panelForm = new JPanel(new GridLayout(4, 2, 10, 10));

        panelForm.add(new JLabel("Matricule :"));
        txtMatricule = new JTextField();
        panelForm.add(txtMatricule);

        panelForm.add(new JLabel("Nom :"));
        txtNom = new JTextField();
        panelForm.add(txtNom);

        panelForm.add(new JLabel("Classe :"));
        txtClasse = new JTextField();
        panelForm.add(txtClasse);

        // Bouton Ajouter / Modifier
        btnAjouterModifier = new JButton("Ajouter");
        btnAjouterModifier.addActionListener(e -> {
            if (btnAjouterModifier.getText().equals("Ajouter")) {
                ajouterEtudiant();
            } else {
                modifierEtudiant();
            }
        });
        panelForm.add(btnAjouterModifier);

        // Bouton Effacer
        JButton btnEffacer = new JButton("Effacer");
        btnEffacer.addActionListener(e -> {
            effacerFormulaire();
        });
        panelForm.add(btnEffacer);

        add(panelForm, BorderLayout.NORTH);

        // ---- TABLEAU ----
        String[] colonnes = {"Matricule", "Nom", "Classe"};
        tableModel = new DefaultTableModel(colonnes, 0);
        table = new JTable(tableModel);

        // Sélection d'une ligne → remplir le formulaire et changer le bouton
        table.getSelectionModel().addListSelectionListener(e -> {
            int ligne = table.getSelectedRow();
            if (ligne != -1) {
                txtMatricule.setText(tableModel.getValueAt(ligne, 0).toString());
                txtNom.setText(tableModel.getValueAt(ligne, 1).toString());
                txtClasse.setText(tableModel.getValueAt(ligne, 2).toString());

                txtMatricule.setEditable(false);
                btnAjouterModifier.setText("Modifier"); // Changement dynamique
            }
        });

        add(new JScrollPane(table), BorderLayout.CENTER);

        // ---- BOUTON SUPPRIMER ----
        JButton btnSupprimer = new JButton("Supprimer");
        btnSupprimer.addActionListener(e -> supprimerEtudiant());
        add(btnSupprimer, BorderLayout.SOUTH);

        // Charger les étudiants au démarrage
        chargerEtudiants();
    }

    private void ajouterEtudiant() {
        String matricule = txtMatricule.getText().trim();
        String nom = txtNom.getText().trim();
        String classe = txtClasse.getText().trim();

        if (matricule.isEmpty() || nom.isEmpty() || classe.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tous les champs sont obligatoires");
            return;
        }

        Etudiant e = new Etudiant(matricule, nom, classe);
        if (studentDAO.ajouterEtudiant(e)) {
            JOptionPane.showMessageDialog(this, "Étudiant ajouté avec succès");
            chargerEtudiants();
            effacerFormulaire();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur : matricule déjà existant");
        }
    }

    private void modifierEtudiant() {
        String matricule = txtMatricule.getText().trim();
        String nom = txtNom.getText().trim();
        String classe = txtClasse.getText().trim();

        if (matricule.isEmpty() || nom.isEmpty() || classe.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tous les champs sont obligatoires");
            return;
        }

        Etudiant e = new Etudiant(matricule, nom, classe);
        if (studentDAO.modifierEtudiant(e)) {
            JOptionPane.showMessageDialog(this, "Étudiant modifié avec succès");
            chargerEtudiants();
            effacerFormulaire();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de la modification");
        }
    }

    private void supprimerEtudiant() {
        int ligne = table.getSelectedRow();
        if (ligne == -1) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un étudiant à supprimer");
            return;
        }

        String matricule = tableModel.getValueAt(ligne, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this,
                "Voulez-vous vraiment supprimer cet étudiant ?", "Confirmation",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (studentDAO.supprimerEtudiant(matricule)) {
                JOptionPane.showMessageDialog(this, "Étudiant supprimé avec succès");
                chargerEtudiants();
                effacerFormulaire();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la suppression");
            }
        }
    }

    private void chargerEtudiants() {
        tableModel.setRowCount(0);

        List<Etudiant> liste = studentDAO.obtenirTousLesEtudiants();
        for (Etudiant e : liste) {
            tableModel.addRow(new Object[]{
                    e.getMatricule(),
                    e.getNom(),
                    e.getClasse()
            });
        }
    }

    private void effacerFormulaire() {
        txtMatricule.setText("");
        txtNom.setText("");
        txtClasse.setText("");
        txtMatricule.setEditable(true);
        btnAjouterModifier.setText("Ajouter"); // Revenir à Ajouter
    }
}
