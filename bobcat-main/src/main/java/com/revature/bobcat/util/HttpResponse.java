package com.revature.bobcat.util;

import java.util.Hashtable;

public class HttpResponse {

    private String httpVersion;
    private int statusCode;
    private String statusMessage;
    private Hashtable<String, String> headers;
    private String body;
    private int contentLength;
    private String contentType;

    public String getHttpVersion() {
        return httpVersion;
    }

    public HttpResponse setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
        return this;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public HttpResponse setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public HttpResponse setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
        return this;
    }

    public Hashtable<String, String> getHeaders() {
        return headers;
    }

    public HttpResponse setHeaders(Hashtable<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public String getBody() {
        return body;
    }

    public HttpResponse setBody(String body) {
        this.body = body;
        return this;
    }

    public int getContentLength() {
        return contentLength;
    }

    public HttpResponse setContentLength(int contentLength) {
        this.contentLength = contentLength;
        return this;
    }

    public String getContentType() {
        return contentType;
    }

    public HttpResponse setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public String getStatusLine() {
        return httpVersion + " " + statusCode + " " + statusMessage + "\r\n";
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "httpVersion='" + httpVersion + '\'' +
                ", statusCode=" + statusCode +
                ", statusMessage='" + statusMessage + '\'' +
                ", headers=" + headers +
                ", body='" + body + '\'' +
                ", contentLength=" + contentLength +
                ", contentType='" + contentType + '\'' +
                '}';
    }
}
