package model.pieces;

import game.Board;
import model.Piece;
import model.Player;

public class Rat extends Piece {
    public Rat(int x, int y, Player proprietaire) {
        super(1, "Rat", x, y, proprietaire);
    }

    @Override

    public boolean peutCapturer(Piece cible, Board plateau) {
        // 1: If target is in opponent's trap,  Rat can capture
        if (plateau.estUnPiege(cible.getX(), cible.getY(), this.getProprietaire())) {
            return true;
        }

        //  2: Rat cannot capture when exiting water
        if (this.estDansLeau && !cible.estDansLeau) {
            return false;
        }

        // Rule 3: Rat can always capture Elephant (even in water)
        if (cible instanceof Elephant) {
            return true;
        }

        //  Use normal force rules (Rat force=1 vs target force)
        return this.getForce() >= cible.getForce();
    }
    @Override
    public String getShortName() {
        return (proprietaire.getCode().equals("P1")) ? "R1" : "R2";
    }

    @Override
    public boolean peutSeDeplacerVers(int newX, int newY, Board plateau) {
        return plateau.estCaseAdjacente(x, y, newX, newY);
    }
}
