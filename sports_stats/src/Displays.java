import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.category.*;
import org.jfree.data.xy.*;
import org.jfree.chart.renderer.xy.XYBlockRenderer;
import org.jfree.chart.axis.NumberAxis;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Displays {

    public static JPanel createBarChart(List<Object[]> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Object[] row : data) {
            dataset.addValue((Number) row[2], "Goals", (String) row[0]);
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Top Scorers", "Player", "Total Goals", dataset,
                PlotOrientation.VERTICAL, false, true, false);

        return new ChartPanel(chart);
    }

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
        // Create simple XY dataset for heatmap visualization of averages
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series = new XYSeries("Player Averages");

        int x = 1;
        for (Object[] row : data) {
            double avg = ((float) row[1] + (float) row[2] - (float) row[3]) / 3;
            series.add(x++, avg);
        }
        dataset.addSeries(series);

        NumberAxis xAxis = new NumberAxis("Player Index");
        NumberAxis yAxis = new NumberAxis("Average Metric");
        XYBlockRenderer renderer = new XYBlockRenderer();
        XYPlot plot = new XYPlot(dataset, xAxis, yAxis, renderer);

        JFreeChart chart = new JFreeChart("Player Averages Heatmap", JFreeChart.DEFAULT_TITLE_FONT, plot, false);
        return new ChartPanel(chart);
    }
}
