package com.revature.bobcat.util;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;

public class HttpRequestParser {

    public HttpRequest parseRequest(Socket clientSocket) {

        String method = "";
        String version = "";
        String uri = "";
        String body = "";
        Map<String, String> map = new TreeMap<>();
        try {
            // Create Map, InputStream, and StringBuilder
            final InputStream inStream = clientSocket.getInputStream();
            StringBuilder textBuilder = new StringBuilder();

            // BR to read input stream
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, Charset.forName(StandardCharsets.UTF_8.name())));
            int c = 0;

            // while has a character, append it to the StringBuilder.
            while ((c = reader.read()) != -1) {
                textBuilder.append((char) c);
            }

            //System.out.println(textBuilder);

            // Convert to String.
            String string = textBuilder.toString();

            // Split into lines
            String[] sArray = string.split("\n");

            // Get the method & version line and then split into individual method and version strings.
            String[] firstLine = sArray[0].split(" ");
            method = firstLine[0].trim();
            //System.out.println("Method: " + method);
            uri = firstLine[1];
            //System.out.println("URI: " + uri);
            version = firstLine[2].trim();
            //System.out.println("Version: " + version);

            //System.out.println(method);
            //System.out.println(version);

            // start at 1 since method and version were index 0.
            for (int i = 1; i < sArray.length - 3; i++) {
                // split into header and data by splitting on ": ". Not giving 2 strings?
                String[] headerSplit = sArray[i].split(": ");

                /*for (String s : headerSplit)
                    System.out.println(s);
                */

                // put into map...
                map.put(headerSplit[0].trim(), headerSplit[1].trim());
            }

            body = "";
            for (int i = sArray.length - 1; i < sArray.length; i++) {
                body += sArray[i];
            }

            HttpRequest request = new HttpRequest(method, version, uri, map, body);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Testing that I parsed correctly.
        System.out.println(method);
        System.out.println(version);
        System.out.println(uri);
        for (String s : map.keySet())
            System.out.println(s + ": " + map.get(s));
        System.out.println(body);


        HttpRequest request = new HttpRequest(method, version, uri, map, body);

        return request;
    }

}
