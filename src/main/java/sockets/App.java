package sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class App {


    public static void main(String[] args) throws IOException, InterruptedException {
        //parse request URI using a raw socket
        final boolean[] isFinish = {false};
        Thread mainThread = new Thread(() -> {
            String line = null;
            BufferedReader bufferedReader = null;
            Socket accept = null;
            try {
                ServerSocket serverSocket = null;
                serverSocket = new ServerSocket(8080);
                accept = serverSocket.accept();
                bufferedReader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
                line = bufferedReader.readLine();
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
                OutputStream outputStream = accept.getOutputStream();
                //               outputStream.write("HTTP/1.1 200 OK\n".getBytes());
                outputStream.write("some asdfasfd".getBytes());
                //    outputStream.write("Connection:keep-alive\n".getBytes());
                //    outputStream.write("Content-Type:text/html\".getBytes());
                outputStream.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        mainThread.start();
      /*  while (!isFinish[0]) {
            System.out.println("await finishing");
            Thread.sleep(100);
        }*/
    }

}

