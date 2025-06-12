package model;

public abstract class Piece {
    protected int force;
    protected String nom;
    protected int x, y;
    public boolean estDansLeau;
    protected Player proprietaire;

    public Piece(int force, String nom, int x, int y, Player proprietaire) {
        this.force = force;
        this.nom = nom;
        this.x = x;
        this.y = y;
        this.estDansLeau = false;
        this.proprietaire = proprietaire;
    }

    public abstract String getShortName();

    public abstract boolean peutSeDeplacerVers(int newX, int newY, Plateau plateau);

    public boolean peutCapturer(Piece cible) {
        if (cible.estDansUnPiege()) {
            return true;
        }
        return this.force >= cible.force;
    }

    public boolean estDansUnPiege() {
        Plateau plateau = new Plateau();
        return plateau.estUnPiege(x, y, proprietaire);
    }

    // Getters and setters
    public int getX() { return x; }
    public int getY() { return y; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public Player getProprietaire() { return proprietaire; }
    public String getNom() { return nom; }
    public void setDansLeau(boolean dansLeau) { this.estDansLeau = dansLeau; }
    public boolean isDansLeau() { return estDansLeau; }
    public int getForce() { return force; }
}