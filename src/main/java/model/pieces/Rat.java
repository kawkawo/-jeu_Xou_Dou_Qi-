package model.pieces;

import model.Piece;
import model.Player;
import model.Plateau;
import model.pieces.Elephant;

public class Rat extends Piece {
    public Rat(int x, int y, Player proprietaire) {
        super(1, "Rat", x, y, proprietaire);
    }

    @Override
    public boolean peutCapturer(Piece cible) {
        // Le Rat ne peut pas capturer en sortant de l'eau
        if (this.estDansLeau && !cible.estDansLeau) {
            return false;
        }

        // Peut capturer l'Éléphant
        if (cible instanceof Elephant) {
            return true;
        }

        // Sinon, règle normale
        return super.peutCapturer(cible);
    }
    @Override
    public String getShortName() {
        return (proprietaire.getCode().equals("P1")) ? "R1" : "R2";
    }

    @Override
    public boolean peutSeDeplacerVers(int newX, int newY, Plateau plateau) {
        return plateau.estCaseAdjacente(x, y, newX, newY);
    }
}
