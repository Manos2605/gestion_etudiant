package ui;
import com.formdev.flatlaf.FlatLightLaf; // Import du thème moderne
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainFrame extends JFrame {
    // Éléments du formulaire (Accessibles par les méthodes plus tard)
    private JTextField txtMatricule, txtNom, txtClasse;
    private JTable table;
    private DefaultTableModel tableModel;

    public MainFrame() {
        // 1. Configuration de base de la fenêtre
        setTitle("Système de Gestion des Étudiants 2025");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centre la fenêtre

        // 2. Organisation principale (BorderLayout)
        setLayout(new BorderLayout(15, 15));
        JPanel contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(contentPane);

        // --- ZONE DE GAUCHE : FORMULAIRE D'AJOUT ---
        JPanel formPanel = new JPanel(new GridLayout(10, 1, 5, 5));
        formPanel.setPreferredSize(new Dimension(250, 0));
        formPanel.setBorder(BorderFactory.createTitledBorder("Informations Étudiant"));

        txtMatricule = new JTextField();
        txtNom = new JTextField();
        txtClasse = new JTextField();
        JButton btnAdd = new JButton("Ajouter l'étudiant");
        btnAdd.setBackground(new Color(41, 128, 185)); // Bleu moderne
        btnAdd.setForeground(Color.WHITE);

        formPanel.add(new JLabel("Matricule :"));
        formPanel.add(txtMatricule);
        formPanel.add(new JLabel("Nom Complet :"));
        formPanel.add(txtNom);
        formPanel.add(new JLabel("Classe :"));
        formPanel.add(txtClasse);
        formPanel.add(new JLabel("")); // Espaceur
        formPanel.add(btnAdd);

        // --- ZONE CENTRALE : TABLEAU ---
        String[] colonnes = {"Matricule", "Nom", "Classe"};
        tableModel = new DefaultTableModel(colonnes, 0);
        table = new JTable(tableModel);
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);

        // --- ZONE DU HAUT : RECHERCHE ---
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JTextField txtSearch = new JTextField(20);
        txtSearch.putClientProperty("JTextField.placeholderText", "Rechercher par matricule...");
        searchPanel.add(new JLabel("🔍"));
        searchPanel.add(txtSearch);

        // 3. Assemblage final
        contentPane.add(searchPanel, BorderLayout.NORTH);
        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.add(formPanel, BorderLayout.WEST);

        // --- RELIER L'API (Logique) ---
        btnAdd.addActionListener(e -> actionAjouter());
    }

    // Cette méthode est "l'entrée" vers ton API/Base de données
    private void actionAjouter() {
        String m = txtMatricule.getText();
        String n = txtNom.getText();
        String c = txtClasse.getText();

        if(m.isEmpty() || n.isEmpty() || c.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Erreur : Tous les champs sont requis !", "Validation", JOptionPane.ERROR_MESSAGE);
        } else {
            // Ici, tu appelleras ta fonction JDBC plus tard
            // Pour l'instant, on l'ajoute juste visuellement au tableau
            tableModel.addRow(new Object[]{m, n, c});
            
            // On vide le formulaire
            txtMatricule.setText(""); txtNom.setText(""); txtClasse.setText("");
        }
    }

    public static void main(String[] args) {
        // Appliquer le style moderne avant d'afficher
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) { e.printStackTrace(); }

        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}