package model;

public class Plateau {
    public static final int LARGEUR = 7;
    public static final int HAUTEUR = 9;

    public boolean estCaseAdjacente(int x1, int y1, int x2, int y2) {
        int dx = Math.abs(x1 - x2);
        int dy = Math.abs(y1 - y2);
        return (dx == 1 && dy == 0) || (dx == 0 && dy == 1);
    }

    public boolean estDansLEau(int x, int y) {
        return (x >= 1 && x <= 2 || x >= 4 && x <= 5) && (y >= 3 && y <= 5);
    }

    public boolean estUnPiege(int x, int y, Player joueur) {
        if (joueur.getUsername().equals("Joueur1")) {
            return (x == 2 && y == 0) || (x == 4 && y == 0) || (x == 3 && y == 1);
        } else {
            return (x == 2 && y == 8) || (x == 4 && y == 8) || (x == 3 && y == 7);
        }
    }

    public boolean estUnSanctuaire(int x, int y, Player joueur) {
        if (joueur.getUsername().equals("Joueur1")) {
            return x == 3 && y == 8;  // Player 1's target is opponent's sanctuary
        } else {
            return x == 3 && y == 0;  // Player 2's target is opponent's sanctuary
        }
    }
}