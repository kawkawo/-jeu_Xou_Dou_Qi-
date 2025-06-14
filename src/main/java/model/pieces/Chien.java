package model.pieces;

import model.Piece;
import model.Player;
import game.Board;

public class Chien extends Piece {
    public Chien(int x, int y, Player proprietaire) {

        super(5, "Chien", x, y, proprietaire);
    }

    @Override
    public String getShortName() {
        return (proprietaire.getCode().equals("P1")) ? "D1" : "D2";
    }

    @Override
    public boolean peutSeDeplacerVers(int newX, int newY, Board plateau) {
        return plateau.estCaseAdjacente(x, y, newX, newY);
    }
}
