package ui;

import game.Board;

public class ConsoleUI {
    public static void printWelcome() {
        System.out.println("=============================");
        System.out.println(" Bienvenue dans Xou Dou Qi ! ");
        System.out.println(" Le jeu de la jungle chinois ");
        System.out.println("=============================");
    }

    public static void displayBoard(Board board) {
        System.out.println("\nPlateau de jeu :");
        System.out.println("   A B C D E F G");

        for (int row = 0; row < board.getRows(); row++) {
            System.out.print((row + 1) + " ");
            for (int col = 0; col < board.getCols(); col++) {
                System.out.print(" " + board.getCellSymbol(col, row));
            }
            System.out.println();
        }
    }

    public static void printWinner(String username) {
        System.out.println("\nFélicitations ! Le joueur " + username + " a gagné !");
    }

    public static void printDraw() {
        System.out.println("\nMatch nul. Aucun joueur n'a gagné.");
    }
}