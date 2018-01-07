package com.lyashenkogs;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class MarkedGraphTest {
    private MarkedGraph edgeWeightedDigraph;

    @Before
    public void parseInput() {
        String input = "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7";
        String[] tokens = input.split(", ");
        System.out.println(Arrays.toString(tokens));
        //use ascii codes
        //calculate number of vertices
        Set<Character> vertices = new HashSet<>();
        for (String token : tokens) {
            char[] chars = token.toCharArray();
            vertices.add(chars[0]);
            vertices.add(chars[1]);
        }
        Map<Character, Integer> verticesMap = new HashMap<>();
        //create maps for labels
        int counter = 0;
        for (Character vertex : vertices) {
            verticesMap.put(vertex, counter);
            counter++;
        }
        edgeWeightedDigraph = new MarkedGraph(vertices.size());
        edgeWeightedDigraph.labelToIndex = verticesMap;
        edgeWeightedDigraph.indexToLabel = verticesMap.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
        //propagate vertices and edges
        for (String token : tokens) {
            char[] chars = token.toCharArray();
            edgeWeightedDigraph.addEdge(new DirectedEdge(verticesMap.get(chars[0]), verticesMap.get(chars[1]),
                    Double.valueOf(Character.valueOf(chars[2]).toString())));
        }
        //System.out.println(edgeWeightedDigraph.toGraphviz());
        System.out.println(edgeWeightedDigraph.toMarkedGraphviz());
    }

    @Test
    public void distance() {
        assertEquals(9.0, edgeWeightedDigraph.pathLength(asList('A', 'B', 'C')), 0.00001);
        assertEquals(5.0, edgeWeightedDigraph.pathLength(asList('A', 'D')), 0.00001);
        assertEquals(13.0, edgeWeightedDigraph.pathLength(asList('A', 'D', 'C')), 0.00001);
        assertEquals(22.0, edgeWeightedDigraph.pathLength(asList('A', 'E', 'B', 'C', 'D')), 0.00001);
        try {
            edgeWeightedDigraph.pathLength(asList('A', 'E', 'D'));
        } catch (RuntimeException e) {
            Assert.assertEquals("NO SUCH ROUTE", e.getMessage());
        }
    }

}
