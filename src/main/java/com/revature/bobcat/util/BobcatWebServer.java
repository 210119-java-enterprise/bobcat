package com.revature.bobcat.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BobcatWebServer {

    private int serverPort;
    private ServerSocket serverSocket;
    private boolean isStopped;
    private final ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public void listen(int port) {
        this.serverPort = port;
        this.isStopped = false;
        start();
    }

    // TODO: implementation
    public synchronized void shutdown() {

    }

    private void start() {

        try {
            this.serverSocket = new ServerSocket(this.serverPort);
            while(!isStopped) {
                Socket clientSocket = serverSocket.accept(); // blocks until a client request comes in
                this.threadPool.execute(new RequestWorker(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
