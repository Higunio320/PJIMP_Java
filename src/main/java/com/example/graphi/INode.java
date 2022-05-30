package com.example.graphi;

public interface INode {

    // interface for object Node which stores and provides access to all information about specific node

    int getNodeNumber();                            // returns number (index) of this node


    void addConnection(int node, double weight);    // adds connection from this node to node with given index with given path weight


    void setState(int state);                       // sets state of node
                                                    // 0 - default
                                                    // 1 - this node has been visited (taken into consideration) by an algorithm
                                                    // 2 - the shortest path to this node has been calculated by an algorithm


    int getConnectedNode(int number);               // returns index of connected node (from connection with given number)


    double getConnectionWeight(int number);         // returns weight of connection (from connection with given number)


    int getConnectionsAmount();                     // returns amount of connections from this node


    int getState();                                 // returns actual state


    int getPredecessor();                           // returns preceding node (set by an algorithm)


    void setPredecessor(int predecessor);           // sets preceding node



}
