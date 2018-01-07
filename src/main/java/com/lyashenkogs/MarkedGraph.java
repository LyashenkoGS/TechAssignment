package com.lyashenkogs;

import edu.princeton.cs.introcs.In;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarkedGraph extends EdgeWeightedDigraph {
    public Map<Character, Integer> labelToIndex = new HashMap<>();
    public Map<Integer, Character> indexToLabel = new HashMap<>();

    public MarkedGraph(int V) {
        super(V);
    }

    public MarkedGraph(int V, int E) {
        super(V, E);
    }

    public MarkedGraph(In in) {
        super(in);
    }

    public MarkedGraph(EdgeWeightedDigraph G) {
        super(G);
    }

    String toMarkedGraphviz() {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph {\n");
        for (int i = 0; i < this.V(); i++) {
            Iterable<DirectedEdge> edges = this.adj(i);
            for (DirectedEdge edge : edges) {
                sb.append(indexToLabel.get(edge.from()) + "->" + indexToLabel.get(edge.to()) + "[label=" + "\"" + edge.weight() + "\",weight=\"" + edge.weight() + "\"]\n");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    public double pathLength(List<Character> points) {
        double path = 0;
        //iterate over a path
        Bag<DirectedEdge>[] graph = this.adj;
        //iterate until the element before the last element
        for (int i = 0; i < points.size() - 1; i++) {
            //get edges by a label
            Integer index = this.labelToIndex.get(points.get(i));
            Bag<DirectedEdge> edges = graph[index];
            //if no path raise an exception
            boolean hasPath = false;
            for (DirectedEdge edge : edges) {
                if (edge.to() == this.labelToIndex.get(points.get(i + 1))) {
                    path += edge.weight();
                    hasPath = true;
                }
            }
            if (!hasPath) {
                throw new RuntimeException("NO SUCH ROUTE");
            }
        }
        return path;
    }
}
