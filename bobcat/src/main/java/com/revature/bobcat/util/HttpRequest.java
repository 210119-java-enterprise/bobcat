package com.revature.bobcat.util;

import java.util.Map;

public class HttpRequest {

    private String httpMethod; // GET, POST, PUT, PATCH, DELETE, etc.
    private String httpVersion;
    private String uri;
    private Map<String, String> headers;
    private String body;

    public HttpRequest(){
        super();
    }

    public HttpRequest(String httpMethod, String httpVersion, String uri, Map<String, String> headers, String body) {
        this.httpMethod = httpMethod;
        this.httpVersion = httpVersion;
        this.uri = uri;
        this.headers = headers;
        this.body = body;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "httpMethod='" + httpMethod + '\'' +
                ", httpVersion='" + httpVersion + '\'' +
                ", uri='" + uri + '\'' +
                ", headers=" + headers +
                ", body='" + body + '\'' +
                '}';
    }
}
