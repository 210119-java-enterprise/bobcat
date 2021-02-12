package com.revature.bobcat.util;

import java.util.Map;

public class HttpRequest {

    private String httpMethod; // GET, POST, PUT, PATCH, DELETE, etc.
    private String httpVersion;
    private String uri;
    private Map<String, String> headers;
    private String body;
}
