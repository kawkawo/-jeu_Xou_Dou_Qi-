
// model/pieces/Tiger.java
package model.pieces;

import model.Piece;
import model.Player;
import model.Plateau;

public class Tiger extends Piece {
    public Tiger(int x, int y, Player proprietaire) {
        super(6, "Tiger", x, y, proprietaire);
    }
    @Override
    public String getShortName() {
        return (proprietaire.getCode().equals("P1")) ? "T1" : "T2";
    }


    @Override
    public boolean peutSeDeplacerVers(int newX, int newY, Plateau plateau) {
        return plateau.estCaseAdjacente(x, y, newX, newY);
    }
}
