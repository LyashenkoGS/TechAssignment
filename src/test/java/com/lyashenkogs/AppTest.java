package com.lyashenkogs;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class AppTest {

    @Test
    public void stringRepresentation() {
        AdjMatrixGraph adjMatrixGraph = new AdjMatrixGraph(3);
        adjMatrixGraph.addEdge(0, 1);
        adjMatrixGraph.addEdge(1, 2);
        adjMatrixGraph.addEdge(2, 0);
        assertEquals("adjacency list:\n" +
                "0: 1 2 \n" +
                "1: 0 2 \n" +
                "2: 0 1 \n", adjMatrixGraph.adjacencyList());
        assertEquals("adjacency matrix:\n" +
                "   0 1 2 \n" +
                "0: 0 1 1 \n" +
                "1: 1 0 1 \n" +
                "2: 1 1 0 \n", adjMatrixGraph.adjacencyMatrix());
    }

}
