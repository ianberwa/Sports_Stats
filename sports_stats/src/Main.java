import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        JFrame frame = new JFrame("Sports Stats Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);

        JTabbedPane tabs = new JTabbedPane();

        tabs.addTab("Top Scorers", Displays.createBarChart(Queries.getTopScorers()));
        tabs.addTab("Team Performance", Displays.createLineChart(Queries.getTeamPerformance()));
        tabs.addTab("Player Averages", Displays.createHeatMap(Queries.getPlayerAverages()));

        frame.add(tabs, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
