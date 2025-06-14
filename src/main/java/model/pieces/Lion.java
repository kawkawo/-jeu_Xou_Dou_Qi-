
package model.pieces;

import game.Board;
import model.Piece;
import model.Player;


public class Lion extends Piece {
    public Lion(int x, int y, Player proprietaire) {
        super(7, "Lion", x, y, proprietaire);
    }
    @Override
    public String getShortName() {
        return (proprietaire.getCode().equals("P1")) ? "L1" : "L2";
    }


    @Override
    public boolean peutSeDeplacerVers(int newX, int newY, Board plateau) {
        // Vérifie d'abord si c'est un déplacement adjacent normal
        if (plateau.estCaseAdjacente(x, y, newX, newY)) {
            return !plateau.estRiviere(newX, newY); // Lion ne peut pas entrer dans la rivière
        }

        // Vérifie le saut par-dessus la rivière
        if (plateau.estSautRiviereValide(x, y, newX, newY)) {
            // Vérifie qu'il n'y a pas de Rat dans la rivière sur le chemin
            return !plateau.aRatDansRiviereEntre(x, y, newX, newY);
        }

        return false;
    }
    @Override
    public boolean peutCapturer(Piece cible, Board board) {
        return this.getForce() >= cible.getForce(); // Lion captures weaker pieces
    }
}
