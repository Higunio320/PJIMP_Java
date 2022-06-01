package com.example.graphi;

public interface IGraphTraversalAlgorithm {

    // interface for class implementing Graph Traversal Algorithm used to determine if graph is connected

    boolean search(int start);  // function that checks if graph is connected - returns true if it does, otherwise returns false


    double getDistance(int nodeNumber);     // function that returns distance to given node (used only for drawing graph)


    double getMaxDistance();    // function that returns max distance from the starting node to another node


}
