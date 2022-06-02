package com.example.graphi;

import java.util.ArrayList;
import java.util.List;

public class Dijkstra implements IShortestPathAlgorithm {

    private final double[] distances;       // array for distances, distances[0] is distance from root to node 0, distances [1] is distance from root to node 1 etc.
    private final IQueue queue;     // queue holding actual node indexes (position of the node with the lowest distance must always be known)
    private final IGraph graph;
    private double maxDistance;


    private class DijkstraHeap implements IQueue {

        private final List<Integer> queue;

        public DijkstraHeap() {
            this.queue = new ArrayList<>();
        }

        @Override
        public void push(int nodeNumber) {
            double nodeDistance = distances[nodeNumber];
            int i = queue.size();
            int j;
            queue.add(nodeNumber);
            j = (i - 1) / 2;
            while(i > 0 && distances[queue.get(j)] > nodeDistance) {
                queue.set(i, queue.get(j));
                i = j;
                j = (i - 1) / 2;
            }
            queue.set(i, nodeNumber);
        }

        @Override
        public int pull() {
            int returnInt = queue.get(0);
            int v = queue.get(queue.size() - 1);
            queue.remove(queue.size() - 1);
            if(queue.size() == 0)
                return returnInt;
            int i = 0;
            int j = 1;
            while(j < queue.size()) {
                if(j + 1 < queue.size() && distances[queue.get(j)] > distances[queue.get(j + 1)])
                    j++;
                if(distances[v] <= distances[queue.get(j)])
                    break;
                queue.set(i, queue.get(j));
                i = j;
                j = 2*j + 1;
            }
            queue.set(i, v);
            return returnInt;
        }

        @Override
        public int getSize() {
            return queue.size();
        }

        @Override
        public void move(int nodeNumber) {
            int index = 0;
            for(int i = (queue.size() - 1); i >= 0; i--) {
                if(queue.get(i) == nodeNumber) {
                    index = i;
                    break;
                }
            }
            if(index == 0) {
                if(queue.get(0) != nodeNumber) {
                    return;
                }
            }
            int j = (index - 1) / 2;
            while(index > 0 && distances[queue.get(j)] > distances[queue.get(index)]) {
                queue.set(index, queue.get(j));
                index = j;
                j = (index - 1) / 2;
            }
            queue.set(index, nodeNumber);
        }
    }

    public Dijkstra(IGraph graph) {
        this.distances = new double[graph.getHeight()*graph.getWidth()];
        this.queue = new DijkstraHeap();
        this.graph = graph;

    }

    @Override
    public void calculate(int start) {
        for(int i = 0; i < graph.getHeight()*graph.getWidth(); i++) {
            graph.getNode(i).setState(0);
        }
        distances[start] = 0.0;
        INode currentNode = graph.getNode(start);
        currentNode.setState(3);
        currentNode.setPredecessor(start);
        int nextNode;
        double currentDistance;
        double possibleDistance;
        for(int i = 0; i < currentNode.getConnectionsAmount(); i++) {
            nextNode = currentNode.getConnectedNode(i);
            distances[nextNode] = currentNode.getConnectionWeight(i);
            queue.push(nextNode);
            graph.getNode(nextNode).setState(2);
            graph.getNode(nextNode).setPredecessor(start);
        }
        while(queue.getSize() != 0) {
            currentNode = graph.getNode(queue.pull());
            currentNode.setState(3);
            for(int i = 0; i < currentNode.getConnectionsAmount(); i++) {
                nextNode = (currentNode.getConnectedNode(i));
                if(graph.getNode(nextNode).getState() < 2) {
                    distances[nextNode] = distances[currentNode.getNodeNumber()] + currentNode.getConnectionWeight(i);
                    queue.push(nextNode);
                    graph.getNode(nextNode).setState(2);
                    graph.getNode(nextNode).setPredecessor(currentNode.getNodeNumber());
                } else if (graph.getNode(nextNode).getState() == 2) {
                    currentDistance = distances[nextNode];
                    possibleDistance = distances[currentNode.getNodeNumber()] + currentNode.getConnectionWeight(i);
                    if(possibleDistance < currentDistance) {
                        distances[nextNode] = possibleDistance;
                        queue.move(nextNode);
                        graph.getNode(nextNode).setPredecessor(currentNode.getNodeNumber());
                    }
                }
                if(queue.getSize() == 0) {
                    this.maxDistance = distances[currentNode.getNodeNumber()];
                }
            }
        }
    }

    public double getDistance(int nodeNumber) {
        return distances[nodeNumber];
    }

    @Override
    public double getMaxDistance() {
        return maxDistance;
    }


}
