
package model.pieces;

import model.Piece;
import model.Player;
import model.Plateau;

public class Lion extends Piece {
    public Lion(int x, int y, Player proprietaire) {
        super(7, "Lion", x, y, proprietaire);
    }
    @Override
    public String getShortName() {
        return (proprietaire.getCode().equals("P1")) ? "L1" : "L2";
    }


    @Override
    public boolean peutSeDeplacerVers(int newX, int newY, Plateau plateau) {
        // À améliorer : implémenter saut au-dessus de la rivière si applicable
        return plateau.estCaseAdjacente(x, y, newX, newY);
    }
}
