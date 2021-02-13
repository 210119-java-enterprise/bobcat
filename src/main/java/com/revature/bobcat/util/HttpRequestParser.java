package com.revature.bobcat.util;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRequestParser {
    static final private Pattern inst_pat = Pattern.compile("[^ :]+");
    static final private Pattern vers_pat = Pattern.compile("HTTP/([^\\s]+)");
    static final private Pattern uri_pat  = Pattern.compile("HOST: (.+)");

    public HttpRequest parseRequest(Socket clientSocket) {
        try {
            final InputStreamReader input = new InputStreamReader(clientSocket.getInputStream());
            StringBuilder str = new StringBuilder();
            while(input.ready()) {
                str.append((char)input.read());
            }
            final HttpRequest htp = new HttpRequest(null,null,null,null,null);
            parseString(str,htp);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setHttpVersion(final String line, final HttpRequest htp) {
        final Matcher match = vers_pat.matcher(line);
        if(match.find()) {
            htp.setHttpVersion(match.group(1));
        }
    }

    private void setHttpMethod(final String method, final HttpRequest htp) {
        htp.setHttpMethod(method);
    }

    private void setHttpURI(final String s, final HttpRequest htp) {
        final Matcher match = uri_pat.matcher(s);
        if (match.find() ){
            htp.setUri(match.group(1));
        }
    }

    private void parseString(final StringBuilder str,final HttpRequest htp) {
       final String[] lines = str.toString().split("\r\n");
       for(String s: lines) {
           final Matcher match = inst_pat.matcher(s);
           if(match.find()) {
               final String m = match.group();
               switch (m) {
                   case "GET":
                   case "POST":
                   case "PUT":
                   case "HEAD":
                   case "DELETE":
                   case "PATCH":
                   case "OPTIONS":
                   case "Trace":
                   case "Connect":
                       setHttpMethod(m,htp);
                       setHttpVersion(s,htp);
                       break;
                   case "User-Agent":
                       break;
                   case "Host":
                       setHttpURI(s,htp);
                       break;
                   case "Accept-Language":
                       break;
                   case "Accept-Encoding":
                       break;
                   case "Connection":
                       break;
               }
           }
       }
    }

}
