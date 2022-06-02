package com.example.graphi;

import org.junit.Assert;
import org.junit.Test;

public class NodeTest {

    @Test
    public void connectionsTest() {
        INode node = new Node(0);
        node.addConnection(1, 5.0);
        node.addConnection(2, 3.0);
        node.addConnection(25, 49.0);
        Assert.assertEquals(node.getConnectionsAmount(), 3);
        Assert.assertEquals(node.getConnectedNode(0), 1);
        Assert.assertEquals(node.getConnectedNode(1), 2);
        Assert.assertEquals(node.getConnectedNode(2), 25);
        Assert.assertEquals(node.getConnectionWeight(0), 5.0, 0.1);
        Assert.assertEquals(node.getConnectionWeight(1), 3.0, 0.1);
        Assert.assertEquals(node.getConnectionWeight(2), 49.0, 0.1);
    }

}