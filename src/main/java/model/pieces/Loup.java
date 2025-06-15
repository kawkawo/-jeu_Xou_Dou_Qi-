package model.pieces;

import model.Piece;
import model.Player;
import game.Board;

public class Loup extends Piece {
    public Loup(int x, int y, Player proprietaire) {
        super(6, "Loup", x, y, proprietaire);
    }
    @Override
    public String getShortName() {
        return (proprietaire.getCode().equals("P1")) ? "LO1" : "LO2";
    }

    @Override
    public boolean peutSeDeplacerVers(int newX, int newY, Board plateau) {
        // Déplacement normal mais ne peut pas entrer dans la rivière
        return plateau.estCaseAdjacente(x, y, newX, newY) && !plateau.estRiviere(newX, newY);
    }
    @Override

    public boolean peutCapturer(Piece cible, Board board) {

        if (board.estUnPiege(cible.getX(), cible.getY(), this.getProprietaire())) {
            return true;
        }
        return this.getForce() >= cible.getForce();
    }
}
