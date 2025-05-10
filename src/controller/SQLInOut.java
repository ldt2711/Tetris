package controller;

import model.GameState;

import java.sql.*;
import java.util.ArrayList;

public class SQLInOut {

    static String url = "jdbc:sqlserver://DESKTOP-M5DETI7\\SQLEXPRESS:1433;databaseName=Tetris;encrypt=true;trustServerCertificate=true";
    static String username = "sa";
    static String password = "123456789";

    public static ArrayList<GameState> getTopScore(int limit) throws SQLException{
        ArrayList<GameState> scores = new ArrayList<>();

        String sql = "SELECT TOP (?) player_name, score FROM HighScore ORDER BY score DESC";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, limit);
            ResultSet rs = pstm.executeQuery();

            while(rs.next()) {
                String name = rs.getString("player_name");
                int score = rs.getInt("score");
                if(name != null && !name.trim().isEmpty() && score > 0) {
                    scores.add(new GameState(name, score));
                }
            }
            System.out.println("Chao sql");
        }
        return scores;
    }

    public static void addScore(GameState gameState) throws SQLException {
        String sql = "INSERT INTO HighScore(player_name, score) VALUES(?, ?)";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, gameState.getPlayerName());
            ps.setInt(2, gameState.getScore());
            ps.executeUpdate();
        }
    }
}
