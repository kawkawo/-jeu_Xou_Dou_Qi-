package model.pieces;

import model.Piece;
import model.Player;
import model.Plateau;

public class Chat extends Piece {
    public Chat(int x, int y, Player proprietaire) {
        super(7, "Chat", x, y, proprietaire);
    }
    @Override
    public String getShortName() {
        return (proprietaire.getCode().equals("P1")) ? "C1" : "C2";
    }


    @Override
    public boolean peutSeDeplacerVers(int newX, int newY, Plateau plateau) {
        return plateau.estCaseAdjacente(x, y, newX, newY);
    }
}

