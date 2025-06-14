// game/rules/Rules.java
package game.rules;

import game.Board;
import model.Piece;


public class Rules {
    public boolean estDeplacementValide(Piece piece, int newX, int newY, Board plateau) {
        return piece.peutSeDeplacerVers(newX, newY, plateau);
    }
}



