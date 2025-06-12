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
        boolean gameEnded = false;

        while (!gameEnded) {
            ConsoleUI.displayBoard(board);
            System.out.println("\n" + currentPlayer.getUsername() + ", à vous de jouer !");
            System.out.print("Entrez le mouvement (ex: A2 A3) : ");
            String moveInput = scanner.nextLine();

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
        }

        ConsoleUI.displayBoard(board);
        if (winner != null) {
            ConsoleUI.printWinner(winner.getUsername());
        } else {
            ConsoleUI.printDraw();
        }
    }

    public Player getWinner() {
        return winner;
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }
}