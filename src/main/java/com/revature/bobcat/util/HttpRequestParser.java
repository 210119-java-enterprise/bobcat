package com.revature.bobcat.util;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRequestParser {
    static final private Pattern inst_pat = Pattern.compile("[^ :]+");
    static final private Pattern vers_pat = Pattern.compile("HTTP/([^\\s]+)");
    static final private Pattern uri_pat  = Pattern.compile("Host:\\s+(.+)");
    static final private Pattern hdr_pat  = Pattern.compile(":\\s+(.+)");
    private boolean BODY_FLAG = false;

    public HttpRequest parseRequest(Socket clientSocket) {
        try {
            final InputStreamReader input = new InputStreamReader(clientSocket.getInputStream());
            StringBuilder str = new StringBuilder();
            while(input.ready()) {
                str.append((char)input.read());
            }
            final HttpRequest htp = new HttpRequest(null,null,null,new HashMap<String,String>(),null);
            parseStringBuilder(str,htp);
            return htp;
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setHttpHeader(final String header,final String s,final HttpRequest htp) {
        final Matcher match = hdr_pat.matcher(s);
        if(match.find()) {
            htp.getHeaders().put(header,match.group(1));
        }

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

    private void parseStringSomeMore(final String m, final String str,final HttpRequest htp) {
        if(BODY_FLAG) {
            htp.setBody(str);
        }
        else if(str.length() == 0) {
            BODY_FLAG = true;
        }
        else {
            setHttpHeader(m,str,htp);

        }
    }

    private void parseString(final String str,final HttpRequest htp) {
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
               case "Host":
                   setHttpURI(str,htp);
                   break;
              default: parseStringSomeMore(m,str,htp);
                   break;
           }
       }
   }

}
