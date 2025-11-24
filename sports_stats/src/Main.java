import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        JFrame mainFrame=new JFrame("Main Page");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(500,300);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setLayout(null);

        JButton graph=new JButton("Graph");
        JButton tables=new JButton("Tables");
        JButton update=new JButton("Update");
        JLabel text=new JLabel("WELCOME TO THE SPORTS ANALYSIS SYSTEM ");

        text.setBounds(40,10,450,50);
        graph.setBounds(100,70,100,50);
        tables.setBounds(300,70,100,50);
        update.setBounds(200,170,100,50);

        text.setFont(new Font("Algerian",Font.BOLD,18));

        mainFrame.add(text);
        mainFrame.add(graph);
        mainFrame.add(tables);
        mainFrame.add(update);

        graph.addActionListener(e -> {
            Analytics.showAnalysis();
        });
        tables.addActionListener(e -> {
            try {
                Tables.showTables();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        update.addActionListener(e -> {
            Insertion.insertData();
        });
        mainFrame.setVisible(true);
    }

}
