/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulator;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.GroupedStackedBarRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.KeyToGroupMap;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author IDontKnow
 */
public class PerformanceCalculation {

    private LoadTrace l;
    private int totalCostClass;

    public PerformanceCalculation() throws IOException {
        l = new LoadTrace("tracefile");
        l.loadTraceIntoDS();
    }

    public JPanel waitTime1() {
        LinkedHashSet no = new LinkedHashSet();
        LinkedHashMap<Integer, ArrayList<Double>> wait1 = new LinkedHashMap<>();

        for (Map.Entry<Integer, TraceObject> entry : l.getLocalTrace().entrySet()) {
            TraceObject traceObject = entry.getValue();

            if (wait1.get(traceObject.getSurgeonId()) == null) {
                ArrayList details = new ArrayList();
                details.add(traceObject.getWaitTime1());
                wait1.put(traceObject.getSurgeonId(), details);
            } else {
                wait1.get(traceObject.getSurgeonId()).add(traceObject.getWaitTime1());
            }

            no.add(traceObject.getSurgeonId());
        }
        String[] column = new String[no.size()];

        String series1 = "Wait Time 1";
        for (int i = 0; i < no.size(); i++) {
            column[i] = "Surgeon " + (i + 1);
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        LinkedHashMap<Integer, Double> average = new LinkedHashMap<>();
        for (Map.Entry<Integer, ArrayList<Double>> entry : wait1.entrySet()) {
            Integer integer = entry.getKey();
            ArrayList<Double> arrayList = entry.getValue();
            double total = 0;
            for (Double double1 : arrayList) {
                total += double1;
            }
            average.put(integer, total / arrayList.size());
        }

        for (int i = 1; i <= average.size(); i++) {
            dataset.addValue(Math.round(average.get(i) / 600), series1, column[i - 1]);
        }
        JFreeChart chart = ChartFactory.createBarChart(
                "Wait Time 1", // chart title
                "Surgeon ID", // domain axis label
                "Days", // range axis label
                dataset, // data
                PlotOrientation.VERTICAL, // orientation
                true, // include legend
                true, // tooltips?
                false // URLs?
        );

        return new ChartPanel(chart);
    }

    public JPanel waitTime2() {
        LinkedHashSet no = new LinkedHashSet();
        LinkedHashMap<Integer, ArrayList<Double>> wait1 = new LinkedHashMap<>();

        for (Map.Entry<Integer, TraceObject> entry : l.getLocalTrace().entrySet()) {
            TraceObject traceObject = entry.getValue();

            if (wait1.get(traceObject.getSurgeonId()) == null) {
                ArrayList details = new ArrayList();
                details.add(traceObject.getWaitTime2());
                wait1.put(traceObject.getSurgeonId(), details);
            } else {
                wait1.get(traceObject.getSurgeonId()).add(traceObject.getWaitTime2());
            }

            no.add(traceObject.getSurgeonId());
        }
        String[] column = new String[no.size()];

        String series1 = "Wait Time 2";
        for (int i = 0; i < no.size(); i++) {
            column[i] = "Surgeon " + (i + 1);
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        LinkedHashMap<Integer, Double> average = new LinkedHashMap<>();
        for (Map.Entry<Integer, ArrayList<Double>> entry : wait1.entrySet()) {
            Integer integer = entry.getKey();
            ArrayList<Double> arrayList = entry.getValue();
            double total = 0;
            for (Double double1 : arrayList) {
                total += double1;
            }
            average.put(integer, total / arrayList.size());
        }

        for (int i = 1; i <= average.size(); i++) {
            dataset.addValue(Math.round(average.get(i) / 600), series1, column[i - 1]);
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Wait Time 2", // chart title
                "Surgeon ID", // domain axis label
                "Days", // range axis label
                dataset, // data
                PlotOrientation.VERTICAL, // orientation
                true, // include legend
                true, // tooltips?
                false // URLs?
        );

        return new ChartPanel(chart);
    }

    public JPanel operationRoom() {
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        for (int i = 1; i <= l.getOperationUtilization().size(); i++) {
            int count = l.getOperationUtilization().get(i).size();
            pieDataset.setValue("Operation Room " + i, count);
        }

        JFreeChart chart = ChartFactory.createPieChart3D("Operation Room Utilization", pieDataset, true, true, true);
        return new ChartPanel(chart);
    }

    public JPanel surgeonOperation() {
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        for (int i = 1; i <= l.getSurgeonUtilization().size(); i++) {
            int count = l.getSurgeonUtilization().get(i).size();
            pieDataset.setValue("Surgeon ID " + i, count);
        }

        JFreeChart chart = ChartFactory.createPieChart3D("Surgeon Utilization", pieDataset, true, true, true);
        return new ChartPanel(chart);
    }

    JPanel minmaxwaitTime2(boolean minCheck) {
        LinkedHashSet no = new LinkedHashSet();
        LinkedHashMap<Integer, ArrayList<Double>> wait1 = new LinkedHashMap<>();

        for (Map.Entry<Integer, TraceObject> entry : l.getLocalTrace().entrySet()) {
            TraceObject traceObject = entry.getValue();

            if (wait1.get(traceObject.getSurgeonId()) == null) {
                ArrayList details = new ArrayList();
                details.add(traceObject.getWaitTime2());
                wait1.put(traceObject.getSurgeonId(), details);
            } else {
                wait1.get(traceObject.getSurgeonId()).add(traceObject.getWaitTime2());
            }

            no.add(traceObject.getSurgeonId());
        }

        XYSeriesCollection dataset = new XYSeriesCollection();

        LinkedHashMap<Integer, Double> average = new LinkedHashMap<>();

        for (Map.Entry<Integer, ArrayList<Double>> entry : wait1.entrySet()) {
            Integer integer = entry.getKey();
            ArrayList<Double> arrayList = entry.getValue();
            double value = 0;
            if (minCheck) {
                value = Collections.min(arrayList);
                value = value / 600;
            } else {
                value = Collections.max(arrayList);
                value = value / 600;
            }

            average.put(integer, value);
        }

        XYSeries series = new XYSeries("Surgeon Minimum Wait Time 2");
        for (int i = 1; i <= average.size(); i++) {
            series.add(i, average.get(i));
        }
        dataset.addSeries(series);
        String name;
        if (minCheck) {
            name = "Minimum";
        } else {
            name = "Maximum";
        }
        // Generate the graph
        JFreeChart chart = ChartFactory.createXYLineChart(
                name + " Wait Time 2 For Patients", // Title
                "Surgeon ID", // x-axis Label
                "Time (Days)", // y-axis Label
                dataset, // Dataset
                PlotOrientation.VERTICAL, // Plot Orientation
                true, // Show Legend
                true, // Use tooltips
                false // Configure chart to generate URLs?
        );

        XYPlot xyPlot = (XYPlot) chart.getPlot();

        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) xyPlot.getRenderer();
        renderer.setBaseShapesVisible(true);
        NumberAxis domain = (NumberAxis) xyPlot.getDomainAxis();
        domain.setVerticalTickLabels(true);

        return new ChartPanel(chart);
    }

    JPanel minmaxwaitTime1(boolean minCheck) {
        LinkedHashSet no = new LinkedHashSet();
        LinkedHashMap<Integer, ArrayList<Double>> wait1 = new LinkedHashMap<>();

        for (Map.Entry<Integer, TraceObject> entry : l.getLocalTrace().entrySet()) {
            TraceObject traceObject = entry.getValue();

            if (wait1.get(traceObject.getSurgeonId()) == null) {
                ArrayList details = new ArrayList();
                details.add(traceObject.getWaitTime1());
                wait1.put(traceObject.getSurgeonId(), details);
            } else {
                wait1.get(traceObject.getSurgeonId()).add(traceObject.getWaitTime1());
            }

            no.add(traceObject.getSurgeonId());
        }

        XYSeriesCollection dataset = new XYSeriesCollection();

        LinkedHashMap<Integer, Double> average = new LinkedHashMap<>();

        for (Map.Entry<Integer, ArrayList<Double>> entry : wait1.entrySet()) {
            Integer integer = entry.getKey();
            ArrayList<Double> arrayList = entry.getValue();
            double value = 0;
            if (minCheck) {
                value = Collections.min(arrayList);
                value = value / 600;
            } else {
                value = Collections.max(arrayList);
                value = value / 600;
            }

            average.put(integer, value);
        }

        XYSeries series = new XYSeries("Surgeon Minimum Wait Time 1");
        for (int i = 1; i <= average.size(); i++) {
            series.add(i, average.get(i));
        }
        dataset.addSeries(series);
        String name;
        if (minCheck) {
            name = "Minimum";
        } else {
            name = "Maximum";
        }
        // Generate the graph
        JFreeChart chart = ChartFactory.createXYLineChart(
                name + " Wait Time 1 For Patients", // Title
                "Surgeon ID", // x-axis Label
                "Time (Days)", // y-axis Label
                dataset, // Dataset
                PlotOrientation.VERTICAL, // Plot Orientation
                true, // Show Legend
                true, // Use tooltips
                false // Configure chart to generate URLs?
        );

        XYPlot xyPlot = (XYPlot) chart.getPlot();

        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) xyPlot.getRenderer();
        renderer.setBaseShapesVisible(true);
        NumberAxis domain = (NumberAxis) xyPlot.getDomainAxis();
        domain.setVerticalTickLabels(true);

        return new ChartPanel(chart);
    }

