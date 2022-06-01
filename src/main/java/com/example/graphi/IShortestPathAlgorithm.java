package com.example.graphi;

public interface IShortestPathAlgorithm {

    // interface for class implementing Dijkstra's Algorithm and storing distances from root to every node

    void calculate(int start);           // function that calculates every node's distance from root
    double getDistance(int nodeNumber); // returns distance from root of node with number nodeNumber;
    double getMaxDistance();            // function that returns max distance from starting node to another node
}
