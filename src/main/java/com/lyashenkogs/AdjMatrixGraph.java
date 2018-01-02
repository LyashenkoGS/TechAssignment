package com.lyashenkogs;


import edu.princeton.cs.introcs.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A undirected graph, implemented using an adjacency matrix.<br>
 * Parallel edges are disallowed; self-loops are allowed.<br>
 * <p>
 * based on <a href="https://algs4.cs.princeton.edu/41graph/AdjMatrixGraph.java.html">https://algs4.cs.princeton.edu/41graph/AdjMatrixGraph.java.html</a>
 */
public class AdjMatrixGraph {
    private static final String NEWLINE = System.getProperty("line.separator");
    private int V;
    private int E;
    private boolean[][] adjacencyMatrix;

    /**
     * empty graph with V vertices
     *
     * @param V number of vertices
     */
    public AdjMatrixGraph(int V) {
        if (V < 0) throw new RuntimeException("Number of vertices must be nonnegative");
        this.V = V;
        this.E = 0;
        this.adjacencyMatrix = new boolean[V][V];
    }

    /**
     * random graph with V vertices and E edges
     *
     * @param V number of vertices
     * @param E number of edges
     */
    public AdjMatrixGraph(int V, int E) {
        this(V);
        if (E < 0) throw new RuntimeException("Number of edges must be nonnegative");
        if (E > V * (V - 1) + V) throw new RuntimeException("Too many edges");

        // can be inefficient
        while (this.E != E) {
            int v = StdRandom.uniform(V);
            int w = StdRandom.uniform(V);
            addEdge(v, w);
        }
    }

    public boolean[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    // number of vertices and edges
    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    // add undirected edge v-w
    public void addEdge(int v, int w) {
        if (!adjacencyMatrix[v][w]) E++;
        adjacencyMatrix[v][w] = true;
        adjacencyMatrix[w][v] = true;
    }

    // does the graph contain the edge v-w?
    public boolean contains(int v, int w) {
        return adjacencyMatrix[v][w];
    }

    // return list of neighbors of v
    public Iterable<Integer> adj(int v) {
        return new AdjIterator(v);
    }

    /**
     * For each vertex it prints a list of vertices the vertex is connected to.<br>
     * Example: <br>
     * 0: 1 2 <br>
     * 1: 0 2 <br>
     * 2: 0 1 <br>
     *
     * @return representation of the graph as an adjacency list.
     */
    public String adjacencyList() {
        StringBuilder s = new StringBuilder();
        s.append("adjacency list:\n");
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (int w : adj(v)) {
                s.append(w + " ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    /**
     * Example: <br>
     * ** 0 1 2 <br>
     * 0: 0 1 1<br>
     * 1: 1 0 1<br>
     * 2: 1 1 0<br>
     *
     * @return representation of the graph as an adjacency matrix.
     */
    public String adjacencyMatrix() {
        StringBuilder s = new StringBuilder();
        s.append("adjacency matrix:\n");
        //create space for vertical indexes
        int numberOfDigits = String.valueOf(adjacencyMatrix.length).length() + 2;
        for (int i = 0; i < numberOfDigits; i++) {
            s.append(" ");
        }
        //print second array indexes(we assume that the matrix is square)
        for (int i = 0; i < adjacencyMatrix[0].length; i++) {
            s.append(i + " ");
        }
        s.append("\n");
        //print vertical index and all values
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            boolean[] matrix = adjacencyMatrix[i];
            s.append(i + ": ");
            for (boolean aMatrix : matrix) {
                if (aMatrix) {
                    s.append(1 + " ");
                } else {
                    s.append(0 + " ");
                }
            }
            s.append("\n");
        }
        return s.toString();
    }

    // support iteration over graph vertices
    private class AdjIterator implements Iterator<Integer>, Iterable<Integer> {
        private int v;
        private int w = 0;

        AdjIterator(int v) {
            this.v = v;
        }

        public Iterator<Integer> iterator() {
            return this;
        }

        public boolean hasNext() {
            while (w < V) {
                if (adjacencyMatrix[v][w]) return true;
                w++;
            }
            return false;
        }

        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return w++;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}