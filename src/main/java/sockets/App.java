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
    private static ServerSocket serverSocket = null;
    private static boolean isReady = false;
    private static boolean running = true;

    public static void stop() throws InterruptedException, IOException {
        App.running = false;
        serverSocket.close();
        while (isReady) {
            Thread.sleep(100);
            System.out.println("awaiting until the server shutdown");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        //process incoming HTTP request in a new thread
        Thread mainThread = new Thread(() -> {
            running = true;
            String line;
            Socket connection;
            String fileName;
            try {
                serverSocket = new ServerSocket(8080);
                serverSocket.setReuseAddress(true);
                System.out.println("START CUSTOM SERVER");
                isReady = true;
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    if (serverSocket != null) {
                        serverSocket.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            while (running) {
                try {
                    //accept a connection and parse URI
                    connection = serverSocket.accept();
                    System.out.println("Accept incoming request");
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    line = bufferedReader.readLine();
                    String[] tokens = line.split(" ");
                    fileName = tokens[1].replace("/", "");
                    while (line != null && !line.equals("")) {
                        System.out.println(line);
                        line = bufferedReader.readLine();
                    }
                    //read and return a static file or an error
                    File file = new File(fileName);
                    byte[] responseBody;
                    if (file.canRead()) {
                        responseBody = Files.readAllBytes(file.toPath());
                    } else {
                        responseBody = ("can't read the file by name: " + fileName).getBytes();
                    }
                    OutputStream outputStream = connection.getOutputStream();
                    outputStream.write(("HTTP/1.1 200 OK" + DELIMITER).getBytes());
                    outputStream.write(("Content-Type: text/html" + DELIMITER).getBytes());
                    outputStream.write(("Content-Length: " + responseBody.length + DELIMITER).getBytes());
                    outputStream.write(DELIMITER.getBytes());
                    outputStream.write(responseBody);
                    outputStream.flush();
                    System.out.println("CUSTOM SERVER RESPONSE HAS BEEN SEND");
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //shutdown the server on the "running" flag change
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
                System.out.println("CUSTOM SERVER:SOCKET IS CLOSED");
            } catch (IOException e) {
                e.printStackTrace();
            }
            isReady = false;
        });
        mainThread.start();
        while (!isReady) {
            System.out.println("awaiting the server start");
            Thread.sleep(500);
        }
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

