package com.example.graphi;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class GraphDrawer implements IGraphDrawer {

    private final IGraph graph;
    private final Circle[] nodes;
    private final Group nodeGroup;
    private final Group edgesGroup;

    public GraphDrawer(Circle[] nodes, IGraph graph) {
        this.graph = graph;
        this.nodes = nodes;
        this.nodeGroup = new Group();
        this.edgesGroup = new Group();
    }

    @Override
    public void drawGraph(double min, double max, double scaledLength) {
        if(graph == null)
            return;
        double weight;
        int width = graph.getWidth();
        int height = graph.getHeight();
        int nodeNumber = width*height;
        int i = 0;
        int x = 0;
        int y = 0;
        double pathWidth = scaledLength / 5;
        Rectangle tmp;
        while(i < nodeNumber) {
            if(y % 2 == 0) {
                nodes[i].setCenterX(scaledLength*(2*x + 1));
                nodes[i].setCenterY(scaledLength*(2*y + 1));
                nodeGroup.getChildren().add(nodes[i]);
                i++;
                if(i % width == 0) {
                    y++;
                    x = 0;
                    continue;
                }
                x++;
                weight = findConnectionWeight(i-1, i);
                if(weight != -1.0) {
                    tmp = new Rectangle(scaledLength*(2 * x), scaledLength*(2 * y + 1) - pathWidth/2 , 2*scaledLength, pathWidth);
                    tmp.setFill(Color.hsb(250 * (max - weight / (max - min)), 1, 1));
                    edgesGroup.getChildren().add(tmp);
                } else {
                    weight = findConnectionWeight(i, i-1);
                    if(weight != -1.0) {
                        tmp = new Rectangle(scaledLength*(2 * x), scaledLength*(2 * y + 1) - pathWidth/2 , 2*scaledLength, pathWidth);
                        tmp.setFill(Color.hsb(250 * (max - weight / (max - min)), 1, 1));
                        edgesGroup.getChildren().add(tmp);
                    }
                }
                x++;
            } else {
                for(int j = 0; j < width; j++) {
                    weight = findConnectionWeight(i - width + j, i + j);
                    if(weight != -1.0) {
                        tmp = new Rectangle(scaledLength*(2*x + 1) - pathWidth/2, (scaledLength*(2*y)), pathWidth, 2*scaledLength);
                        tmp.setFill(Color.hsb(250 * (max - weight)/ (max - min), 1 ,1));
                        edgesGroup.getChildren().add(tmp);
                    } else {
                        weight = findConnectionWeight(i + j, i - width + j);
                        if(weight != -1.0) {
                            tmp = new Rectangle(scaledLength*(2*x + 1) - pathWidth/2, (scaledLength*(2*y)), pathWidth, 2*scaledLength);
                            tmp.setFill(Color.hsb(250 * (max - weight)/ (max - min), 1 ,1));
                            edgesGroup.getChildren().add(tmp);
                        }
                    }
                    x+= 2;
                }
                x = 0;
                y++;
            }
        }
    }


    @Override
    public Group getNodeGroup() {
        return nodeGroup;
    }

    @Override
    public Group getEdgeGroup() {
        return edgesGroup;
    }

    private double findConnectionWeight(int currentNode, int connectedNode) {
        INode node = graph.getNode(currentNode);
        for(int i = 0; i < node.getConnectionsAmount(); i++) {
            if(node.getConnectedNode(i) == connectedNode) {
                return node.getConnectionWeight(i);
            }
        }
        return -1.0;
    }
}
