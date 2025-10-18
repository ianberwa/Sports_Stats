import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mariadb://localhost:3306/Sports_stats";
        String username = "root";
        String password = "";
        Connection con= DriverManager.getConnection(url,username,password);

        Statement statement = con.createStatement();

        //Query to display the Season top scorers
        ResultSet resultSet = statement.executeQuery("select s.player_id,p.first_name,p.last_name,sum(s.goals) as total_goals from player_stats as s,players as p where s.player_id=p.player_id group by p.player_id order by total_goals desc");
        System.out.println("Season Top Scorers:");
        System.out.println("________________________________________________");
        System.out.println("playerId\tfirstName\tlastName\tgoals");
        while(resultSet.next()){
            System.out.println(resultSet.getString("player_id")+"\t\t"+resultSet.getString("first_name")+"\t\t"+resultSet.getString("last_name")+"\t\t"+resultSet.getInt("total_goals"));
        }
        System.out.println(" ");
        System.out.println(" ");

        //Query to display Team performances
        ResultSet resultSet1= statement.executeQuery("""

                SELECT
    team,
    SUM(CASE WHEN goals_for > goals_against THEN 1 ELSE 0 END) AS wins,
    SUM(CASE WHEN goals_for = goals_against THEN 1 ELSE 0 END) AS draws,
    SUM(CASE WHEN goals_for < goals_against THEN 1 ELSE 0 END) AS losses,
    SUM(goals_for - goals_against) AS goal_difference
FROM (
    SELECT\s
        m.match_id,
        m.home_team AS team,
        SUM(CASE WHEN p.team = m.home_team THEN ps.goals ELSE 0 END) AS goals_for,
        SUM(CASE WHEN p.team = m.away_team THEN ps.goals ELSE 0 END) AS goals_against
    FROM matches m
    JOIN player_stats ps ON m.match_id = ps.match_id
    JOIN players p ON ps.player_id = p.player_id
    GROUP BY m.match_id, m.home_team, m.away_team

    UNION ALL

    SELECT\s
        m.match_id,
        m.away_team AS team,
        SUM(CASE WHEN p.team = m.away_team THEN ps.goals ELSE 0 END) AS goals_for,
        SUM(CASE WHEN p.team = m.home_team THEN ps.goals ELSE 0 END) AS goals_against
    FROM matches m
    JOIN player_stats ps ON m.match_id = ps.match_id
    JOIN players p ON ps.player_id = p.player_id
    GROUP BY m.match_id, m.home_team, m.away_team
) AS team_results
GROUP BY team
ORDER BY wins DESC, goal_difference DESC;
""");
        System.out.println("Team Performances:");
        System.out.println("________________________________________________");
        System.out.println("Team\tWins\tDraws\tLosses\tGoalDifference");
        while(resultSet1.next()){
            System.out.println(resultSet1.getString(1)+"\t\t"+resultSet1.getInt(2)+"\t\t"+resultSet1.getInt(3)+"\t\t"+resultSet1.getInt(4)+"\t\t"+resultSet1.getInt(5));
        }
        System.out.println(" ");
        System.out.println(" ");


//Query for getting player averages
ResultSet resultSet2=statement.executeQuery("""
SELECT p.player_id,
       p.first_name,
       p.last_name,
       AVG(ps.goals) as goals_per_match,
       AVG(ps.assists) as assists_per_match,
       AVG(ps.fouls) as fouls_per_match 
from players p join player_stats ps on p.player_id=ps.player_id 
GROUP BY p.player_id;
""");
        System.out.println("Players Averages:");
        System.out.println("_______________________________________________________________");
        System.out.println("playerId\tfirstName\tlastName\tgoals/game\tassists/game\tfouls/game");
        while(resultSet2.next()){
            System.out.println(resultSet2.getString(1)+"\t\t"+resultSet2.getString(2)+"\t\t"+resultSet2.getString(3)+"\t\t"+resultSet2.getFloat(4)+"\t\t"+resultSet2.getFloat(5)+"\t\t"+resultSet2.getFloat(6));
        }
        con.close();

    }
}