package com.example.graphi;

import java.util.ArrayList;
import java.util.List;

public class Bfs implements IGraphTraversalAlgorithm {

    private final IGraph graph;
    int visited;
    private final double[] distances;
    private double maxDistance;
    private final IQueue queue;

    private class Heap implements IQueue {

        private final List<Integer> queue;

        public Heap() {
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

    public Bfs(IGraph graph) {
        this.queue = new Heap();
        this.graph = graph;
        this.distances = new double[this.graph.getWidth()*this.graph.getHeight()];
    }

    public boolean search(int start) {
        visited = 0;
        int width = this.graph.getWidth();
        int height = this.graph.getHeight();
        int nodeNumber = width * height;
        int nextNode;
        for (int i = 0; i < nodeNumber; i++) {
            graph.getNode(i).setState(0);
        }
        distances[start] = 0.0;
        INode currentNode = graph.getNode(start);
        currentNode.setPredecessor(start);
        currentNode.setState(1);
        queue.push(start);
        visited++;
        while (queue.getSize() != 0) {
            currentNode = graph.getNode(queue.pull());
            currentNode.setState(2);
            for (int i = 0; i < currentNode.getConnectionsAmount(); i++) {
                nextNode = currentNode.getConnectedNode(i);
                pushNode(currentNode.getNodeNumber(), nextNode);
            }
            if (queue.getSize() == 0) {
                this.maxDistance = distances[currentNode.getNodeNumber()];
            }
        }
        return visited == nodeNumber;
    }


    private void pushNode(int currentNode, int nextNode) {
        double currentDistance;
        double possibleDistance;
        int state = graph.getNode(nextNode).getState();
        if(state == 0) {
            distances[nextNode] = distances[currentNode] + 1.0;
            queue.push(nextNode);
            graph.getNode(nextNode).setState(1);
            graph.getNode(nextNode).setPredecessor(currentNode);
            visited++;
        } else if (state == 1) {
            currentDistance = distances[nextNode];
            possibleDistance = distances[currentNode] + 1.0;
            if(possibleDistance < currentDistance) {
                distances[nextNode] = possibleDistance;
                queue.move(nextNode);
                graph.getNode(nextNode).setPredecessor(currentNode);
            }
        }
    }

    @Override
    public double getMaxDistance() {
        return maxDistance;
    }

    @Override
    public double getDistance(int nodeNumber) {
        return distances[nodeNumber];
    }


}
