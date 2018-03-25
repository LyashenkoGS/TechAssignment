package sockets;

import org.junit.Ignore;
import org.junit.Test;

import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;


public class AppTest {

    @Test
    @Ignore
    //TODO finish implementing a web server on a raw socket
    public void readRequestAsBytes() throws Exception {
        // App.main(new String[]{});
        final ServerSocket[] serverSocket = new ServerSocket[1];
        final Socket[] accept = new Socket[1];
        Thread thread = new Thread(() -> {
            try {
                serverSocket[0] = new ServerSocket(8000);
                accept[0] = serverSocket[0].accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        Thread.sleep(2_000);
        //given a socket
        URL yahoo = new URL("http://127.0.0.1:8000/test");
        URLConnection yc = yahoo.openConnection();
        yc.setDoOutput(true);
        yc.setDoInput(true);
        OutputStream outputStream = yc.getOutputStream();
        //then send a request
        byte[] hexBinary = DatatypeConverter.parseHexBinary("0fb8");
        outputStream.write(hexBinary);
        outputStream.flush();
        outputStream.close();
        Thread.sleep(1_000);
        //so we will get a request as a byte array
        InputStream inputStream = accept[0].getInputStream();
        byte[] response = readBytes(inputStream);
        System.out.println(Arrays.toString(response));
    }

    @Test
    public void getSomeFile() throws Exception {
        //given a web server
        App.main(new String[]{});
        //then sent an http request
        URL yahoo = new URL("http://127.0.0.1:8080/someFile.html");
        URLConnection yc = yahoo.openConnection();
        InputStream inputStream = yc.getInputStream();
        byte[] response = readBytes(inputStream);
        System.out.println("actual:\n" + new String(response));
        //so we will get a file in the response
        byte[] someHTML1Bytes = Files.readAllBytes(Paths.get("someFile.html"));
        System.out.println("expected:\n" + new String(someHTML1Bytes, Charset.defaultCharset()));
        assertArrayEquals(someHTML1Bytes, response);
    }

    @Test
    public void getSomeFile2() throws Exception {
        //given a web server
        App.main(new String[]{});
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
        //then sent an http request to a wrong URI
        URL yahoo = new URL("http://127.0.0.1:8080/aaaaa.html");
        URLConnection yc = yahoo.openConnection();
        InputStream inputStream = yc.getInputStream();
        byte[] response = readBytes(inputStream);
        //so we will get an error message
        assertTrue(new String(response).contains("unexpected URI: " + "/aaaaa.html"));
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

