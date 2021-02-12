package com.revature.bobcat.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestParser {

    // TODO: implement me!
    public HttpRequest parseRequest(Socket clientSocket) {
        HttpRequest request = new HttpRequest();
        // System.out.println("Thread working on reading stuff: " + Thread.currentThread().getName());

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String line = reader.readLine();
            String[] splitLine = line.split(" ");
            request.setHttpMethod(splitLine[0]);
            request.setUri(splitLine[1]);
            request.setHttpVersion(splitLine[2]);

            line = reader.readLine();
            Map<String, String> headerMap = new HashMap<>();
            while(line != null) {
                // get headers
                splitLine = line.split(": ");
                if (splitLine.length > 1) {
                    headerMap.put(splitLine[0], splitLine[1]);
                }
                line = reader.readLine();
            }
            request.setHeaders(headerMap);
            // get body
            line = reader.readLine();
            StringBuilder body = new StringBuilder();
            while (line != null) {
                body.append(line);
                line = reader.readLine();
            }
            request.setBody(body.toString());

        } catch (IOException e ) {
            e.printStackTrace();
        }




        return request;
        // return null;
    }

}
