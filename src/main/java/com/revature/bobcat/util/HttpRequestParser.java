package com.revature.bobcat.util;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestParser {

    HttpRequest HR;
    public HttpRequest parseRequest(Socket clientSocket) {

//        try {
//            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(),"UTF8"));
//            for( String line : HR.getHttpMethod()){
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        HttpResponse response = new HttpResponse();
        HttpRequest HRequest = new HttpRequest();
        Map<String, String> headers = new HashMap<String,String>();
        int linenum = 0;
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String inputLine;
            while (true) {


                if (!!(inputLine = in.readLine()).equals("")) break;
                linenum++;
               // System.out.println("Linenumber"+linenum);
               // System.out.println(inputLine);

                //Method and Version
                if(linenum==1){
                    //String mystring = "the quick brown fox";
                    String arr[] = inputLine.split(" ", 3);

//                    String firstWord = arr[0];   //the
//                    String theRest = arr[1];     //quick brown fox
                    //System.out.println("Method is"+ arr[0]);
                    HRequest.setHttpMethod(arr[0]);
                   // System.out.println("URI is"+arr[1]);
                   // System.out.println("Version is "+ arr[2]);
                    HRequest.setUri(arr[1]);
                    HRequest.setHttpVersion(arr[2]);
                    //HRequest.setHttpMethod(inputLine.);
                }
                String arr2[] = inputLine.split(" ", 2);

                String key = arr2[0];   //the
                String value = arr2[1];     //quick brown fox
               // System.out.println("name "+key);
                //System.out.println("value " + value);
                headers.put(key,value);
            }
            HRequest.setHeaders(headers);
            String inputLine2 = in.readLine();
           // System.out.println("Body: " + inputLine2);
            HRequest.setBody(inputLine2);

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return HRequest;
    }

}
