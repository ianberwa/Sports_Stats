import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        //We create a frame for our GUI and initialize it's title
        JFrame frame = new JFrame("Sports Stats Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);

        //Since we want multiple tabs for each our data analytics graph we create a tabbed pane
        JTabbedPane tabs = new JTabbedPane();

        //We add tabs and in each tab we add a title and call functions to display charts
        tabs.addTab("Top Scorers", Displays.createBarChart(Queries.getTopScorers()));
        tabs.addTab("Team Performance", Displays.createLineChart(Queries.getTeamPerformance()));
        tabs.addTab("Player Ratings", Displays.createHeatMap(Queries.getPlayerAverages()));

        frame.add(tabs, BorderLayout.CENTER);
        frame.setVisible(true);
    }

}
