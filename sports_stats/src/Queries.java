//We create functions to return a list of the desired query results
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.*;

public class Queries {

    public static List<Object[]> getTopScorers() throws SQLException {
        //we store the query in a string variable
        String query = """
            SELECT s.player_id, p.first_name, p.last_name, SUM(s.goals) AS total_goals
            FROM player_stats AS s
            JOIN players AS p ON s.player_id = p.player_id
            GROUP BY p.player_id
            ORDER BY total_goals DESC;
        """;

        //we create a list that will hold every row as an object
        List<Object[]> data = new ArrayList<>();

        //We establish a connection from the database class and execute the query
        try (Connection con = Database.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
        //Use of a while loop to record each row of the resultset into our list
            while (rs.next()) {
                data.add(new Object[]{
                        rs.getString("player_id"),
                        rs.getString("first_name")+" "+rs.getString("last_name"),
                        rs.getInt("total_goals")
                });
            }
        }
        return data;
    }

    //We do the exact same thing as above i.e.(make connection,run the query, store the results in a list and return that list)
    public static List<Object[]> getTeamPerformance() throws SQLException {
        String query = """
            SELECT team, SUM(CASE WHEN goals_for > goals_against THEN 1 ELSE 0 END) AS wins,
                   SUM(CASE WHEN goals_for = goals_against THEN 1 ELSE 0 END) AS draws,
                   SUM(CASE WHEN goals_for < goals_against THEN 1 ELSE 0 END) AS losses,
                   SUM(goals_for - goals_against) AS goal_difference
            FROM (
                SELECT m.match_id, m.home_team AS team,
                       SUM(CASE WHEN p.team = m.home_team THEN ps.goals ELSE 0 END) AS goals_for,
                       SUM(CASE WHEN p.team = m.away_team THEN ps.goals ELSE 0 END) AS goals_against
                FROM matches m
                JOIN player_stats ps ON m.match_id = ps.match_id
                JOIN players p ON ps.player_id = p.player_id
                GROUP BY m.match_id, m.home_team, m.away_team

                UNION ALL

                SELECT m.match_id, m.away_team AS team,
                       SUM(CASE WHEN p.team = m.away_team THEN ps.goals ELSE 0 END) AS goals_for,
                       SUM(CASE WHEN p.team = m.home_team THEN ps.goals ELSE 0 END) AS goals_against
                FROM matches m
                JOIN player_stats ps ON m.match_id = ps.match_id
                JOIN players p ON ps.player_id = p.player_id
                GROUP BY m.match_id, m.home_team, m.away_team
            ) AS team_results
            GROUP BY team
            ORDER BY wins DESC, goal_difference DESC;
        """;

        List<Object[]> data = new ArrayList<>();
        try (Connection con = Database.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                data.add(new Object[]{
                        rs.getString("team"),
                        rs.getInt("wins"),
                        rs.getInt("draws"),
                        rs.getInt("losses"),
                        rs.getInt("goal_difference")
                });
            }
        }
        return data;
    }

    public static List<Object[]> getPlayerAverages() throws SQLException {
        String query = """
            SELECT p.player_id, p.first_name, p.last_name,
                   AVG(ps.goals) as goals_per_match,
                   AVG(ps.assists) as assists_per_match,
                   AVG(ps.fouls) as fouls_per_match
            FROM players p
            JOIN player_stats ps ON p.player_id = ps.player_id
            GROUP BY p.player_id;
        """;

        List<Object[]> data = new ArrayList<>();
        try (Connection con = Database.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                data.add(new Object[]{
                        rs.getString("first_name") + " " + rs.getString("last_name"),
                        rs.getFloat("goals_per_match"),
                        rs.getFloat("assists_per_match"),
                        rs.getFloat("fouls_per_match")
                });
            }
        }
        return data;
    }
    //Functions that will be used for table displays
    public static void showPlayers(DefaultTableModel model) throws SQLException {
        String sql = "Select * from players";
        try (Connection con = Database.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql);
        ) {
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("player_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("team"),
                        rs.getString("position")
                });
            }
        }

    }
    public static void showMatches(DefaultTableModel model) throws SQLException {
        String sql = "Select * from matches";
        try (Connection con = Database.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql);
        ) {
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("match_id"),
                        rs.getString("date"),
                        rs.getString("home_team"),
                        rs.getString("away_team")
                });
            }
        }

    }
    public static void showPlayerStats(DefaultTableModel model) throws SQLException {
        String sql = "Select * from player_stats";
        try (Connection con = Database.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql);
        ) {
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("stat_id"),
                        rs.getString("match_id"),
                        rs.getString("player_id"),
                        rs.getString("goals"),
                        rs.getString("assists"),
                        rs.getString("fouls")
                });
            }
        }

    }
    //Functions used for data insertion
    public static void insertPlayer(String id,String fname,String lname,String team,String position) throws SQLException {
        String sql="Insert into players values(?,?,?,?,?)";

        try {Connection con = Database.getConnection();
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1,id);
        stmt.setString(2,fname);
        stmt.setString(3,lname);
        stmt.setString(4,team);
        stmt.setString(5,position);
        stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        JOptionPane.showMessageDialog(null,"Player has been inserted successfully!");
    }
    public static void insertMatch(String id,String date,String home_team,String away_team) throws SQLException {
        String sql="Insert into matches values(?,?,?,?)";

        try {Connection con = Database.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1,id);
            stmt.setString(2,date);
            stmt.setString(3,home_team);
            stmt.setString(4,away_team);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        JOptionPane.showMessageDialog(null,"Match has been inserted successfully!");
    }
    public static void insertStats(String s_id,String m_id,String p_id,int goals,int assists,int fouls) throws SQLException {
        String sql="Insert into matches values(?,?,?,?,?,?,?)";

        try {Connection con = Database.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1,s_id);
            stmt.setString(2,m_id);
            stmt.setString(3,p_id);
            stmt.setInt(4,goals);
            stmt.setInt(5,assists);
            stmt.setInt(6,fouls);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        JOptionPane.showMessageDialog(null,"stats have been inserted successfully!");
    }

}
