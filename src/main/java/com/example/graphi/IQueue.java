package com.example.graphi;

public interface IQueue {

    // interface for queue used by Dijkstra's algorithm to store node numbers

    void push(int nodeNumber);  // function for pushing node number into queue
    int pull();                 // return node number (from queue) with the lowest distance from root
    int getSize();              // returns actual size fo the queue
    void move(int nodeNumber);  // function for moving node in the queue (when distance to it has been reduced by Dijkstra algorithm)
}
