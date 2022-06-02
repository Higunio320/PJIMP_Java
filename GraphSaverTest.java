package com.example.graphi;


import org.junit.Assert;
import org.junit.Test;

public class GraphSaverTest {

    @Test
    public void nullGraphTest() {
        GraphSaver saver = new GraphSaver(null, null);
        Assert.assertFalse(saver.saveGraph());
    }

}