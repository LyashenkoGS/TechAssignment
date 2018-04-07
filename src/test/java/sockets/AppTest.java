package sockets;

import org.junit.After;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;


public class AppTest {

    @After
    public void tearDown() throws Exception {
        //prevents an error due to unclosed socket
        Thread.sleep(2_000);
    }

    @Test
    public void getSomeFile() throws Exception {
        //given a web server
        App.main(new String[]{});
        Thread.sleep(1_000);
        //then sent an http request
        URL yahoo = new URL("http://127.0.0.1:8080/someFile.html");
        URLConnection yc = yahoo.openConnection();
        System.out.println(yc.getHeaderFields());
        InputStream inputStream = yc.getInputStream();
        byte[] response = readBytes(inputStream);
        System.out.println("actual:\n" + new String(response));
        //so we will get a file in the response
        byte[] someHTML1Bytes = Files.readAllBytes(Paths.get("someFile.html"));
        System.out.println("expected:\n" + new String(someHTML1Bytes, Charset.defaultCharset()));
        assertArrayEquals(someHTML1Bytes, response);
    }

    @Test
    public void nanoHTTPDgetFile1() throws IOException, InterruptedException {
        //given a web server
        App.NanoHTTTPDexample.main();
        Thread.sleep(1_000);
        //then sent an http request
        URL yahoo = new URL("http://127.0.0.1:8080/someFile.html");
        URLConnection yc = yahoo.openConnection();
        System.out.println(yc.getHeaderFields());
        InputStream inputStream = yc.getInputStream();
        byte[] response = readBytes(inputStream);
        System.out.println("actual:\n" + new String(response));
        //so we will get a file in the response
        byte[] someHTML1Bytes = Files.readAllBytes(Paths.get("someFile.html"));
        System.out.println("expected:\n" + new String(someHTML1Bytes, Charset.defaultCharset()));
        assertArrayEquals(someHTML1Bytes, response);
        App.NanoHTTTPDexample.instance.closeAllConnections();
    }

    @Test
    public void nanoHTTPDgetUnexistedFile() throws Exception {
        //given a web server
        App.NanoHTTTPDexample.main();
        Thread.sleep(1_000);
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

    @Test
    public void getSomeFile2() throws Exception {
        //given a web server
        App.main(new String[]{});
        Thread.sleep(1_000);
        //then sent an http request
        URL yahoo = new URL("http://127.0.0.1:8080/someFile2.html");
        URLConnection yc = yahoo.openConnection();
        InputStream inputStream = yc.getInputStream();
        byte[] response = readBytes(inputStream);
        System.out.println("actual:\n" + new String(response));
        //so we will get a file in the response
        byte[] someHTML1Bytes = Files.readAllBytes(Paths.get("someFile2.html"));
        System.out.println("expected:\n" + new String(someHTML1Bytes, Charset.defaultCharset()));
        assertArrayEquals(someHTML1Bytes, response);
    }

    @Test
    public void getUnexistedFile() throws Exception {
        //given a web server
        App.main(new String[]{});
        Thread.sleep(1_000);
        //then sent an http request to a wrong URI
        URL yahoo = new URL("http://127.0.0.1:8080/aaaaa.html");
        URLConnection yc = yahoo.openConnection();
        InputStream inputStream = yc.getInputStream();
        byte[] response = readBytes(inputStream);
        //so we will get an error message
        System.out.println(new String(response));
        assertTrue(new String(response).contains("can't read the file by name: " + "aaaaa.html"));
    }

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

