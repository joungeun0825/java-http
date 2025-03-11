package org.apache.coyote.http11;

import org.apache.catalina.connector.HttpRequest;
import java.io.IOException;

public class Router {
    public static String route(HttpRequest httpRequest) throws IOException {
        if(isFile(httpRequest.getRequestURI())) {
            return FilePath.of(httpRequest.getRequestURI()).getResponse();
        }
        try {
            return ServerPath.of(httpRequest.getRequestURI()).execute(httpRequest.getParameters());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isFile(String route) {
        return route.matches(".*\\.(html|css|js|png|jpg|gif|ico|svg)$");
    }
}
