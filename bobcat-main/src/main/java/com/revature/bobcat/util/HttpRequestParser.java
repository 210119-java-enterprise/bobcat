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

        try {
            Map<String, String> header = new HashMap<>();

            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String line = reader.readLine();

            String []arr = line.split(" "); //"get / http/1.1
            request.setHttpMethod(arr[0]); //set GET method from first line
            request.setUri(arr[1]); //set URI from first line
            request.setHttpVersion(arr[2]); //set Httpveresion from first line

            line = reader.readLine(); //get the headers now
            while(line.length() != 0) //want this != 0 to stop before the body
            {
                arr = line.split(": ");
                if(arr.length > 1)
                {
                    header.put(arr[0], arr[1]); //header title : header body
                }
                line = reader.readLine();
            }
            request.setHeaders(header);

            StringBuilder body = new StringBuilder();
            while (line != null)
            {
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
