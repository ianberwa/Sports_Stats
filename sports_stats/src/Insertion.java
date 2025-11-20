import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class Insertion {
    public static JFrame insertData(){
        JFrame frame = new JFrame("Insertion Window");
        frame.setSize(1000, 700);

        JPanel playersPanel = new JPanel();
        playersPanel.setLayout(new GridLayout(6, 2, 10, 60));

        playersPanel.add(new JLabel("Player ID:"));
        JTextField playerID = new JTextField();
        playersPanel.add(playerID);

        playersPanel.add(new JLabel("First Name:"));
        JTextField firstName = new JTextField();
        playersPanel.add(firstName);

        playersPanel.add(new JLabel("Last Name:"));
        JTextField lastName = new JTextField();
        playersPanel.add(lastName);

        playersPanel.add(new JLabel("Team:"));
        JTextField team = new JTextField();
        playersPanel.add(team);

        playersPanel.add(new JLabel("Position:"));
        JTextField position = new JTextField();
        playersPanel.add(position);

        JButton p_insert = new JButton("Insert");
        p_insert.addActionListener(e -> {
            try {
                Queries.insertPlayer(playerID.getText(),firstName.getText(),lastName.getText(),team.getText(),position.getText());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        playersPanel.add(p_insert);

        // --- Panel for Matches ---
        JPanel matchesPanel = new JPanel();
        matchesPanel.setLayout(new GridLayout(5, 2, 10, 80));

        matchesPanel.add(new JLabel("Match ID:"));
        JTextField matchID = new JTextField();
        matchesPanel.add(matchID);

        matchesPanel.add(new JLabel("Date:"));
        JTextField date = new JTextField();
        matchesPanel.add(date);

        matchesPanel.add(new JLabel("Home Team:"));
        JTextField home_team = new JTextField();
        matchesPanel.add(home_team);

        matchesPanel.add(new JLabel("Away Team:"));
        JTextField away_team = new JTextField();
        matchesPanel.add(away_team);

        JButton m_insert=new JButton("Insert");
        m_insert.addActionListener(e -> {
            try {
                Queries.insertMatch(matchID.getText(),date.getText(),home_team.getText(),away_team.getText());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        matchesPanel.add(m_insert);

        // --- Panel for Player Stats ---
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(7, 2, 10, 40));

        statsPanel.add(new JLabel("Stat ID:"));
        JTextField statID = new JTextField();
        statsPanel.add(statID);

        statsPanel.add(new JLabel("Match ID:"));
        JTextField mID = new JTextField();
        statsPanel.add(mID);

        statsPanel.add(new JLabel("Player ID:"));
        JTextField pID = new JTextField();
        statsPanel.add(pID);

        statsPanel.add(new JLabel("Goals:"));
        JTextField goals = new JTextField();
        statsPanel.add(goals);

        statsPanel.add(new JLabel("Assists:"));
        JTextField assists = new JTextField();
        statsPanel.add(assists);

        statsPanel.add(new JLabel("Fouls:"));
        JTextField fouls = new JTextField();
        statsPanel.add(fouls);

        JButton s_insert=new JButton("Insert");
        s_insert.addActionListener(e -> {
            try {
                Queries.insertStats(statID.getText(),mID.getText(),pID.getText(),Integer.parseInt(goals.getText()),Integer.parseInt(assists.getText()),Integer.parseInt(fouls.getText()));
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        statsPanel.add(s_insert);


        JTabbedPane tabs = new JTabbedPane();

         tabs.add("Players",playersPanel);
         tabs.add("Matches",matchesPanel);
         tabs.add("PlayerStats",statsPanel);

         frame.add(tabs, BorderLayout.CENTER);
         frame.setVisible(true);
         return frame;
    }
}
