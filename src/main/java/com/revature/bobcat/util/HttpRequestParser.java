// Authors: Kalyb Levesque and Daniel Skwarcha
package com.revature.bobcat.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class HttpRequestParser {

    // TODO: IMPLEMENT ME!
    public HttpRequest parseRequest(Socket clientSocket) {


        String httpMethod = "";
        String httpVersion= "";
        String uri= "";
        Map<String,String> headers = new Hashtable<>();
        String body= "";
        BufferedReader reader;

        ArrayList<String> myString = new ArrayList<>();
        String key = "";
        String value = "";

        try {
             //reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             InputStreamReader inputStream = new InputStreamReader(clientSocket.getInputStream());

             reader = new BufferedReader(inputStream);
            String line = reader.readLine();
             if (line != null)
             {
                 String[] tempStrings = line.split(" ");
                 httpMethod = tempStrings[0];
                 uri = tempStrings[1];
                 httpVersion = tempStrings[2];
             }

             while(!key.equals("Connection"))
             {
                 line = reader.readLine();
                 if(line != null)
                 {
                     String[] tempStrings = line.split(": ");
                     key = tempStrings[0];
                     value = tempStrings[1];
                     headers.put(key, value);
                 }

             }

             // Didn't know what to do with the body. Different depending on what type of body was sent.
            //System.out.println(reader.readLine()) ;
//             while(line!= null)
//            {
//                 System.out.println(line);
//                 line = reader.readLine();
//
//             }


             HttpRequest httpRequest = new HttpRequest(httpMethod, httpVersion, uri, headers, body);
             return httpRequest;

        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

}
