package model.pieces;

import game.Board;
import model.Piece;
import model.Player;


public class Elephant extends Piece {
    public Elephant(int x, int y, Player proprietaire) {
        super(8, "Elephant", x, y, proprietaire);
    }


    @Override
    public String getShortName() {
        return (proprietaire.getCode().equals("P1")) ? "E1" : "E2";
    }

    @Override
    public boolean peutSeDeplacerVers(int newX, int newY, Board plateau) {
        // Déplacement normal mais ne peut pas entrer dans la rivière
        return plateau.estCaseAdjacente(x, y, newX, newY) && !plateau.estRiviere(newX, newY);
    }

    //@Override
   /* public boolean peutCapturer(Piece cible,Board plateau) {
        // Ne peut pas capturer le Rat
        return !(cible instanceof Rat) && super.peutCapturer(cible,plateau);
    }*/
    @Override

    public boolean peutCapturer(Piece cible, Board board) {

        if (board.estUnPiege(cible.getX(), cible.getY(), this.getProprietaire())) {
            return true;
        }
        return this.getForce() >= cible.getForce();
    }
}
