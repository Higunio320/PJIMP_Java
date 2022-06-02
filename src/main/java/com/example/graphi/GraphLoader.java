package com.example.graphi;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GraphLoader {
    private File file;

    private int width;
    private int height;
    private IGraph graph;
    private double minValue;
    private double maxValue;


    public GraphLoader(File file) {
        this.file = file;
    }


    public boolean loadGraph() {

        Scanner scanner = null;
        Scanner lineNumberScanner = null;

        try {
            scanner = new Scanner(file);
            lineNumberScanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            return false;
        }

        lineNumberScanner.nextLine();

        if (scanner.hasNextInt()) {
            height = scanner.nextInt();
        } else {
            return false;
        }
        if (scanner.hasNextInt()) {
            width = scanner.nextInt();
        } else {
            return false;
        }

        graph = new Graph(height, width);

        int vertexNum = 0;
        int lineCounter = 0;
        minValue = Double.MAX_VALUE;
        maxValue = 0.0;

        while (lineNumberScanner.hasNextLine()) {

            graph.addNode(new Node(lineCounter));
            String currentLine = lineNumberScanner.nextLine();
            scanner = new Scanner(currentLine);

            while (scanner.hasNextInt()) {
                int checkedVertex = scanner.nextInt();
                double weightValue;
                String weightString = scanner.next();

                if (weightString.charAt(0) != ':') {
                    return false;
                }
                try {
                    weightValue = Double.parseDouble(weightString.substring(1, weightString.length()));
                } catch (Exception e) {
                    return false;
                }
               graph.getNode(lineCounter).addConnection(checkedVertex, weightValue);

                if(weightValue < minValue)
                    minValue = weightValue;
                if(weightValue > maxValue)
                    maxValue = weightValue;
            }
            vertexNum++;
            lineCounter++;

        }

        scanner.close();
        lineNumberScanner.close();

        return true;
    }

    public IGraph getGraph() {
        return graph;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public double getMinValue() {
        return minValue;
    }
}
