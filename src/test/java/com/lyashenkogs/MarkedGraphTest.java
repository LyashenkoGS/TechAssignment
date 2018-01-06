package com.lyashenkogs;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class MarkedGraphTest {

    @Test
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
        int counter = 0;
        for (Character vertex : vertices) {
            verticesMap.put(vertex, counter);
            counter++;
        }
        //make integer -> char map
        Map<Integer, Character> mapInversed =
                verticesMap.entrySet()
                        .stream()
                        .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

        MarkedGraph edgeWeightedDigraph = new MarkedGraph(vertices.size());
        for (String token : tokens) {
            char[] chars = token.toCharArray();
            edgeWeightedDigraph.addEdge(new DirectedEdge(verticesMap.get(chars[0]), verticesMap.get(chars[1]),
                    Double.valueOf(Character.valueOf(chars[2]).toString())));
        }
        edgeWeightedDigraph.labels = mapInversed;
        System.out.println(edgeWeightedDigraph.toGraphviz());
        System.out.println(edgeWeightedDigraph.toMarkedGraphviz());
    }
}
