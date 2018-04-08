package sockets;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
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


public class AppTest {
    private static final String FILE1 = "someFile.html";
    private static final String FILE2 = "someFile2.html";

    //region nanoHTTPD

    @Test
    public void nanoHTTPDgetFile() throws IOException {
        //given a web server
        App.NanoHTTTPDexample.main();
        //then sent an http request
        URL yahoo = new URL("http://127.0.0.1:8080/" + FILE1);
        URLConnection yc = yahoo.openConnection();
        System.out.println(yc.getHeaderFields());
        InputStream inputStream = yc.getInputStream();
        byte[] response = readBytes(inputStream);
        System.out.println("actual:\n" + new String(response));
        //so we will get a file in the response
        byte[] someHTML1Bytes = Files.readAllBytes(Paths.get(FILE1));
        System.out.println("expected:\n" + new String(someHTML1Bytes, Charset.defaultCharset()));
        assertArrayEquals(someHTML1Bytes, response);
        App.NanoHTTTPDexample.instance.closeAllConnections();
    }


    @Test
    public void nanoHTTPDgetFile2() throws IOException {
        //given a web server
        App.NanoHTTTPDexample.main();
        //then sent an http request
        URL yahoo = new URL("http://127.0.0.1:8080/" + FILE2);
        URLConnection yc = yahoo.openConnection();
        System.out.println(yc.getHeaderFields());
        InputStream inputStream = yc.getInputStream();
        byte[] response = readBytes(inputStream);
        System.out.println("actual:\n" + new String(response));
        //so we will get a file in the response
        byte[] someHTML1Bytes = Files.readAllBytes(Paths.get(FILE2));
        System.out.println("expected:\n" + new String(someHTML1Bytes, Charset.defaultCharset()));
        assertArrayEquals(someHTML1Bytes, response);
        App.NanoHTTTPDexample.instance.closeAllConnections();
    }


    @Test
    public void nanoHTTPDgetSomeFileMultipleTimes() throws Exception {
        //given a web server
        App.NanoHTTTPDexample.main();
        //then sent several same requests
        for (int i = 0; i < 3; i++) {
            URL yahoo = new URL("http://127.0.0.1:8080/" + FILE1);
            URLConnection yc = yahoo.openConnection();
            System.out.println(yc.getHeaderFields());
            InputStream inputStream = yc.getInputStream();
            byte[] response = readBytes(inputStream);
            System.out.println("actual:\n" + new String(response));
            //so we will get a file in the response
            byte[] someHTML1Bytes = Files.readAllBytes(Paths.get(FILE1));
            System.out.println("expected:\n" + new String(someHTML1Bytes, Charset.defaultCharset()));
            assertArrayEquals(someHTML1Bytes, response);
        }
        App.NanoHTTTPDexample.instance.closeAllConnections();
    }

    @Test(timeout = 3_000)
    public void nanoHTTPDgetSomeFileConcurrently() throws IOException, InterruptedException {
        //given a web server
        App.NanoHTTTPDexample.main();
        //then sent http requests concurrently
        AtomicInteger times_passed = new AtomicInteger(5);
        Thread thread = new Thread(() -> {
            try {
                URL yahoo = new URL("http://127.0.0.1:8080/" + FILE2);
                URLConnection yc = yahoo.openConnection();
                System.out.println(yc.getHeaderFields());
                InputStream inputStream = yc.getInputStream();
                byte[] response = readBytes(inputStream);
                System.out.println("actual:\n" + new String(response));
                //so we will get a file in the response
                byte[] someHTML1Bytes = Files.readAllBytes(Paths.get(FILE2));
                System.out.println("expected:\n" + new String(someHTML1Bytes, Charset.defaultCharset()));
                assertArrayEquals(someHTML1Bytes, response);
                times_passed.decrementAndGet();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
        new Thread(thread).start();
        new Thread(thread).start();
        new Thread(thread).start();
        new Thread(thread).start();
        while (times_passed.get() != 0) {
            System.out.println("awaiting a test result");
            Thread.sleep(100);
        }
        App.NanoHTTTPDexample.instance.closeAllConnections();
    }

    @Test
    public void nanoHTTPDgetUnexistedFile() throws Exception {
        //given a web server
        App.NanoHTTTPDexample.main();
        //then sent an http request to a wrong URI
        URL yahoo = new URL("http://127.0.0.1:8080/aaaaa.html");
        URLConnection yc = yahoo.openConnection();
        InputStream inputStream = yc.getInputStream();
        byte[] response = readBytes(inputStream);
        //so we will get an error message
        System.out.println(new String(response));
        assertTrue(new String(response).contains("can't read the file by name: " + "aaaaa.html"));
        App.NanoHTTTPDexample.instance.closeAllConnections();
    }

    //endregion

    // region tomdog
    @Test
    public void getSomeFile() throws Exception {
        //given a web server
        App.main(new String[]{});
        //then sent an http request
        URL yahoo = new URL("http://127.0.0.1:8080/" + FILE1);
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
            URL yahoo = new URL("http://127.0.0.1:8080/" + FILE1);
            URLConnection yc = yahoo.openConnection();
            System.out.println(yc.getHeaderFields());
            InputStream inputStream = yc.getInputStream();
            byte[] response = readBytes(inputStream);
            System.out.println("actual:\n" + new String(response));
            //so we will get a file in the response
            byte[] someHTML1Bytes = Files.readAllBytes(Paths.get(FILE1));
            System.out.println("expected:\n" + new String(someHTML1Bytes, Charset.defaultCharset()));
            assertArrayEquals(someHTML1Bytes, response);
        }
        App.stop();
    }


    @Test(timeout = 3_000)
    public void getSomeFileConcurrently() throws IOException, InterruptedException {
        //given a web server
        App.main(new String[]{});
        //then sent http requests concurrently
        AtomicInteger times_passed = new AtomicInteger(5);
        Thread thread = new Thread(() -> {
            try {
                URL yahoo = new URL("http://127.0.0.1:8080/" + FILE2);
                URLConnection yc = yahoo.openConnection();
                System.out.println(yc.getHeaderFields());
                InputStream inputStream = yc.getInputStream();
                byte[] response = readBytes(inputStream);
                System.out.println("actual:\n" + new String(response));
                //so we will get a file in the response
                byte[] someHTML1Bytes = Files.readAllBytes(Paths.get(FILE2));
                System.out.println("expected:\n" + new String(someHTML1Bytes, Charset.defaultCharset()));
                assertArrayEquals(someHTML1Bytes, response);
                times_passed.decrementAndGet();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
        new Thread(thread).start();
        new Thread(thread).start();
        new Thread(thread).start();
        new Thread(thread).start();
        while (times_passed.get() != 0) {
            System.out.println("awaiting a test result");
            Thread.sleep(20);
        }
        App.stop();
    }

    @Test
    public void getSomeFile2() throws Exception {
        //given a web server
        App.main(new String[]{});
        //then sent an http request
        URL yahoo = new URL("http://127.0.0.1:8080/" + FILE2);
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
        URL yahoo = new URL("http://127.0.0.1:8080/aaaaa.html");
        URLConnection yc = yahoo.openConnection();
        InputStream inputStream = yc.getInputStream();
        byte[] response = readBytes(inputStream);
        //so we will get an error message
        System.out.println(new String(response));
        assertTrue(new String(response).contains("can't read the file by name: " + "aaaaa.html"));
        App.stop();
    }

    // endregion
    private byte[] readBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384];
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        return buffer.toByteArray();
    }

}

