package main.java.view;

import main.java.dao.StudentDAO;
import main.java.model.Etudiant;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class StudentManagementFrame extends JFrame {

    // COMPOSANTS
    private JTextField txtMatricule, txtNom, txtClasse, txtRecherche;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAjouterModifier;

    private StudentDAO studentDAO = new StudentDAO();

    // COULEURS
    private final Color PRIMARY = new Color(41, 128, 185);
    private final Color SECONDARY = new Color(52, 73, 94);
    private final Color ACCENT = new Color(26, 188, 156);
    private final Color DELETE = new Color(231, 76, 60);
    private final Color BG = new Color(236, 240, 241);

    public StudentManagementFrame() {
        setTitle("Gestion des étudiants");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(BG);

        add(creerTitre(), BorderLayout.NORTH);
        add(creerContenu(), BorderLayout.CENTER);

        chargerEtudiants();
    }

    // TITRE
    private JPanel creerTitre() {
        JPanel panel = new JPanel();
        panel.setBackground(PRIMARY);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 20, 25, 20));

        JLabel titre = new JLabel("SYSTÈME DE GESTION DES ÉTUDIANTS");
        titre.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titre.setForeground(Color.WHITE);

        panel.add(titre);
        return panel;
    }

    // CONTENU CENTRAL
    private JPanel creerContenu() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(BG);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(creerFormulaire(), BorderLayout.NORTH);
        panel.add(creerTable(), BorderLayout.CENTER);

        return panel;
    }

    // FORMULAIRE
    private JPanel creerFormulaire() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY, 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);

        // Matricule
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(label("Matricule :", labelFont), gbc);
        gbc.gridx = 1;
        txtMatricule = champTexte();
        panel.add(txtMatricule, gbc);

        // Nom
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(label("Nom :", labelFont), gbc);
        gbc.gridx = 1;
        txtNom = champTexte();
        panel.add(txtNom, gbc);

        // Classe
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(label("Classe :", labelFont), gbc);
        gbc.gridx = 1;
        txtClasse = champTexte();
        panel.add(txtClasse, gbc);

        // Boutons
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        panelBtn.setBackground(Color.WHITE);

        btnAjouterModifier = bouton("Ajouter", ACCENT);
        btnAjouterModifier.addActionListener(e -> {
            if (btnAjouterModifier.getText().equals("Ajouter")) {
                ajouterEtudiant();
            } else {
                modifierEtudiant();
            }
        });

        JButton btnEffacer = bouton("Effacer", new Color(149, 165, 166));
        btnEffacer.addActionListener(e -> effacerFormulaire());

        panelBtn.add(btnAjouterModifier);
        panelBtn.add(btnEffacer);
        panel.add(panelBtn, gbc);

        return panel;
    }

    // TABLE + RECHERCHE
    private JPanel creerTable() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(SECONDARY, 2),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // Recherche
        JPanel panelRecherche = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelRecherche.setBackground(Color.WHITE);

        JLabel lblRecherche = new JLabel("Rechercher par Matricule :");
        lblRecherche.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panelRecherche.add(lblRecherche);

        txtRecherche = champTexte();
        txtRecherche.setPreferredSize(new Dimension(200, 35));
        panelRecherche.add(txtRecherche);

        JButton btnRechercher = bouton("Rechercher", new Color(52, 152, 219));
        btnRechercher.addActionListener(e -> rechercherEtudiant());

        JButton btnActualiser = bouton("Actualiser", new Color(149, 165, 166));
        btnActualiser.addActionListener(e -> chargerEtudiants());

        panelRecherche.add(btnRechercher);
        panelRecherche.add(btnActualiser);

        panel.add(panelRecherche, BorderLayout.NORTH);

        // Table
        String[] colonnes = {"Matricule", "Nom", "Classe"};
        tableModel = new DefaultTableModel(colonnes, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(35);
        table.setSelectionBackground(new Color(52, 152, 219));
        table.setSelectionForeground(Color.BLACK);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(SECONDARY);
        header.setForeground(Color.BLACK);
        header.setPreferredSize(new Dimension(header.getWidth(), 40));

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                txtMatricule.setText(tableModel.getValueAt(row, 0).toString());
                txtNom.setText(tableModel.getValueAt(row, 1).toString());
                txtClasse.setText(tableModel.getValueAt(row, 2).toString());
                txtMatricule.setEditable(false);
                btnAjouterModifier.setText("Modifier");
            }
        });

        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        // Supprimer
        JButton btnSupprimer = bouton("Supprimer la sélection", DELETE);
        btnSupprimer.addActionListener(e -> supprimerEtudiant());

        JPanel bas = new JPanel();
        bas.setBackground(Color.WHITE);
        bas.add(btnSupprimer);

        panel.add(bas, BorderLayout.SOUTH);

        return panel;
    }

    // LOGIQUE
    private void ajouterEtudiant() {
        Etudiant e = lireFormulaire();
        if (e == null) return;

        if (studentDAO.ajouterEtudiant(e)) {
            chargerEtudiants();
            effacerFormulaire();
        } else {
            JOptionPane.showMessageDialog(this, "Matricule déjà existant");
        }
    }

    private void modifierEtudiant() {
        Etudiant e = lireFormulaire();
        if (e == null) return;

        if (studentDAO.modifierEtudiant(e)) {
            chargerEtudiants();
            effacerFormulaire();
            JOptionPane.showMessageDialog(this, "Étudiant modifié avec succès");
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de la modification");
        }
    }

    private void supprimerEtudiant() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        String matricule = tableModel.getValueAt(row, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(
            this, "Voulez-vous vraiment supprimer cet étudiant ?", "Confirmation",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            if (studentDAO.supprimerEtudiant(matricule)) {
                chargerEtudiants();
                effacerFormulaire();
                JOptionPane.showMessageDialog(this, "Étudiant supprimé avec succès !");
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la suppression !");
            }
        }

    }

    private void rechercherEtudiant() {
        String matricule = txtRecherche.getText().trim();
        if (matricule.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Entrez un matricule !");
            return;
        }

        Etudiant e = studentDAO.rechercherParMatricule(matricule);
        tableModel.setRowCount(0);

        if (e != null) {
            tableModel.addRow(new Object[]{
                    e.getMatricule(),
                    e.getNom(),
                    e.getClasse()
            });
        } else {
            JOptionPane.showMessageDialog(this, "Aucun étudiant trouvé");
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
        txtRecherche.setText("");
    }

    private Etudiant lireFormulaire() {
        if (txtMatricule.getText().isEmpty()
                || txtNom.getText().isEmpty()
                || txtClasse.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tous les champs sont obligatoires");
            return null;
        }

        return new Etudiant(
                txtMatricule.getText().trim(),
                txtNom.getText().trim(),
                txtClasse.getText().trim()
        );
    }

    private void effacerFormulaire() {
        txtMatricule.setText("");
        txtNom.setText("");
        txtClasse.setText("");
        txtMatricule.setEditable(true);
        btnAjouterModifier.setText("Ajouter");
    }

    // OUTILS UI
    private JLabel label(String txt, Font f) {
        JLabel l = new JLabel(txt);
        l.setFont(f);
        return l;
    }

    private JTextField champTexte() {
        JTextField t = new JTextField(25);
        t.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        t.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        return t;
    }

    private JButton bouton(String txt, Color c) {
        JButton b = new JButton(txt);
        b.setBackground(c);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setPreferredSize(new Dimension(180, 40));
        return b;
    }
}
