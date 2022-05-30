package com.example.graphi;

public interface IGraphTraversalAlgorithm {

    boolean search(int start);
    double getDistance(int nodeNumber);
    double getMaxDistance();
}
