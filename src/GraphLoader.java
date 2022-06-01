//package pl.jimp.pathfinder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GraphLoader {
    private String inputFileName;

    private int width;
    private int height;
    private Graph graph;


    public GraphLoader(String inputFileName) {
        this.inputFileName = inputFileName;
    }


    public InfoLabel loadGraph() {
        String inputFilePath = "src/main/resources/pl/jimp/data/" + inputFileName;
        File inputFile = new File(inputFilePath);

        Scanner scanner = null;
        Scanner lineNumberScanner = null;

        try {
            scanner = new Scanner(inputFile);
            lineNumberScanner = new Scanner(inputFile);
        } catch (FileNotFoundException e) {
            return new InfoLabel("Nie mozna zaladowac grafu z  '" + inputFileName + " Plik nie znaleziony. ", InfoLabelSource.LOAD, true);
        }

        lineNumberScanner.nextLine();

        if (scanner.hasNextInt()) {
            height = scanner.nextInt();
        } else {
            return new InfoLabel("Bledny format danych, nie mozna zaladowac ilosci wierszy.", InfoLabelSource.LOAD, true);
        }
        if (scanner.hasNextInt()) {
            width = scanner.nextInt();
        } else {
            return new InfoLabel("Bledny format danych, nie mozna zaladowac ilosci kolumn.", InfoLabelSource.LOAD, true);
        }

        graph = new Graph(width, height);

        int vertexNum = 0;

        while (lineNumberScanner.hasNextLine()) {

            String currentLine = lineNumberScanner.nextLine();
            scanner = new Scanner(currentLine);

            while (scanner.hasNextInt()) {
                int checkedVertex = scanner.nextInt();
                double weightValue;
                String weightString;

                weightString = scanner.next();

                if (weightString.charAt(0) != ':') {
                    return new InfoLabel("Bledny format danych, brak separatora ':'." + inputFileName +"' line no: " + (vertexNum+2),
                            InfoLabelSource.LOAD, true);
                }
                weightValue = Double.parseDouble(weightString.substring(1, weightString.length()));

                if (checkedVertex == (vertexNum - height)) {
                    graph.getNodeNumber().get(vertexNum).addConnection(0, weightValue);
                }
                if (checkedVertex == (vertexNum + height)) {
                    graph.getNodeNumber().get(vertexNum).addConnection(1, weightValue);
                }
                if (checkedVertex == (vertexNum + 1)) {
                    graph.getNodeNumber().get(vertexNum).addConnection(2, weightValue);
                }
                if (checkedVertex == (vertexNum - 1)) {
                    graph.getNodeNumber().get(vertexNum).addConnection(3, weightValue);
                }


            }
            vertexNum++;

        }

        scanner.close();
        lineNumberScanner.close();

        return new InfoLabel("Zaladowano graf z  " + inputFileName, InfoLabelSource.LOAD, false);
    }

    public Graph getGraph() {
        return graph;
    }
}
