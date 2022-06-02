package com.example.graphi;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GraphSaver {
    private IGraph graph;
    private File file;

    public GraphSaver(IGraph graph, File file) {
        this.graph = graph;
        this.file = file;
    }

    public boolean saveGraph() {
        if (graph == null)
            return false;
        INode node;
            try {
                FileWriter fileWriter = new FileWriter(this.file, false);
                PrintWriter printWriter = new PrintWriter(fileWriter);

                printWriter.println("\t" + graph.getHeight() + " " + graph.getWidth());
                for (int vertexIndex = 0; vertexIndex < graph.getHeight() * graph.getWidth(); vertexIndex++) {
                    printWriter.print("\t");
                    node = graph.getNode(vertexIndex);
                    for (int weightIndex = 0; weightIndex < node.getConnectionsAmount(); weightIndex++) {
                        if (node.getConnectionWeight(weightIndex) != -1) {
                            printWriter.print(node.getConnectedNode(weightIndex) + " :" + node.getConnectionWeight(weightIndex));
                            printWriter.print("\t");
                        }
                    }
                    printWriter.print("\n");
                }


                printWriter.close();
                fileWriter.close();

            } catch (IOException e) {
                return false;
            }
        return true;
    }

}
