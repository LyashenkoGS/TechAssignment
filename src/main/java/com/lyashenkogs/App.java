package com.lyashenkogs;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class App {

    private static final String TEMP_IMG = "example/ex1.png";

    public static void main(String[] args) throws IOException {
        MutableGraph g = Parser.read("strict graph G {\n" +
                "0 -- 1;\n" +
                "0 -- 2;\n" +
                "1 -- 0;\n" +
                "1 -- 2;\n" +
                "2 -- 0;\n" +
                "2 -- 1;\n" +
                "}");
        Graphviz.fromGraph(g).height(700).render(Format.PNG).toFile(new File(TEMP_IMG));
        drawWindow(new File(TEMP_IMG));
    }

    public static void drawWindow(File image) throws IOException {
        //create a window with a graph picture
        final BufferedImage backgroundImage = ImageIO.read(image);
        JFrame jframe = new JFrame() {
            public void paint(Graphics g) {
                super.paint(g);
                g.drawImage(backgroundImage, 0, 0, null);
            }
        };
        jframe.setPreferredSize(new Dimension(backgroundImage.getWidth(), backgroundImage.getHeight() + 50));
        jframe.pack();
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);
    }
}