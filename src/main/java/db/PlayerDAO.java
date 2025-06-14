// Structure : XouDouQi/src/db/PlayerDAO.java
package db;

import model.Player;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO pour accéder aux données des joueurs.
 */
public class PlayerDAO {
    private Connection connection;

    public PlayerDAO() {
        this.connection = Database.getConnection();
    }

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS players (" +
                "username TEXT PRIMARY KEY," +
                "password TEXT NOT NULL," +
                "score INTEGER DEFAULT 0);";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Erreur création table Player : " + e.getMessage());
        }
    }

    public boolean insert(Player player) {
        String sql = "INSERT INTO players(username, password, score) VALUES (?, ?, ?);";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, player.getUsername());
            pstmt.setString(2, player.getPassword());
            pstmt.setInt(3, player.getScore());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erreur insertion joueur : " + e.getMessage());
            return false;
        }
    }



    public Player getByUsername(String username) {
        String sql = "SELECT * FROM players WHERE username = ?;";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Player player = new Player(rs.getString("username"), rs.getString("password"));
                player.setScore(rs.getInt("score"));
                return player;
            }
        } catch (SQLException e) {
            System.err.println("Erreur récupération joueur : " + e.getMessage());
        }
        return null;
    }

    public void updateScore(Player player) {
        String sql = "UPDATE players SET score = ? WHERE username = ?;";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, player.getScore());
            pstmt.setString(2, player.getUsername());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur mise à jour score : " + e.getMessage());
        }
    }
}