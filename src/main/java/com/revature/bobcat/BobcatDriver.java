package com.revature.bobcat;

import com.revature.bobcat.util.BobcatWebServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class BobcatDriver {

    public static void main(String[] args) {

        System.out.println("Starting Bobcat server on port 8080");
        BobcatWebServer server = new BobcatWebServer();

    }
}
