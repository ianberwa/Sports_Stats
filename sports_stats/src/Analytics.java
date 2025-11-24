import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class Analytics {
    public static JFrame showAnalysis()
    {
        //We create a frame for our GUI and initialize it's title
        JFrame frame = new JFrame("Sports Stats Dashboard");
        frame.setSize(1000, 700);

        //Since we want multiple tabs for each our data analytics graph we create a tabbed pane
        JTabbedPane tabs = new JTabbedPane();

        //We add tabs and in each tab we add a title and call functions to display charts
        try {
            tabs.addTab("Top Scorers", Displays.createBarChart(Queries.getTopScorers()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            tabs.addTab("Team Performance", Displays.createLineChart(Queries.getTeamPerformance()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            tabs.addTab("Player Ratings", Displays.createHeatMap(Queries.getPlayerAverages()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        frame.add(tabs, BorderLayout.CENTER);
        frame.setVisible(true);

        return frame;

    }
}
