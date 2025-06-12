// Structure : XouDouQi/src/db/MatchDAO.java
package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO pour l'enregistrement et la récupération des parties jouées.
 */
public class MatchDAO {
    private Connection connection;

    public MatchDAO() {
        this.connection = Database.getConnection();
    }

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS matches (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "player1 TEXT NOT NULL," +
                "player2 TEXT NOT NULL," +
                "winner TEXT," +
                "date TEXT DEFAULT CURRENT_TIMESTAMP);";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Erreur création table Match : " + e.getMessage());
        }
    }

    public void insertMatch(String player1, String player2, String winner) {
        String sql = "INSERT INTO matches(player1, player2, winner) VALUES (?, ?, ?);";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, player1);
            pstmt.setString(2, player2);
            pstmt.setString(3, winner);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur insertion match : " + e.getMessage());
        }
    }

    public List<String> getMatchHistory(String player) {
        List<String> history = new ArrayList<>();
        String sql = "SELECT * FROM matches WHERE player1 = ? OR player2 = ? ORDER BY date DESC;";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, player);
            pstmt.setString(2, player);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String entry = rs.getString("date") + ": " + rs.getString("player1") + " vs " + rs.getString("player2") + " => Winner: " + rs.getString("winner");
                history.add(entry);
            }
        } catch (SQLException e) {
            System.err.println("Erreur récupération historique : " + e.getMessage());
        }
        return history;
    }
}
