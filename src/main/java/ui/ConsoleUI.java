package ui;

import game.Board;

public class ConsoleUI {
    // Couleurs ANSI révisées
    private static final String RESET = "\u001B[0m";
    private static final String WATER_COLOR = "\u001B[36m";  // Cyan vif
    private static final String TRAP_COLOR = "\u001B[31m";   // Rouge
    private static final String DEN_COLOR = "\u001B[33;1m";  // Jaune vif
    private static final String PLAYER1_COLOR = "\u001B[32m"; // Vert
    private static final String PLAYER2_COLOR = "\u001B[35m"; // Magenta
    private static final String GRID_COLOR = "\u001B[37m";   // Blanc

    public static void printWelcome() {
        System.out.println("\n" + GRID_COLOR + "╔══════════════════════════════╗");
        System.out.println("║🎋  XOU DOU QI - JUNGLE  🎋   ║");
        System.out.println("╚══════════════════════════════╝" + RESET);
        printLegend();
    }

    public static void displayBoard(Board board) {
        System.out.print("\n     ");
        for (char c = 'A'; c < 'A' + board.getCols(); c++) {
            System.out.print("  " + c + "   ");
        }
        System.out.println();

        printHorizontalBorder(board, "╔", "╦", "╗");

        for (int row = 0; row < board.getRows(); row++) {
            System.out.printf("%2d ║", row + 1);

            for (int col = 0; col < board.getCols(); col++) {
                String cell = formatCell(board, col, row);
                System.out.print(cell + "║");
            }
            System.out.println();

            if (row < board.getRows() - 1) {
                printHorizontalBorder(board, "╠", "╬", "╣");
            }
        }
        printHorizontalBorder(board, "╚", "╩", "╝");
    }

    private static void printHorizontalBorder(Board board, String left, String mid, String right) {
        System.out.print("   " + left);
        for (int i = 0; i < board.getCols(); i++) {
            System.out.print("══════");
            if (i < board.getCols() - 1) System.out.print(mid);
        }
        System.out.println(right);
    }

    private static String formatCell(Board board, int col, int row) {
        String symbol = board.getCellSymbol(col, row);
        String content;

        if (symbol == null || symbol.equals(".")) {
            if (board.estDansLEau(col, row)) {
                content = WATER_COLOR + " ≈≈≈ " + RESET;
            } else if (board.estUnSanctuaire(col, row, null)) {
                content = DEN_COLOR + " ⌂⌂⌂ " + RESET;
            } else if (board.estUnPiege(col, row, null)) {
                content = TRAP_COLOR + " ☠☠☠ " + RESET;
            } else {
                content = "      ";
            }
        } else {
            String playerColor = symbol.endsWith("1") ? PLAYER1_COLOR : PLAYER2_COLOR;
            String animal = getAnimalEmoji(symbol.substring(0, symbol.length()-1));
            content = playerColor + " " + animal + symbol.charAt(symbol.length()-1) + " " + RESET;
        }

        return String.format("%-5s", content);
    }

    private static String getAnimalEmoji(String code) {
        switch (code) {
            case "L": return "🦁";
            case "T": return "🐅";
            case "E": return "🐘";
            case "P": return "🐆";
            case "D": return "🐕";
            case "LO": return "🐺";
            case "C": return "🐈";
            case "R": return "🐀";
            default: return " ";
        }
    }

    public static void printLegend() {
        System.out.println("\n" + GRID_COLOR + "╔═══════════════════════════════════════════════╗");
        System.out.printf("║ %-54s ║\n", WATER_COLOR + "≈≈≈ - Rivière" + RESET);
        System.out.printf("║ %-56s ║\n", DEN_COLOR + "⌂⌂⌂ - Sanctuaire" + RESET);
        System.out.printf("║ %-52s ║\n", TRAP_COLOR + "☠☠☠ - Piège " + RESET);
        System.out.printf("║ %-54s ║\n", PLAYER1_COLOR + "Animal1 - Joueur 1" + RESET);
        System.out.printf("║ %-54s ║\n", PLAYER2_COLOR + "Animal2 - Joueur 2" + RESET);
        System.out.printf("║ %-45s ║\n", "");
        System.out.printf("║ %-45s ║\n", "🦁:Lion    🐅:Tigre   🐘:Éléphant ");
        System.out.printf("║ %-45s ║\n", "🐆:Panthère 🐕:Chien   🐺:Loup ");
        System.out.printf("║ %-45s ║\n", "🐈:Chat    🐀:Rat ");
        System.out.println("╚═══════════════════════════════════════════════╝" + RESET);
    }

    public static void printWinner(String username) {
        System.out.println("\n" + DEN_COLOR + "╔══════════════════════════════╗");
        System.out.println("║   🏆 " + username + " a gagné ! 🏆   ║");
        System.out.println("╚══════════════════════════════╝" + RESET);
    }

    public static void printDraw() {
        System.out.println("\n" + GRID_COLOR + "╔══════════════════════════════╗");
        System.out.println("║        🤝 Match nul 🤝        ║");
        System.out.println("╚══════════════════════════════╝" + RESET);
    }
}
