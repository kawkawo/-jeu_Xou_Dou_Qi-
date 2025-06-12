package ui;

import model.Player;
import db.PlayerDAO;
import java.util.Scanner;

public class InputHandler {
    public static Player loginOrRegister(Scanner scanner, PlayerDAO playerDAO) {
        while (true) {
            System.out.print("Entrez le nom d'utilisateur : ");
            String username = scanner.nextLine().trim();

            System.out.print("Entrez le mot de passe : ");
            String password = scanner.nextLine().trim();

            Player existingPlayer = playerDAO.getByUsername(username);

            if (existingPlayer != null) {
                if (existingPlayer.getPassword().equals(password)) {
                    System.out.println("Connexion réussie. Bienvenue " + username + " !");
                    return existingPlayer;
                } else {
                    System.out.println("Mot de passe incorrect. Réessayez.");
                }
            } else {
                Player newPlayer = new Player(username, password);
                playerDAO.insert(newPlayer);
                System.out.println("Compte créé avec succès. Bienvenue " + username + " !");
                return newPlayer;
            }
        }
    }
}