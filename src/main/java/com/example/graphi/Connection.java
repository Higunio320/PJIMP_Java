package com.example.graphi;

public class Connection {

    private final int nodeNumber;
    private final double weight;

    public Connection(int nodeNumber, double weight) {
        this.nodeNumber = nodeNumber;
        this.weight = weight;
    }

    public int getNodeNumber() {
        return nodeNumber;
    }

    public double getWeight() {
        return weight;
    }
}
