package com.example.graphi;

public interface IGraph {

    // interface for graph storing nodes and providing access to them

    int getHeight();
    int getWidth();     // both return height or width of graph

    INode getNode(int number);  // returns INode with given index

    void addNode(INode node);   // adds given node to graph

}
