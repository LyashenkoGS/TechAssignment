package sockets;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;


public class AppTest {


    @Test
    public void name() throws IOException {
        Socket client = new Socket("144.76.162.245", 80);
        OutputStream outputStream = client.getOutputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    /*    DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeBytes("GET / HTTP/1.1\r\n");
        dataOutputStream.writeBytes("Host: stackoverflow.com\r\n\r\n");
        dataOutputStream.flush();*/
    }

    @Test
    public void systemTest() throws Exception {
        //given a web server
        new Thread((Runnable) () -> {
            try {
                App.main(new String[]{});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        Thread.sleep(2_000);
        //then sent an http request
        //URL yahoo = new URL("https://stackoverflow.com/questions/1359689/how-to-send-http-request-in-java");
        URL yahoo = new URL("http://127.0.0.1:8000/test");
        URLConnection yc = yahoo.openConnection();
         /*BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
       StringBuilder sb = new StringBuilder();
        String buffer = "";
        while (buffer != null) {
            buffer = in.readLine();
            sb.append(buffer).append("\n");
        }*/

        InputStream inputStream = yc.getInputStream();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        byte[] response = buffer.toByteArray();

        System.out.println("response:\n" + sb.toString());
        //so wi will ge a file in a response
        byte[] someHTML1Bytes = Files.readAllBytes(Paths.get("someHTML1.html"));
        System.out.println("expected:\n" + new String(someHTML1Bytes, Charset.defaultCharset()));
        Assert.assertArrayEquals(someHTML1Bytes, sb.toString().getBytes());
    }
}

