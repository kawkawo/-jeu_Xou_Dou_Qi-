package game;
import model.Piece;
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
        // Reset scores at the start of a new game
        player1.setScore(0);
        player2.setScore(0);
    }

    public void play() {
        ConsoleUI.printWelcome();
        boolean gameEnded = false;

        while (!gameEnded) {
            ConsoleUI.displayBoard(board);
            printScores(); // Display current scores

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
                    // Update scores after successful move
                    updateScores();

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
        printFinalScores(); //  Display final scores
        if (winner != null) {
            ConsoleUI.printWinner(winner.getUsername());
        } else {
            ConsoleUI.printDraw();
        }
        scanner.close();
    }



    private void printScores() {
        System.out.println("\n=== SCORES ===");
        System.out.println(player1.getUsername() + " (" + player1.getCode() + "): " + player1.getScore());
        System.out.println(player2.getUsername() + " (" + player2.getCode() + "): " + player2.getScore());
    }

    private void printFinalScores() {
        System.out.println("\n=== FINAL SCORES ===");
        System.out.println(player1.getUsername() + ": " + player1.getScore() + " points");
        System.out.println(player2.getUsername() + ": " + player2.getScore() + " points");
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

    private void updateScores() {
        // Check if current player reached the opponent's sanctuary
        int opponentSanctuaryY = currentPlayer.getCode().equals("P1") ? 0 : board.getRows() - 1;
        Piece sanctuaryPiece = board.getPiece(3, opponentSanctuaryY);

        if (sanctuaryPiece != null && sanctuaryPiece.getProprietaire().equals(currentPlayer)) {
            currentPlayer.setScore(currentPlayer.getScore() + 50); 
            System.out.println("[BONUS] " + currentPlayer.getUsername() + " +50 for reaching the enemy sanctuary!");
        }
    }
}
