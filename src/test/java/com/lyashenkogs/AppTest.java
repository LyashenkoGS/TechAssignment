package com.lyashenkogs;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


public class AppTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }

    @Test
    public void solution() {
        int[] solve = Solution.solve(5, 6, 7, 3, 6, 10);
        assertArrayEquals(new int[]{1, 1}, solve);

        int[] solve1 = Solution.solve(1, 1, 1, 1, 1, 1);
        assertArrayEquals(new int[]{0, 0}, solve1);
    }

    @Test
    public void systemTest() {
        String initialString = "5 6 7\n" +
                "3 6 10\n";
        InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());
        System.setIn(targetStream);
        Solution.main(new String[]{});
        assertEquals("1 1\n", outContent.toString());
    }
}
