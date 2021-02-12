package com.revature.bobcat.util;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class HttpRequestParser {

    public HttpRequest parseRequest(Socket clientSocket) {
        BufferedReader buff =null;
        try {
            buff = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String method;
            String uri;
            String version;

            String line = buff.readLine();

            int index = line.indexOf(" ");
            method = line.substring(0, index);
            index++;
            uri = line.substring(index, line.indexOf(" "));
            index++;
            version = line.substring(index);

            buff.readLine();

            String body = buff.readLine();

            return new HttpRequest(method, version, uri, body);




        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
