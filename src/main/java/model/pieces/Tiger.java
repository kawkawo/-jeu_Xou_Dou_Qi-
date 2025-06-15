
// model/pieces/Tiger.java
package model.pieces;

import game.Board;
import model.Piece;
import model.Player;


public class Tiger extends Piece {
    public Tiger(int x, int y, Player proprietaire) {
        super(6, "Tiger", x, y, proprietaire);
    }
    @Override
    public String getShortName() {
        return (proprietaire.getCode().equals("P1")) ? "T1" : "T2";
    }


    @Override
    public boolean peutSeDeplacerVers(int newX, int newY, Board plateau) {
        //
        if (plateau.estCaseAdjacente(x, y, newX, newY)) {
            return !plateau.estRiviere(newX, newY);
        }

        if (plateau.estSautRiviereValide(x, y, newX, newY)) {
            return !plateau.aRatDansRiviereEntre(x, y, newX, newY);
        }

        return false;
    }
    @Override

    public boolean peutCapturer(Piece cible, Board board) {

        if (board.estUnPiege(cible.getX(), cible.getY(), this.getProprietaire())) {
            return true;
        }
        return this.getForce() >= cible.getForce();
    }
}
