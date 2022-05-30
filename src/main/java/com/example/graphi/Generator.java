package com.example.graphi;

public class Generator {

    public static IGraph generate(int height, int width, double min, double max) {
        IGraph graph = new Graph(height, width);
        for(int i = 0; i < height * width; i++) {
            graph.addNode(generateNode(graph, i, height, width, min, max));
        }
        return graph;
    }

    private static INode generateNode(IGraph graph, int number, int height, int width, double min, double max) {
        int dimensions = height * width;
        INode node = new Node(number);
        if(number < width) {
            if(number + width < dimensions)
            node.addConnection(number + width, min + Math.random() * (max - min));
            if(number == 0) {
                if(width != 1)
                node.addConnection(1, min + Math.random() * (max- min));
            } else if(number == width - 1) {
                node.addConnection(number - 1, getExistingConnection(graph, number, number - 1));
            } else {
                if(number + 1 < height*width)
                node.addConnection(number + 1, min + Math.random() * (max - min));
                node.addConnection(number - 1, getExistingConnection(graph, number, number - 1));
            }
        } else if((number % width) == 0) {
            if(number + 1 < dimensions)
            node.addConnection(number + 1, min + Math.random() * (max - min));
            if(number == width*(height - 1)) {
                node.addConnection(number - width, getExistingConnection(graph, number, number - width));
            } else {
                if(number + width < dimensions)
                node.addConnection(number + width, min + Math.random() * (max - min));
                node.addConnection(number - width, getExistingConnection(graph, number, number - width));
            }
        } else if(number > width*(height-1)) {
            node.addConnection(number - width, getExistingConnection(graph, number, number - width));
            node.addConnection(number - 1, getExistingConnection(graph, number, number - 1));
            if(number != height*width - 1) {
                if(number + 1 < dimensions)
                node.addConnection(number + 1, min + Math.random() * (max - min));
            }
        } else if((number + 1) % width == 0) {
            if(number + width < dimensions)
            node.addConnection(number + width, min + Math.random() * (max - min));
            node.addConnection(number - 1, getExistingConnection(graph, number, number - 1));
            node.addConnection(number - width, getExistingConnection(graph, number, number - width));
        } else {
            if(number + 1 < dimensions)
            node.addConnection(number + 1, min + Math.random() * (max - min));
            if(number + width < dimensions)
            node.addConnection(number + width, min + Math.random() * (max - min));
            node.addConnection(number - 1, getExistingConnection(graph, number, number - 1));
            node.addConnection(number - width, getExistingConnection(graph, number, number - width));
        }

        return node;
    }

    private static double getExistingConnection(IGraph graph, int currentNode, int connectedNode) {
        INode node = graph.getNode(connectedNode);
        for(int i = 0; i < node.getConnectionsAmount(); i++) {
            if (node.getConnectedNode(i) == currentNode) {
                return node.getConnectionWeight(i);
            }
        }
        return 0.0;
    }

}
