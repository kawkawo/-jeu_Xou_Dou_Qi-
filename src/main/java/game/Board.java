package game;

import model.*;
import model.pieces.*;

public class Board {
    private Piece[][] cases = new Piece[7][9];  // [colonne][ligne] - 7x9
    public static final int WIDTH = 7;
    public static final int HEIGHT = 9;

    public Board(Player joueur1, Player joueur2) {
        initialiserPieces(joueur1, joueur2);
    }
    private void initialiserPieces(Player j1, Player j2) {
        // Joueur 1 (bas)
        setPiece(0, 8, new Tiger(0, 8, j1));        // coin bas gauche
        setPiece(1, 7, new Chat(1, 7, j1));
        setPiece(0, 6, new Elephant(0, 6, j1));
        setPiece(2, 6, new Loup(2, 6, j1));
        setPiece(4, 6, new Panthere(4, 6, j1));
        setPiece(5, 7, new Chien(5, 7, j1));
        setPiece(6, 6, new Rat(6, 6, j1));
        setPiece(6, 8, new Lion(6, 8, j1));         // coin bas droit

        // Joueur 2 (haut)
        setPiece(0, 0, new Lion(0, 0, j2));
        setPiece(1, 1, new Chien(1, 1, j2));
        setPiece(0, 2, new Rat(0, 2, j2));
        setPiece(2, 2, new Panthere(2, 2, j2));
        setPiece(4, 2, new Loup(4, 2, j2));
        setPiece(6, 2, new Elephant(6, 2, j2));
        setPiece(5, 1, new Chat(5, 1, j2));
        setPiece(6, 0, new Tiger(6, 0, j2));        // coin haut droit
    }


