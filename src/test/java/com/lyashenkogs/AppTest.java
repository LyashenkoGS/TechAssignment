package com.lyashenkogs;

import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;


public class AppTest {


    private static String removeComments(String input) {
        System.out.println("input String:   " + input);
        char[] chars = input.toCharArray();
        StringBuilder outputBuilder = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            //check if an element is a comment sign
            if (chars[i] == '/' && chars[i + 1] == '*') {
                //iterate until closed bracket
                for (int j = i + 2; j < chars.length; j++) {
                    if (chars[j] == '*' && chars[j + 1] == '/') {
                        //start new iteration from the and of a comment block
                        i = j + 1;
                        break;
                    }
                }
            } else {
                outputBuilder.append(chars[i]);
            }
        }
        System.out.println("cleaned String: " + outputBuilder.toString());
        return outputBuilder.toString();
    }

    private static void cleanComments(File output, File input) throws IOException {
        Files.deleteIfExists(output.toPath());
        Scanner scanner = new Scanner(input);
        BufferedWriter writer = new BufferedWriter(new FileWriter(output, true));
        while (scanner.hasNext()) {
            //for each line remove comments and append to a new file
            String nextLine = scanner.nextLine();
            String cleaned = removeComments(nextLine);
            writeLine(writer, cleaned);
        }
    }

    private static void writeLine(BufferedWriter writer, String string) throws IOException {
        writer.append(string);
        writer.newLine();
        writer.flush();
    }

    @Test
    public void removeCommentsFromFile() throws IOException {
        cleanComments(new File("Output.txt"), new File("Input.txt"));
        List<String> exampleLines = Files.readAllLines(Paths.get("ExampleOutput.txt"));
        List<String> outputLines = Files.readAllLines(Paths.get("Output.txt"));
        assertEquals(exampleLines, outputLines);
    }

    @Test
    public void removeCommentsFromLine() {
        String cleaned = removeComments("This is /*not */so cool.");
        assertEquals("This is so cool.", cleaned);
    }


}



