package sockets;

import fi.iki.elonen.NanoHTTPD;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

public class App {

    private static final String DELIMITER = "\r\n";

    public static void main(String[] args) {
        //process incoming HTTP request in a new thread
        Thread mainThread = new Thread(() -> {
            String line = null;
            BufferedReader bufferedReader = null;
            Socket accept = null;
            String fileName = "";
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(8080);
                serverSocket.setReuseAddress(true);
                accept = serverSocket.accept();
                System.out.println("START CUSTOM SERVER");
                bufferedReader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
                line = bufferedReader.readLine();
                //parse URI
                String[] tokens = line.split(" ");
                fileName = tokens[1].replace("/", "");
            } catch (Exception e) {
                e.printStackTrace();
            }
            while (line != null && !line.equals("")) {
                System.out.println(line);
                try {
                    line = bufferedReader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //send response
            try {
                File file = new File(fileName);
                byte[] responseBody;
                if (file.canRead()) {
                    responseBody = Files.readAllBytes(file.toPath());
                } else {
                    responseBody = ("can't read the file by name: " + fileName).getBytes();
                }
                OutputStream outputStream = accept.getOutputStream();
                outputStream.write(("HTTP/1.1 200 OK" + DELIMITER).getBytes());
                outputStream.write(("Content-Type: text/html" + DELIMITER).getBytes());
                outputStream.write(("Content-Length: " + responseBody.length + DELIMITER).getBytes());
                outputStream.write(DELIMITER.getBytes());
                outputStream.write(responseBody);
                outputStream.flush();
                System.out.println("CUSTOM SERVER RESPONSE HAS BEEN SEND");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                serverSocket.close();
                accept.close();
                System.out.println("CUSTOM SERVER:SOCKET IS CLOSED");

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        mainThread.start();
    }

    static class NanoHTTTPDexample extends NanoHTTPD {

        static NanoHTTTPDexample instance;

        public NanoHTTTPDexample() throws IOException {
            super(8080);
            start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
            System.out.println("\nRunning! Point your browsers to http://localhost:8080/ \n");
        }

        public static void main() {
            try {
                instance = new NanoHTTTPDexample();
            } catch (IOException ioe) {
                System.err.println("Couldn't start server:\n" + ioe);
            }
        }

        @Override
        public NanoHTTPD.Response serve(IHTTPSession session) {
            String fileName = session.getUri().replace("/", "");
            byte[] bytes = new byte[0];
            try {
                bytes = Files.readAllBytes(Paths.get(fileName));
            } catch (NoSuchFileException e) {
                return newFixedLengthResponse("can't read the file by name: " + fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("CUSTOM SERVER RESPONSE HAS BEEN SEND");
            return newFixedLengthResponse(new String(bytes, Charset.defaultCharset()));
        }
    }

}

