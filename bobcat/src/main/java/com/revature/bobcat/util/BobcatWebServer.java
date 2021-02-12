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

    public void listen(int port){
        serverPort = port;
        isStopped = false;
        start();
    }

    public synchronized void shutdown(){
        isStopped = true;
    }

    private void start(){
        try{
            serverSocket = new ServerSocket(serverPort);

            while(!isStopped) {
                Socket clientSocket = serverSocket.accept();//blocks until client request is made
                this.threadPool.execute(new RequestWorker(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
