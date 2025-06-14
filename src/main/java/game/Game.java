package game;

import model.Player;
import ui.ConsoleUI;
import java.util.Scanner;

public class Game {
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private Player winner;
    private Board board;
    private Scanner scanner;

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        this.board = new Board(player1, player2);
        this.scanner = new Scanner(System.in);
    }

    public void play() {
        ConsoleUI.printWelcome();
        boolean gameEnded = false;

        while (!gameEnded) {
            ConsoleUI.displayBoard(board);
            System.out.println("\n" + currentPlayer.getUsername() + " (" + currentPlayer.getCode() + "), à vous de jouer !");
            System.out.print("Entrez le mouvement (ex: A2 A3) : ");
            String moveInput = scanner.nextLine().trim().toUpperCase();

            if (moveInput.equals("QUIT")) {
                gameEnded = true;
                System.out.println("Partie abandonnée.");
                continue;
            }

            if (isValidMoveFormat(moveInput)) {
                if (board.processMove(moveInput, currentPlayer)) {
                    if (board.checkVictory(currentPlayer)) {
                        winner = currentPlayer;
                        gameEnded = true;
                    } else {
                        switchPlayer();
                    }
                } else {
                    System.out.println("Mouvement invalide. Réessayez.");
                }
            } else {
                System.out.println("Format invalide. Utilisez 'A2 A3' ou tapez 'QUIT' pour quitter.");
            }
        }

        ConsoleUI.displayBoard(board);
        if (winner != null) {
            ConsoleUI.printWinner(winner.getUsername());
        } else {
            ConsoleUI.printDraw();
        }
        scanner.close();
    }

    private boolean isValidMoveFormat(String input) {
        return input.matches("^[A-I][1-9] [A-I][1-9]$");
    }

    public Player getWinner() {
        return winner;
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }
}