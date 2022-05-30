package com.example.graphi;

import java.util.ArrayList;

public class Graph implements IGraph{

    private final int height;
    private final int width;
    private final ArrayList<INode> nodes;

    public Graph(int height, int width) {
        this.height = height;
        this.width = width;
        nodes = new ArrayList<>();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public INode getNode(int number) {
        return nodes.get(number);
    }

    public void addNode(INode node) {
        nodes.add(node);
    }

}
