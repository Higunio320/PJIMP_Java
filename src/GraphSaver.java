package pl.jimp.pathfinder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GraphSaver {
    Graph graph;
    String outputFileName;

    public GraphSaver(Graph graph, String outputFileName) {
        this.graph = graph;
        this.outputFileName = outputFileName;
    }

    public InfoLabel saveGraph() {
        if(graph == null){
            return new InfoLabel("Nie zaladowano grafu.", InfoLabelSource.SAVE, true);
        }
        String outputFilePath = "src/main/resources/pl/jimp/pathfinder/data/" + outputFileName; // sciezka do zmienienia
        File outputFile = new File(outputFilePath);

        if (!outputFile.exists()) {
            try {
                outputFile.createNewFile();
            } catch (IOException e) {
                return new InfoLabel("NIe mozna stworzyc pliku '" + outputFileName +"'.", InfoLabelSource.SAVE, true);
            }
        }


        try {
            FileWriter fileWriter = new FileWriter(outputFilePath, false);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println(graph.getHeight() + " " + graph.getWidth());
            for (int vertexIndex = 0; vertexIndex < graph.getHeight() * graph.getWidth(); vertexIndex++) {
                printWriter.print("\t");
                for (int weightIndex = 0; weightIndex < 4; weightIndex++) {
                    if (graph.getNodeNumber().get(vertexIndex).getWeights().get(weightIndex) != 0) {
                        switch (weightIndex) {
                            case 0 -> printWriter.print((vertexIndex - graph.getWidth()) + " :" + graph.getNodeNumber().get(vertexIndex).getWeight().get(0));
                            case 1 -> printWriter.print((vertexIndex + graph.getWidth()) + " :" + graph.getNodeNumber().get(vertexIndex).getWeight().get(1));
                            case 2 -> printWriter.print((vertexIndex + 1) + " :" + graph.getNodeNumber().get(vertexIndex).getWeight().get(2));
                            case 3 -> printWriter.print((vertexIndex - 1) + " :" + graph.getNodeNumber().get(vertexIndex).getWeight().get(3));
                        }
                        printWriter.print(" ");
                    }
                }
                printWriter.print("\n");
            }


            printWriter.close();
            fileWriter.close();

        } catch (IOException e) {
            return new InfoLabel("Nie mozna zapisac pliku '" + outputFileName + "'.", InfoLabelSource.SAVE, true);
        }


        return new InfoLabel("Graph successfully saved to '" + outputFileName + "'.", InfoLabelSource.SAVE, false);
    }
}
