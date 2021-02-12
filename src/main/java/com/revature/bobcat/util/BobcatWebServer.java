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
    private ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public void listen() {
        serverPort = 8080;
        isStopped = false;
    }

    public synchronized void shutdown() {

    }

    public void start() {
        try {
            serverSocket = new ServerSocket(serverPort);

            while(!isStopped) {
                Socket clientSocket = serverSocket.accept();
                this.threadPool.execute(new RequestWorker(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
