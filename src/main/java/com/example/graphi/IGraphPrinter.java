package com.example.graphi;

public interface IGraphPrinter {

    static void printGraph(IGraph graph) {
        int i, j;
        int width = graph.getWidth();
        int height = graph.getHeight();
        int nodeNumber = height * width;
        for(i = 0; i < nodeNumber - 1; i++) {
            System.out.printf("%02d", i);
            if((i + 1) % width == 0) {
                System.out.println();
                if(graph.getNode(i - width + 1).getConnectionWeight(1) > 0) {
                    System.out.print("|");
                } else {
                    System.out.print(" ");
                }
                for(j = 1; j < width; j++) {
                    if(graph.getNode(i - width + j + 1).getConnectionWeight(1) > 0) {
                        System.out.print("    |");
                    } else {
                        System.out.print("     ");
                    }
                }
                System.out.println();
            } else {
                if(graph.getNode(i).getConnectionWeight(0) > 0) {
                    System.out.print(" - ");
                } else {
                    System.out.print("   ");
                }
            }
        }
        System.out.printf("%02d", nodeNumber - 1);
    }

    static void printGraphInfo(IGraph graph) {
        int nodeNumber = graph.getWidth()*graph.getHeight();
        int connections;
        for(int i = 0; i < nodeNumber; i++) {
            System.out.println("Wierzchołek: " + i);
            INode currentNode = graph.getNode(i);
            connections = currentNode.getConnectionsAmount();
            System.out.println("Ilość połączeń: " + connections);
            for(int j = 0; j < connections; j++) {
                System.out.print("Połączony: " + currentNode.getConnectedNode(j));
                System.out.println("  Waga: " + currentNode.getConnectionWeight(j));
            }
            System.out.println("Stan: " + currentNode.getState());
            System.out.println("Poprzednik: " + currentNode.getPredecessor());
            System.out.println();
        }
    }
}
