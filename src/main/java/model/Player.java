
// Structure : XouDouQi/src/model/Player.java
package model;

/**
 * Classe représentant un joueur du jeu.
 */
public class Player {
    private static int counter = 1;  // counts how many players have been created
    private final String username;
    private final String code; // "P1", "P2"
    private String password;
    private int score;




    public Player(String username, String password) {
        this.username = username;
        this.password = password;
        this.code = "P" + counter++;
        this.score = 0;
    }
    public String getCode() {
        return code;
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
