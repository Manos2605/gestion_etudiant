# Gestion Étudiants Java

## Description
Ce projet est une application Java pour la gestion des étudiants, permettant d'ajouter, modifier, supprimer et rechercher des étudiants dans une base de données.


## Fonctionnalités
- Ajouter un étudiant
- Supprimer un étudiant
- Modifier les informations d'un étudiant
- Rechercher un étudiant par matricule

## Instructions pour lancer le projet
1. Clonez le repository :
   ```bash
   git clone <url-du-repository>
   ```
2. Importez le projet dans votre IDE préféré (VS Code, Eclipse, IntelliJ, etc.).
3. Assurez-vous que la base de données est configurée correctement.
4. Exécutez la classe `Main` pour lancer l'application.

## instructions pour executer le code (partie ui)
1. Compiler le code en specifiant la bibliotheque a utiliser :
   ```bash
   javac -cp "lib/flatlaf-3.7.jar" src/main/java/ui/MainFrame.java
   ```

2. lancer le code 
- sur linux
   ```bash
   java -cp "lib/flatlaf-3.7.jar:src/main/java" ui.MainFrame
   ```
- Sur Windows 
   ```bash
   java -cp "lib/flatlaf-3.7.jar;src/main/java" ui.MainFrame
   ```
