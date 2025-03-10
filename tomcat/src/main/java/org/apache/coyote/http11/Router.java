package org.apache.coyote.http11;

import org.apache.coyote.http11.parser.UriParser;

import java.io.BufferedReader;
import java.io.IOException;

public class Router {
    public static String route(final BufferedReader bufferedReader) throws IOException {
        String url = bufferedReader.readLine().split(" ")[1];
        if(isFile(url)) {
            return FilePath.of(url).getResponse();
        }
        try {
            UriParser parsedUri = new UriParser(url);
            return ServerPath.of(parsedUri.getUrl()).execute(parsedUri.getQueryStrings());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isFile(String route) {
        return route.matches(".*\\.(html|css|js|png|jpg|gif|ico|svg)$");
    }
}
