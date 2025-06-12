package model.pieces;

import model.Piece;
import model.Player;
import model.Plateau;

public class Elephant extends Piece {
    public Elephant(int x, int y, Player proprietaire) {
        super(8, "Elephant", x, y, proprietaire);
    }

    @Override
    public boolean peutSeDeplacerVers(int newX, int newY, Plateau plateau) {
        return plateau.estCaseAdjacente(x, y, newX, newY);
    }
    @Override
    public String getShortName() {
        return (proprietaire.getCode().equals("P1")) ? "E1" : "E2";
    }

    @Override
    public boolean peutCapturer(Piece cible) {
        // Ne peut pas capturer le Rat
        if (cible instanceof model.pieces.Rat) {
            return false;
        }
        return super.peutCapturer(cible);
    }
}
