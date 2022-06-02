package com.example.graphi;

import java.util.ArrayList;
import java.util.List;

public class Node implements INode {

    private final int nodeNumber;
    private final List<Connection> connections;
    private int state;
    private int predecessor;

    public Node(int number) {
        this.nodeNumber = number;
        this.connections = new ArrayList<>();
        this.state = 0;
        this.predecessor = -1;
    }

    @Override
    public int getNodeNumber() {
        return this.nodeNumber;
    }

    @Override
    public void addConnection(int node, double weight) {
        connections.add(new Connection(node, weight));
    }

    @Override
    public void setState(int state) {
        this.state = state;
    }

    @Override
    public double getConnectionWeight(int number) {
        return connections.get(number).getWeight();
    }

    @Override
    public int getState() {
        return this.state;
    }

    @Override
    public int getPredecessor() {
        return this.predecessor;
    }

    @Override
    public int getConnectedNode(int number) {
        return connections.get(number).getNodeNumber();
    }

    @Override
    public int getConnectionsAmount() {
        return connections.size();
    }

    @Override
    public void setPredecessor(int predecessor) {
        this.predecessor = predecessor;
    }


}
