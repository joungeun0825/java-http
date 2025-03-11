package org.apache.catalina.connector;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.Principal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private final String method;
    private final String uri;
    private final Map<String, String> headers;
    private final Map<String, String> parameters;
    private final String body;

    public HttpRequest(String requestText) throws IOException {
        System.out.println(requestText);
        headers = new HashMap<>();
        parameters = new HashMap<>();

        // HTTP 요청 라인 파싱
        String[] lines = requestText.split("\r\n");
        String[] requestLine = lines[0].split(" ");
        this.method = requestLine[0];
        this.uri = requestLine[1];

        // 헤더 파싱
        int i = 1;
        while (i < lines.length && !lines[i].isEmpty()) {
            String[] header = lines[i].split(": ");
            headers.put(header[0], header[1]);
            i++;
        }

        // Body 파싱 (POST 요청 시)
        this.body = lines[lines.length - 1];
    }

    public String getMethod() {
        return method;
    }

    public String getRequestURI() {
        return uri;
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    public String getParameter(String name) {
        return parameters.get(name);
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public String getContentType() {
        return getHeader("Content-Type");
    }

    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(new InputStream() {

            public int read() throws IOException {
                return -1;
            }
        }));
    }

    public Cookie[] getCookies() {
        String cookieHeader = headers.get("Cookie");
        if (cookieHeader == null) return null;

        String[] cookiePairs = cookieHeader.split("; ");
        Cookie[] cookies = new Cookie[cookiePairs.length];
        for (int i = 0; i < cookiePairs.length; i++) {
            String[] cookie = cookiePairs[i].split("=");
            cookies[i] = new Cookie(cookie[0], cookie[1]);
        }
        return cookies;
    }

    public String getAuthType() { return null; }

    public HttpSession getSession() { return null; }

    public Principal getUserPrincipal() { return null; }

    public Enumeration<String> getHeaderNames() {
        return java.util.Collections.enumeration(headers.keySet());
    }
}
