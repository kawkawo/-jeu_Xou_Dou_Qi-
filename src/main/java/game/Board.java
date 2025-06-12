package game;

import model.*;
import model.pieces.*;

public class Board {
    private Piece[][] cases = new Piece[7][9];
    private Plateau plateau = new Plateau();

    public Board(Player joueur1, Player joueur2) {
        initialiserPieces(joueur1, joueur2);
    }

    private void initialiserPieces(Player j1, Player j2) {
        // Joueur 1 (bottom)
        setPiece(0, 2, new Lion(0, 2, j1));
        setPiece(6, 2, new Tiger(6, 2, j1));
        setPiece(0, 1, new Rat(0, 1, j1));
        setPiece(1, 1, new Chat(1, 1, j1));
        setPiece(5, 1, new Chien(5, 1, j1));
        setPiece(2, 2, new Loup(2, 2, j1));
        setPiece(4, 2, new Panthere(4, 2, j1));
        setPiece(6, 0, new Elephant(6, 0, j1));

        // Joueur 2 (top)
        setPiece(0, 6, new Elephant(0, 6, j2));
        setPiece(2, 6, new Panthere(2, 6, j2));
        setPiece(4, 6, new Loup(4, 6, j2));
        setPiece(6, 6, new Chien(6, 6, j2));
        setPiece(1, 7, new Chat(1, 7, j2));
        setPiece(5, 7, new Rat(5, 7, j2));
        setPiece(0, 8, new Tiger(0, 8, j2));
        setPiece(6, 8, new Lion(6, 8, j2));
    }

    public Piece getPiece(int x, int y) {
        return cases[x][y];
    }

    public void setPiece(int x, int y, Piece piece) {
        cases[x][y] = piece;
        if (piece != null) {
            piece.setX(x);
            piece.setY(y);
            piece.setDansLeau(plateau.estDansLEau(x, y));
        }
    }

    public boolean processMove(String moveInput, Player player) {
        try {
            String[] parts = moveInput.trim().split(" ");
            if (parts.length != 2) return false;

            int[] from = parsePosition(parts[0]);
            int[] to = parsePosition(parts[1]);

            int x1 = from[0], y1 = from[1];
            int x2 = to[0], y2 = to[1];

            // Validate positions
            if (x1 < 0 || x1 >= 7 || y1 < 0 || y1 >= 9 ||
                    x2 < 0 || x2 >= 7 || y2 < 0 || y2 >= 9) {
                return false;
            }

            Piece piece = getPiece(x1, y1);
            if (piece == null || !piece.getProprietaire().equals(player)) return false;

            // Check if moving to own sanctuary
            if (plateau.estUnSanctuaire(x2, y2, player)) return false;

            if (!piece.peutSeDeplacerVers(x2, y2, plateau)) return false;

            Piece cible = getPiece(x2, y2);
            if (cible != null) {
                if (cible.getProprietaire().equals(player)) return false;
                if (!piece.peutCapturer(cible)) return false;
            }

            // Execute the move
            setPiece(x2, y2, piece);
            setPiece(x1, y1, null);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkVictory(Player player) {
        // Check if player has reached opponent's sanctuary
        int sanctuaryY = player.getUsername().equals("Joueur1") ? 8 : 0;
        Piece sanctuaryPiece = getPiece(3, sanctuaryY);
        return sanctuaryPiece != null && sanctuaryPiece.getProprietaire().equals(player);
    }

    private int[] parsePosition(String pos) {
        int col = Character.toUpperCase(pos.charAt(0)) - 'A';
        int row = Integer.parseInt(pos.substring(1)) - 1;
        return new int[]{col, row};
    }

    public String getCellSymbol(int x, int y) {
        Piece p = getPiece(x, y);
        if (p == null) {
            if (plateau.estDansLEau(x, y)) return "~";
            return ".";
        }
        return p.getShortName();
    }

    public int getRows() { return 9; }
    public int getCols() { return 7; }
}