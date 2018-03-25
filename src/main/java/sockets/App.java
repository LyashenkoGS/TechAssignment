package sockets;

import fi.iki.elonen.NanoHTTPD;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

public class App extends NanoHTTPD {

    public App() throws IOException {
        super(8080);
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("\nRunning! Point your browsers to http://localhost:8080/ \n");
    }

    public static void main(String[] args) {
        try {
            new App();
        } catch (IOException ioe) {
            System.err.println("Couldn't start server:\n" + ioe);
        }
    }

    @Override
    public Response serve(IHTTPSession session) {
        String fileName = session.getUri().replace("/", "");
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(Paths.get(fileName));
        } catch (NoSuchFileException e) {
            System.out.println("cant handle URI:" + session.getUri());
            return newFixedLengthResponse("unexpected URI: " + session.getUri());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newFixedLengthResponse(new String(bytes, Charset.defaultCharset()));
    }
}

