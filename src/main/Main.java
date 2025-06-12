package main;

import db.MatchDAO;
import db.PlayerDAO;
import model.Player;
import game.Game;
import ui.ConsoleUI;
import ui.InputHandler;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PlayerDAO playerDAO = new PlayerDAO();
        MatchDAO matchDAO = new MatchDAO();

        playerDAO.createTable();
        matchDAO.createTable();

        ConsoleUI.printWelcome();

        // Player login/registration
        System.out.println("Connexion ou création du Joueur 1 :");
        Player player1 = InputHandler.loginOrRegister(scanner, playerDAO);

        System.out.println("Connexion ou création du Joueur 2 :");
        Player player2 = InputHandler.loginOrRegister(scanner, playerDAO);

        // Start game
        Game game = new Game(player1, player2);
        game.play();

        // Update scores and record match
        Player winner = game.getWinner();
        if (winner != null) {
            System.out.println("\nLe gagnant est : " + winner.getUsername());
            winner.setScore(winner.getScore() + 1);
            playerDAO.updateScore(winner);
            matchDAO.insertMatch(player1.getUsername(), player2.getUsername(), winner.getUsername());
        } else {
            System.out.println("\nMatch nul.");
            matchDAO.insertMatch(player1.getUsername(), player2.getUsername(), "NUL");
        }

        // Display history
        System.out.println("\nHistorique du Joueur 1 :");
        matchDAO.getMatchHistory(player1.getUsername()).forEach(System.out::println);

        System.out.println("\nHistorique du Joueur 2 :");
        matchDAO.getMatchHistory(player2.getUsername()).forEach(System.out::println);

        scanner.close();
    }
}