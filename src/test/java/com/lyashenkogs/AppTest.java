package com.lyashenkogs;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;


public class AppTest {
    private AdjMatrixGraph adjMatrixGraph = new AdjMatrixGraph(3);

    @Before
    public void setUp() {
        adjMatrixGraph.addEdge(0, 1);
        adjMatrixGraph.addEdge(1, 2);
        adjMatrixGraph.addEdge(2, 0);
    }

    @Test
    public void adjacancyListAsString() {
        assertEquals("adjacency list:\n" +
                "0: 1 2 \n" +
                "1: 0 2 \n" +
                "2: 0 1 \n", adjMatrixGraph.adjacencyList());
    }

    @Test
    public void adjacencyMatrixAsString() {
        assertEquals("adjacency matrix:\n" +
                "   0 1 2 \n" +
                "0: 0 1 1 \n" +
                "1: 1 0 1 \n" +
                "2: 1 1 0 \n", adjMatrixGraph.adjacencyMatrix());
    }

    @Test
    public void graphvizRepresentation() {
        assertEquals("strict graph G {\n" +
                "0 -- 1;\n" +
                "0 -- 2;\n" +
                "1 -- 0;\n" +
                "1 -- 2;\n" +
                "2 -- 0;\n" +
                "2 -- 1;\n" +
                "}", adjMatrixGraph.graphvizRepresentaion());
    }

    @Test
    public void drawWindow() throws IOException, InterruptedException {
        App.drawWindow(new File("example/ex1.png"));
        Thread.sleep(2_000);
    }
}
