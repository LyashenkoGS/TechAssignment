package sockets;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static sockets.Utils.readBytes;


public class AppTest {
    private static final String HTTP_127_0_0_1_8080 = "http://127.0.0.1:8080/";
    private static final String FILE1 = "someFile.html";
    private static final String FILE2 = "someFile2.html";

    // region tomdog
    @Test
    public void getSomeFile() throws Exception {
        //given a web server
        App.main(new String[]{});
        //then sent an http request
        URL yahoo = new URL(HTTP_127_0_0_1_8080 + FILE1);
        URLConnection yc = yahoo.openConnection();
        System.out.println(yc.getHeaderFields());
        InputStream inputStream = yc.getInputStream();
        byte[] response = readBytes(inputStream);
        System.out.println("actual:\n" + new String(response));
        //so we will get a file in the response
        byte[] someHTML1Bytes = Files.readAllBytes(Paths.get(FILE1));
        System.out.println("expected:\n" + new String(someHTML1Bytes, Charset.defaultCharset()));
        assertArrayEquals(someHTML1Bytes, response);
        App.stop();
    }

    @Test
    public void getSomeFileMultipleTimes() throws Exception {
        //given a web server
        App.main(new String[]{});
        //then sent several same requests
        for (int i = 0; i < 3; i++) {
            URL yahoo = new URL(HTTP_127_0_0_1_8080 + FILE1);
            URLConnection yc = yahoo.openConnection();
            System.out.println(yc.getHeaderFields());
            InputStream inputStream = yc.getInputStream();
            byte[] response = readBytes(inputStream);
            System.out.println("actual:\n" + new String(response));
            //so we will get a file in the response
            byte[] someHTML1Bytes = Files.readAllBytes(Paths.get(FILE1));
            System.out.println("expected:\n" + new String(someHTML1Bytes, Charset.defaultCharset()));
            assertArrayEquals(someHTML1Bytes, response);
            inputStream.close();
        }
        App.stop();
    }


    @Test(timeout = 3_000)
    public void getSomeFileConcurrently() throws IOException, InterruptedException {
        //given a web server
        App.main(new String[]{});
        //then sent http requests concurrently
        int threads_count = 2000;
        AtomicInteger times_passed = new AtomicInteger(threads_count);
        Thread thread = new Thread(() -> {
            try {
                URL yahoo = new URL(HTTP_127_0_0_1_8080 + FILE2);
                URLConnection yc = yahoo.openConnection();
                System.out.println(yc.getHeaderFields());
                InputStream inputStream = yc.getInputStream();
                byte[] response = readBytes(inputStream);
                System.out.println("actual:\n" + new String(response));
                //so we will get a file in the response
                byte[] someHTML1Bytes = Files.readAllBytes(Paths.get(FILE2));
                System.out.println("expected:\n" + new String(someHTML1Bytes, Charset.defaultCharset()));
                assertArrayEquals(someHTML1Bytes, response);
                inputStream.close();
                times_passed.decrementAndGet();
                System.out.println("LEFT: " + times_passed.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        for (int i = 0; i < threads_count; i++) {
            new Thread(thread).start();
        }
        //await until all tests threads will pass
        while (times_passed.get() > 0) {
            System.out.println("awaiting a test result");
            Thread.sleep(1_000);
        }
        App.stop();
    }

    @Test
    public void getSomeFile2() throws Exception {
        //given a web server
        App.main(new String[]{});
        //then sent an http request
        URL yahoo = new URL(HTTP_127_0_0_1_8080 + FILE2);
        URLConnection yc = yahoo.openConnection();
        InputStream inputStream = yc.getInputStream();
        byte[] response = readBytes(inputStream);
        System.out.println("actual:\n" + new String(response));
        //so we will get a file in the response
        byte[] someHTML1Bytes = Files.readAllBytes(Paths.get(FILE2));
        System.out.println("expected:\n" + new String(someHTML1Bytes, Charset.defaultCharset()));
        assertArrayEquals(someHTML1Bytes, response);
        App.stop();
    }

    @Test
    public void getUnexistedFile() throws Exception {
        //given a web server
        App.main(new String[]{});
        //then sent an http request to a wrong URI
        URL yahoo = new URL(HTTP_127_0_0_1_8080 + "/aaaaa.html");
        URLConnection yc = yahoo.openConnection();
        InputStream inputStream = yc.getInputStream();
        byte[] response = readBytes(inputStream);
        //so we will get an error message
        System.out.println(new String(response));
        assertTrue(new String(response).contains("can't read the file by name: " + "aaaaa.html"));
        App.stop();
    }

    // endregion

}

