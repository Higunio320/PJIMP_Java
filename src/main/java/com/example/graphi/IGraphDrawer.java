package com.example.graphi;

import javafx.scene.Group;

public interface IGraphDrawer {

    void drawGraph(double min, double max, double scaledLength);

    Group getNodeGroup();

    Group getEdgeGroup();

}
