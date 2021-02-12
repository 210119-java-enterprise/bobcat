package com.revature.bobcat.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestParser {

    // TODO: IMPLEMENT ME!
    public HttpRequest parseRequest(Socket clientSocket) {
        HttpRequest request = new HttpRequest();
        String line = "";
        try {
            Map<String, String> headerMap = new HashMap<>();

            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            line = reader.readLine();

            String[] firstLine = line.split(" ");
            request.setHttpMethod(firstLine[0]);
            request.setUri(firstLine[1]);
            request.setHttpVersion(firstLine[2]);

            line = reader.readLine();
            while(line.length() != 0){
                String[] nextLine = line.split(" ");
                headerMap.put(nextLine[0], nextLine[1]);
                line = reader.readLine();
            }
            request.setHeaders(headerMap);
            StringBuilder body = new StringBuilder("");
            while(line != null){
                body.append(line);
                line = reader.readLine();
            }
            request.setBody(body.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return request;
    }

}