    JPanel waitingQueueSurgeon() {
        String[] column = new String[l.getSurgeonQueue().size()];

        String series1 = "Surgeon Queue";
        String series2 = "Operation Queue";
        for (int i = 0; i < l.getSurgeonQueue().size(); i++) {
            column[i] = "Surgeon " + (i + 1);
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i = 1; i <= l.getSurgeonQueue().size(); i++) {
            dataset.addValue(l.getSurgeonQueue().get(i), series1, column[i - 1]);
            dataset.addValue(l.getOperationQueue().get(i), series2, column[i - 1]);
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Patient Waiting in the Queue", // chart title
                "Surgeon ID", // domain axis label
                "Number of Patients", // range axis label
                dataset, // data
                PlotOrientation.VERTICAL, // orientation
                true, // include legend
                true, // tooltips?
                false // URLs?
        );

        return new ChartPanel(chart);
    }

    JPanel waitingQueueCostAnaylsis() {
        String[] column = new String[l.getSurgeonQueue().size()];

        String series1 = "Operation Queue";
        for (int i = 0; i < l.getSurgeonQueue().size(); i++) {
            column[i] = "Surgeon " + (i + 1);
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i = 1; i <= l.getOperationQueue().size(); i++) {
            dataset.addValue(l.getOperationQueue().get(i) * 53.068, series1, column[i - 1]);
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Patient Waiting in the Queue", // chart title
                "Surgeon ID", // domain axis label
                "Cost ($)", // range axis label
                dataset, // data
                PlotOrientation.VERTICAL, // orientation
                true, // include legend
                true, // tooltips?
                false // URLs?
        );

        return new ChartPanel(chart);
    }

