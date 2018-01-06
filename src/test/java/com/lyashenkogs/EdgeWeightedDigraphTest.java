package com.lyashenkogs;

import edu.princeton.cs.introcs.In;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class EdgeWeightedDigraphTest {

    @Test
    public void toGraphviz() {
        EdgeWeightedDigraph edgeWeightedDigraph = new EdgeWeightedDigraph(new In(new File("tinyEWD.txt")));
        System.out.println(edgeWeightedDigraph);
        Assert.assertEquals("digraph {\n" +
                "0->2[label=\"0.26\",weight=\"0.26\"]\n" +
                "0->4[label=\"0.38\",weight=\"0.38\"]\n" +
                "1->3[label=\"0.29\",weight=\"0.29\"]\n" +
                "2->7[label=\"0.34\",weight=\"0.34\"]\n" +
                "3->6[label=\"0.52\",weight=\"0.52\"]\n" +
                "4->7[label=\"0.37\",weight=\"0.37\"]\n" +
                "4->5[label=\"0.35\",weight=\"0.35\"]\n" +
                "5->1[label=\"0.32\",weight=\"0.32\"]\n" +
                "5->7[label=\"0.28\",weight=\"0.28\"]\n" +
                "5->4[label=\"0.35\",weight=\"0.35\"]\n" +
                "6->4[label=\"0.93\",weight=\"0.93\"]\n" +
                "6->0[label=\"0.58\",weight=\"0.58\"]\n" +
                "6->2[label=\"0.4\",weight=\"0.4\"]\n" +
                "7->3[label=\"0.39\",weight=\"0.39\"]\n" +
                "7->5[label=\"0.28\",weight=\"0.28\"]\n" +
                "}",edgeWeightedDigraph.toGraphviz());
    }
}