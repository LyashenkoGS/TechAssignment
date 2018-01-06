package com.lyashenkogs;

import edu.princeton.cs.introcs.In;

import java.util.HashMap;
import java.util.Map;

public class MarkedGraph extends EdgeWeightedDigraph {
  public Map<Integer, Character> labels = new HashMap<>();

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

    String toMarkedGraphviz(){
        StringBuilder sb = new StringBuilder();
        sb.append("digraph {\n");
        for (int i = 0; i < this.V(); i++) {
            Iterable<DirectedEdge> edges = this.adj(i);
            for (DirectedEdge edge : edges) {
                sb.append(labels.get(edge.from()) + "->" + labels.get(edge.to()) + "[label=" + "\"" + edge.weight() + "\",weight=\"" + edge.weight() + "\"]\n");
            }
        }
        sb.append("}");
        return sb.toString();

    }
}