    public JPanel waitTime() {
        LinkedHashSet no = new LinkedHashSet();
        LinkedHashMap<Integer, ArrayList<Double>> wait1 = new LinkedHashMap<>();

        for (Map.Entry<Integer, TraceObject> entry : l.getLocalTrace().entrySet()) {
            TraceObject traceObject = entry.getValue();

            if (wait1.get(traceObject.getSurgeonId()) == null) {
                ArrayList details = new ArrayList();
                details.add(traceObject.getWaitTime1());
                details.add(traceObject.getWaitTime2());
                wait1.put(traceObject.getSurgeonId(), details);
            } else {
                wait1.get(traceObject.getSurgeonId()).add(traceObject.getWaitTime1());
                wait1.get(traceObject.getSurgeonId()).add(traceObject.getWaitTime2());
            }

            no.add(traceObject.getSurgeonId());
        }
        String[] column = new String[no.size()];

        String series1 = "Wait Time";
        for (int i = 0; i < no.size(); i++) {
            column[i] = "Surgeon " + (i + 1);
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        LinkedHashMap<Integer, Double> average = new LinkedHashMap<>();
        for (Map.Entry<Integer, ArrayList<Double>> entry : wait1.entrySet()) {
            Integer integer = entry.getKey();
            ArrayList<Double> arrayList = entry.getValue();
            double total = 0;
            for (Double double1 : arrayList) {
                total += double1;
            }
            average.put(integer, total / (arrayList.size() / 2));
        }

        for (int i = 1; i <= average.size(); i++) {
            dataset.addValue(Math.round(average.get(i) / 600), series1, column[i - 1]);
        }
        JFreeChart chart = ChartFactory.createBarChart(
                "Wait Time", // chart title
                "Surgeon ID", // domain axis label
                "Days", // range axis label
                dataset, // data
                PlotOrientation.VERTICAL, // orientation
                true, // include legend
                true, // tooltips?
                false // URLs?
        );

        return new ChartPanel(chart);
    }

    public JPanel costAnaylsis() {
        LinkedHashSet no = new LinkedHashSet();
        LinkedHashMap<Integer, ArrayList<Double>> wait1 = new LinkedHashMap<>();

        for (Map.Entry<Integer, TraceObject> entry : l.getLocalTrace().entrySet()) {
            TraceObject traceObject = entry.getValue();

            if (wait1.get(traceObject.getSurgeonId()) == null) {
                ArrayList details = new ArrayList();
                details.add(traceObject.getWaitTime1());
                details.add(traceObject.getWaitTime2());
                wait1.put(traceObject.getSurgeonId(), details);
            } else {
                wait1.get(traceObject.getSurgeonId()).add(traceObject.getWaitTime1());
                wait1.get(traceObject.getSurgeonId()).add(traceObject.getWaitTime2());
            }

            no.add(traceObject.getSurgeonId());
        }
        String[] column = new String[no.size()];

        String series1 = "Cost";
        for (int i = 0; i < no.size(); i++) {
            column[i] = "Surgeon " + (i + 1);
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int totalCost = 0;
        LinkedHashMap<Integer, Double> average = new LinkedHashMap<>();
        for (Map.Entry<Integer, ArrayList<Double>> entry : wait1.entrySet()) {
            Integer integer = entry.getKey();
            ArrayList<Double> arrayList = entry.getValue();
            double total = 0;
            for (Double double1 : arrayList) {
                total += double1;
            }
            totalCost += total * Configuration.costOfPatientWaiting;
            average.put(integer, total / 600);
        }

        for (int i = 1; i <= average.size(); i++) {
            dataset.addValue(Math.round(average.get(i) * Configuration.costOfPatientWaiting), series1, column[i - 1]);
        }
        totalCostClass = totalCost;
        JFreeChart chart = ChartFactory.createBarChart(
                "Cost", // chart title
                "Surgeon ID", // domain axis label
                "$", // range axis label
                dataset, // data
                PlotOrientation.VERTICAL, // orientation
                true, // include legend
                true, // tooltips?
                false // URLs?
        );

        return new ChartPanel(chart);
    }

    public int getTotalCost() {
        return totalCostClass;
    }

    public JPanel cost2() {

        CategoryDataset dataset = createDataset();
        JFreeChart stackedChart = ChartFactory.createStackedBarChart("Cost", "Surgeon ID", "$",
                dataset, PlotOrientation.VERTICAL, true, true, false);
        String series1 = "Cost for Actual Wait";
        String series2 = "Cost for Minimum Wait";
        String series3 = "Cost for Acceptable Wait";

        //create group 
        GroupedStackedBarRenderer renderer = new GroupedStackedBarRenderer();
        KeyToGroupMap map = new KeyToGroupMap("G1");
        map.mapKeyToGroup(series1, "G1");
        map.mapKeyToGroup(series2, "G1");
        map.mapKeyToGroup(series3, "G1");

        renderer.setSeriesToGroupMap(map);
        renderer.setItemMargin(0.03);
        
        Color red = new Color(255, 128, 0);
        Color green = new Color(0, 128, 0);
        Color yellow = new Color(165, 227, 17);
        
        renderer.setSeriesPaint(2, Color.RED); 
        renderer.setSeriesPaint(1, yellow);         
        renderer.setSeriesPaint(0, green);         

        CategoryPlot plot = (CategoryPlot) stackedChart.getPlot();
        plot.setRenderer(renderer);

        return new ChartPanel(stackedChart);
    }

    private CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        LinkedHashSet no = new LinkedHashSet();
        LinkedHashMap<Integer, ArrayList<Double>> wait1 = new LinkedHashMap<>();

        for (Map.Entry<Integer, TraceObject> entry : l.getLocalTrace().entrySet()) {
            TraceObject traceObject = entry.getValue();

            if (wait1.get(traceObject.getSurgeonId()) == null) {
                ArrayList details = new ArrayList();
                details.add(traceObject.getWaitTime1());
                details.add(traceObject.getWaitTime2());
                wait1.put(traceObject.getSurgeonId(), details);
            } else {
                wait1.get(traceObject.getSurgeonId()).add(traceObject.getWaitTime1());
                wait1.get(traceObject.getSurgeonId()).add(traceObject.getWaitTime2());
            }

            no.add(traceObject.getSurgeonId());
        }
        String[] column = new String[no.size()];

        String series1 = "Minimum Cost";
        String series2 = "Acceptable Cost";
        String series3 = "Actual Cost";        
        

        for (int i = 0; i < no.size(); i++) {
            column[i] = "Surgeon " + (i + 1);
        }

        LinkedHashMap<Integer, Double> average = new LinkedHashMap<>();
        for (Map.Entry<Integer, ArrayList<Double>> entry : wait1.entrySet()) {
            Integer integer = entry.getKey();
            ArrayList<Double> arrayList = entry.getValue();
            double total = 0;
            for (Double double1 : arrayList) {
                total += double1;
            }
            average.put(integer, total / 600);
        }

        for (int i = 1; i <= average.size(); i++) {
            int costMin = (Configuration.minimumSurgeryTime * (wait1.get(i).size() / 2) * Configuration.costOfPatientWaiting);
            int costAccept = (Configuration.acceptedSurgeryTime * (wait1.get(i).size() / 2) * Configuration.costOfPatientWaiting);
            int actualCost = (int)Math.round(average.get(i) * Configuration.costOfPatientWaiting);
            
            int x = actualCost-(costAccept+costMin);
            if(x > actualCost)
            {
                x =actualCost;
            }
            dataset.addValue(costMin, 
                    series1, column[i - 1]);
            dataset.addValue(costAccept-costMin, 
                    series2, column[i - 1]);
            dataset.addValue(x, 
                    series3, column[i - 1]);
        }

        return dataset;
    }
}