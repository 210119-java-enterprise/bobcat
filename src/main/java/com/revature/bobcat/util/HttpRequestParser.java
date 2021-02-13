package com.revature.bobcat.util;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRequestParser {
    static final private Pattern inst_pat = Pattern.compile("[^ :]+");
    static final private Pattern vers_pat = Pattern.compile("HTTP/([^\\s]+)");
    static final private Pattern uri_pat  = Pattern.compile("Host:\\s+(.+)");

    public HttpRequest parseRequest(Socket clientSocket) {
        try {
            final InputStreamReader input = new InputStreamReader(clientSocket.getInputStream());
            StringBuilder str = new StringBuilder();
            while(input.ready()) {
                str.append((char)input.read());
            }
            final HttpRequest htp = new HttpRequest(null,null,null,null,null);
            parseStringBuilder(str,htp);
            System.out.println(htp.toString());
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
        if (match.find()) {
            htp.setUri(match.group(1));
        }
    }

    private void parseStringBuilder(final StringBuilder str,final HttpRequest htp) {
        final String[] lines = str.toString().split("\r\n");
        Arrays.stream(lines).forEach(s ->parseString(s,htp));
    }

    private void parseString(final String str,final HttpRequest htp) {
        System.out.println(str);
       final Matcher match = inst_pat.matcher(str);
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
                   setHttpVersion(str,htp);
                   break;
               case "User-Agent":
                   break;
               case "Host":
                   setHttpURI(str,htp);
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
