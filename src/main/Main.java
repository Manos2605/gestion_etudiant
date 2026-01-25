package main;

import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
    
        System.out.println("Gestion des étudiants démarrée.");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        javax.swing.SwingUtilities.invokeLater(() -> {
            new main.java.view.StudentManagementFrame().setVisible(true);
        });
    }
}
