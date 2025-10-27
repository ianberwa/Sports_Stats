//In this class we create functions that receive as parameters array lists from the previous class
//Then they draw charts and graph using the data
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.category.*;
import org.jfree.data.xy.*;
import org.jfree.chart.renderer.xy.XYBlockRenderer;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.renderer.LookupPaintScale;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.LookupPaintScale;
import org.jfree.chart.renderer.xy.XYBlockRenderer;
import org.jfree.chart.title.PaintScaleLegend;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.chart.ui.RectangleEdge;


import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Displays {

    public static JPanel createBarChart(List<Object[]> data) {
        //We create a dataset that will hold the data for both axes of the graph
        //we read the values we need from the data arraylist
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Object[] row : data) {
            dataset.addValue((Number) row[2], "Goals", (String) row[0]);
        }

        //We create the barchart and label the title and axes
        JFreeChart chart = ChartFactory.createBarChart(
                "Top Scorers", "Player", "Total Goals", dataset,
                PlotOrientation.VERTICAL, false, true, false);

        return new ChartPanel(chart);
    }
    //Same logic as in the bar graph above
    public static JPanel createLineChart(List<Object[]> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Object[] row : data) {
            dataset.addValue((Number) row[1], "Wins", (String) row[0]);
            dataset.addValue((Number) row[2], "Draws", (String) row[0]);
            dataset.addValue((Number) row[3], "Losses", (String) row[0]);
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Team Performance", "Team", "Results", dataset,
                PlotOrientation.VERTICAL, true, true, false);

        return new ChartPanel(chart);
    }

    public static JPanel createHeatMap(List<Object[]> data) {
        // Create XYZ dataset where z = y
        DefaultXYZDataset dataset = new DefaultXYZDataset();
        double[][] values = new double[3][data.size()]; // x, y, z

        int i = 0;
        for (Object[] row : data) {
            // Compute the metric
            double avg = (((Number) row[1]).doubleValue() +
                    ((Number) row[2]).doubleValue() -
                    ((Number) row[3]).doubleValue()) / 3.0;

            values[0][i] = i + 1;   // X: player index
            values[1][i] = avg;     // Y: metric value
            values[2][i] = avg;     // Z: same value for color
            i++;
        }
        dataset.addSeries("Player Metrics", values);

        // Axes
        NumberAxis xAxis = new NumberAxis("Player Index");
        NumberAxis yAxis = new NumberAxis("Average Metric");

        // Renderer setup
        XYBlockRenderer renderer = new XYBlockRenderer();
        renderer.setBlockWidth(1.0);
        renderer.setBlockHeight(0.05);

        // Paint scale (color mapping)
        LookupPaintScale paintScale = new LookupPaintScale(-0.5, 2.0, Color.GRAY);
        paintScale.add(-0.5, Color.MAGENTA); //Worst
        paintScale.add(0.15, Color.RED);    //Bad
        paintScale.add(0.45, Color.ORANGE); //Average
        paintScale.add(0.75, Color.YELLOW); //Good
        paintScale.add(1.05, Color.GREEN); //Elite

        renderer.setPaintScale(paintScale);

        // Plot
        XYPlot plot = new XYPlot(dataset, xAxis, yAxis, renderer);
        plot.setBackgroundPaint(Color.WHITE);

        // Add legend to explain color scale
        PaintScaleLegend legend = new PaintScaleLegend(paintScale, new NumberAxis("Metric Value"));
        legend.setPosition(RectangleEdge.RIGHT);

        // Chart
        JFreeChart chart = new JFreeChart(
                "Player Contributions Heatmap",
                JFreeChart.DEFAULT_TITLE_FONT,
                plot,
                false
        );
        chart.addSubtitle(legend);

        return new ChartPanel(chart);
    }

}
