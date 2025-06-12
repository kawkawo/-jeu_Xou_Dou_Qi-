// game/rules/Rules.java
package game.rules;

import model.Piece;
import model.Plateau;

public class Rules {
    public boolean estDeplacementValide(Piece piece, int newX, int newY, Plateau plateau) {
        return piece.peutSeDeplacerVers(newX, newY, plateau);
    }
}



