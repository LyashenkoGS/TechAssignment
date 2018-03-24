package sockets;

import org.junit.Assert;
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
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        byte[] response = buffer.toByteArray();

        System.out.println(Arrays.toString(response));
    }


    @Test
    public void systemTest() throws Exception {
        //given a web server
        App.main(new String[]{});
        //then sent an http request
        URL yahoo = new URL("http://127.0.0.1:8000/test");
        URLConnection yc = yahoo.openConnection();
        InputStream inputStream = yc.getInputStream();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        byte[] response = buffer.toByteArray();
        System.out.println("actual:\n" + new String(response));
        //so we will get a file in a response
        byte[] someHTML1Bytes = Files.readAllBytes(Paths.get("someHTML1.html"));
        System.out.println("expected:\n" + new String(someHTML1Bytes, Charset.defaultCharset()));
        Assert.assertArrayEquals(someHTML1Bytes, response);
    }


}

