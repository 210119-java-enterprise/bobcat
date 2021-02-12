package com.revature.bobcat.util;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class RequestWorker implements Runnable{

    private Socket clientSocket;

    public RequestWorker(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {

        String threadName = Thread.currentThread().getName();
        System.out.println("Request is being processed by thread: "+threadName);

        try {

            //Mock Response
            HttpResponse response = new HttpResponse();
            response.setHttpVersion("HTTP/1.1")
                    .setStatusCode(200)
                    .setStatusMessage("OK")
                    .setBody("<h1>Server response provided by thread: "+ threadName+"</h1>")
                    .setContentLength(response.getBody().length())
                    .setContentType("text/html");

            OutputStream outputStream = clientSocket.getOutputStream();
            BufferedReader reader = new BufferedReader(new StringReader(response.getBody()));

            try {
                outputStream.write(response.getStatusLine().getBytes());
                outputStream.write(("Server: Bobcat").getBytes());
                outputStream.write(("Content-Length: "+ response.getContentLength()).getBytes());
                outputStream.write(("Content-Type: "+ response.getContentType()).getBytes());
                outputStream.write(("\r\n").getBytes());

                String line = reader.readLine();
                while (line != null) {
                    outputStream.write(line.getBytes());
                    line = reader.readLine();
                }

                outputStream.flush();
                outputStream.close();

            } catch (SocketException e) {
                e.printStackTrace();
                clientSocket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
