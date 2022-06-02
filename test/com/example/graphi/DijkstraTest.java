package com.example.graphi;

import org.junit.Assert;
import org.junit.Test;

public class DijkstraTest {

    @Test
    public void dijkstraTest() {
        IGraph graph = new Graph(2, 3);
        INode node = new Node(0);
        node.addConnection(1, 1.0);
        node.addConnection(3, 2.5);
        graph.addNode(node);
        node = new Node(1);
        node.addConnection(0, 1.0);
        node.addConnection(2, 2.0);
        node.addConnection(4, 4.0);
        graph.addNode(node);
        node = new Node(2);
        node.addConnection(1, 2.0);
        node.addConnection(5, 3.0);
        graph.addNode(node);
        node = new Node(3);
        node.addConnection(0, 2.5);
        node.addConnection(4, 3.5);
        graph.addNode(node);
        node = new Node(4);
        node.addConnection(3, 3.5);
        node.addConnection(1, 4.0);
        node.addConnection(5, 3.5);
        graph.addNode(node);
        node = new Node(5);
        node.addConnection(4, 3.5);
        node.addConnection(2, 3.0);
        graph.addNode(node);
        IShortestPathAlgorithm dijkstra = new Dijkstra(graph);
        dijkstra.calculate(0);
        Assert.assertEquals(dijkstra.getDistance(0), 0.0, 0.1);
        Assert.assertEquals(dijkstra.getDistance(2), 3.0, 0.1);
        Assert.assertEquals(dijkstra.getDistance(5), 6.0, 0.1);
        Assert.assertEquals(dijkstra.getDistance(4), 5.0, 0.1);
        Assert.assertEquals(dijkstra.getMaxDistance(), 6.0, 0.1);
    }

    @Test
    public void dijkstraPredecessorsTest() {
        IGraph graph = new Graph(2, 3);
        INode node = new Node(0);
        node.addConnection(1, 1.0);
        node.addConnection(3, 2.5);
        graph.addNode(node);
        node = new Node(1);
        node.addConnection(0, 1.0);
        node.addConnection(2, 2.0);
        node.addConnection(4, 4.0);
        graph.addNode(node);
        node = new Node(2);
        node.addConnection(1, 2.0);
        node.addConnection(5, 3.0);
        graph.addNode(node);
        node = new Node(3);
        node.addConnection(0, 2.5);
        node.addConnection(4, 3.5);
        graph.addNode(node);
        node = new Node(4);
        node.addConnection(3, 3.5);
        node.addConnection(1, 4.0);
        node.addConnection(5, 3.5);
        graph.addNode(node);
        node = new Node(5);
        node.addConnection(4, 3.5);
        node.addConnection(2, 3.0);
        graph.addNode(node);
        IShortestPathAlgorithm dijkstra = new Dijkstra(graph);
        dijkstra.calculate(0);
        Assert.assertEquals(graph.getNode(0).getPredecessor(), 0);
        Assert.assertEquals(graph.getNode(5).getPredecessor(), 2);
        Assert.assertEquals(graph.getNode(4).getPredecessor(), 1);
    }
}