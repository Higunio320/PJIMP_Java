package com.example.graphi;

import org.junit.Assert;
import org.junit.Test;

public class BfsTest {

    @Test
    public void testSearch() {
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
        IGraphTraversalAlgorithm bfs = new Bfs(graph);
        Assert.assertTrue(bfs.search(0));
    }

}