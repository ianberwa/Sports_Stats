import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;

public class Tables {
    public static JFrame showTables() throws SQLException {
        JFrame frame=new JFrame("Tables");
        frame.setSize(1000,700);

        DefaultTableModel model1=new DefaultTableModel();

        model1.addColumn("player_id");
        model1.addColumn("first_name");
        model1.addColumn("last_name");
        model1.addColumn("team");
        model1.addColumn("position");

        JTable players=new JTable(model1);

        Queries.showPlayers(model1);

        JScrollPane scroll=new JScrollPane(players);

        DefaultTableModel model2=new DefaultTableModel();

        model2.addColumn("match_id");
        model2.addColumn("date");
        model2.addColumn("home_team");
        model2.addColumn("away_team");

        JTable matches=new JTable(model2);

        Queries.showMatches(model2);

        JScrollPane scroll2=new JScrollPane(matches);


        DefaultTableModel model3=new DefaultTableModel();
        model3.addColumn("stat_id");
        model3.addColumn("match_id");
        model3.addColumn("player_id");
        model3.addColumn("goals");
        model3.addColumn("assists");
        model3.addColumn("fouls");

        JTable playerStats=new JTable(model3);

        Queries.showPlayerStats(model3);

        JScrollPane scroll3=new JScrollPane(playerStats);

        JTabbedPane tabs=new JTabbedPane();

        tabs.addTab("Players",scroll);
        tabs.addTab("Matches",scroll2);
        tabs.addTab("Player Stats",scroll3);

        frame.add(tabs, BorderLayout.CENTER);

        frame.setVisible(true);
        return frame;
    }
}