    /* Méthodes de gestion des pièces */
    public Piece getPiece(int x, int y) {
        if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) {
            return null;
        }
        return cases[x][y];
    }

    public void setPiece(int x, int y, Piece piece) {
        if (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT) {
            cases[x][y] = piece;
            if (piece != null) {
                piece.setX(x);
                piece.setY(y);
                piece.setDansLeau(estDansLEau(x, y));
            }
        }
    }

    /* Méthodes de vérification du plateau */
    public boolean estCaseAdjacente(int x1, int y1, int x2, int y2) {
        int dx = Math.abs(x1 - x2);
        int dy = Math.abs(y1 - y2);
        return (dx == 1 && dy == 0) || (dx == 0 && dy == 1);
    }


    public boolean estDansLEau(int x, int y) {
        // Première rivière: colonnes B-C (1-2), lignes 4-6 (3-5 en index 0-based)
        // Deuxième rivière: colonnes E-F (4-5), lignes 4-6
        return ((x >= 1 && x <= 2) || (x >= 4 && x <= 5)) && (y >= 3 && y <= 5);
    }

    public boolean estUnSanctuaire(int x, int y, Player joueur) {
        if (joueur == null) {
            return (x == 3 && y == 0) || (x == 3 && y == 8); // Tous les sanctuaires
        }
        return joueur.getCode().equals("P1")
                ? x == 3 && y == 8  // Sanctuaire adverse P1
                : x == 3 && y == 0;  // Sanctuaire adverse P2
    }

    public boolean estUnPiege(int x, int y, Player joueur) {
        if (joueur == null) {
            // Tous les pièges
            return (x == 2 && y == 0) || (x == 4 && y == 0) || (x == 3 && y == 1) || // Pièges P1
                    (x == 2 && y == 8) || (x == 4 && y == 8) || (x == 3 && y == 7);   // Pièges P2
        }
        return joueur.getCode().equals("P1")
                ? (x == 2 && y == 8) || (x == 4 && y == 8) || (x == 3 && y == 7)  // Pièges P2
                : (x == 2 && y == 0) || (x == 4 && y == 0) || (x == 3 && y == 1); // Pièges P1
    }

    public boolean estRiviere(int x, int y) {
        return estDansLEau(x, y);
    }
    public boolean estSautRiviereValide(int fromX, int fromY, int toX, int toY) {
        if (fromX == toX) { // Saut vertical
            int minY = Math.min(fromY, toY);
            int maxY = Math.max(fromY, toY);
            //  un saut de 4 cases (3 de rivière au milieu)
            if (maxY - minY == 4) {
                for (int y = minY + 1; y < maxY; y++) {
                    if (!estRiviere(fromX, y)) return false;
                }
                return true;
            }
        } else if (fromY == toY) { // Saut horizontal
            int minX = Math.min(fromX, toX);
            int maxX = Math.max(fromX, toX);
            //
            if (maxX - minX == 3) {
                for (int x = minX + 1; x < maxX; x++) {
                    if (!estRiviere(x, fromY)) return false;
                }
                return true;
            }
        }
        return false;
    }


    public boolean aRatDansRiviereEntre(int fromX, int fromY, int toX, int toY) {
        if (fromX == toX) { // Saut vertical
            int step = (toY > fromY) ? 1 : -1;
            for (int y = fromY + step; y != toY; y += step) {
                if (estRiviere(fromX, y)) {
                    Piece p = getPiece(fromX, y);
                    if (p != null && p instanceof Rat) return true;
                }
            }
        } else if (fromY == toY) { // Saut horizontal
            int step = (toX > fromX) ? 1 : -1;
            for (int x = fromX + step; x != toX; x += step) {
                if (estRiviere(x, fromY)) {
                    Piece p = getPiece(x, fromY);
                    if (p != null && p instanceof Rat) return true;
                }
            }
        }
        return false;
    }



    /* Méthodes de jeu */
    public boolean processMove(String moveInput, Player player) {
        try {
            String[] parts = moveInput.trim().split(" ");
            if (parts.length != 2) return false;

            int[] from = parsePosition(parts[0]);
            int[] to = parsePosition(parts[1]);

            int x1 = from[0], y1 = from[1];
            int x2 = to[0], y2 = to[1];

            // Validation des positions
            if (x1 < 0 || x1 >= WIDTH || y1 < 0 || y1 >= HEIGHT ||
                    x2 < 0 || x2 >= WIDTH || y2 < 0 || y2 >= HEIGHT) {
                return false;
            }

            Piece piece = getPiece(x1, y1);
            if (piece == null || !piece.getProprietaire().equals(player)) return false;

            // Vérification sanctuaire
            if (estUnSanctuaire(x2, y2, player)) return false;

            // Vérification déplacement valide
            if (!piece.peutSeDeplacerVers(x2, y2, this)) return false;

            // Vérification capture
            Piece cible = getPiece(x2, y2);
            if (cible != null) {
                if (cible.getProprietaire().equals(player)) return false;
                if (!piece.peutCapturer(cible,this)) return false;
            }

            // Exécution du mouvement
            setPiece(x2, y2, piece);
            setPiece(x1, y1, null);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkVictory(Player player) {
        // Vérifie si le joueur a atteint le sanctuaire adverse
        int sanctuaryY = player.getUsername().equals("Joueur1") ? 8 : 0;
        Piece sanctuaryPiece = getPiece(3, sanctuaryY);
        return sanctuaryPiece != null && sanctuaryPiece.getProprietaire().equals(player);
    }

    /* Méthodes utilitaires */
    private int[] parsePosition(String pos) {
        int col = Character.toUpperCase(pos.charAt(0)) - 'A';
        int row = Integer.parseInt(pos.substring(1)) - 1;
        return new int[]{col, row};
    }

    public String getCellSymbol(int x, int y) {
        Piece p = getPiece(x, y);
        if (p == null) {
            /*if (estDansLEau(x, y)) return "~";
            if (estUnSanctuaire(x, y, new Player("Joueur1", "P1")) ||
                    estUnSanctuaire(x, y, new Player("Joueur2", "P2"))) return "S";*/
            return ".";}

        return p.getShortName();
    }

    public int getRows() { return HEIGHT; }
    public int getCols() { return WIDTH; }
}