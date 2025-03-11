package org.apache.coyote.http11;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.util.Arrays;

public enum FilePath {
    DEFAULT_PAGE("/"),
    INDEX_PAGE("/index.html"),
    INDEX_CSS("/css/styles.css"),
    INDEX_ASSETS_AREA("/assets/chart-area.js"),
    INDEX_ASSETS_BAR("/assets/chart-bar.js"),
    INDEX_ASSETS_PIE("/assets/chart-pie.js"),
    INDEX_JS("/js/scripts.js"),
    LOGIN_PAGE("/login.html");

    private static final String ERROR_MESSAGE = "request not found file -> path: %s";

    private final String path;

    FilePath(final String path){
        this.path = path;
    }

    public static FilePath of(final String path) {
        return Arrays.stream(FilePath.values())
                .filter(responseBody -> path.equals(responseBody.path))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format(ERROR_MESSAGE, path)));
    }

    public String getResponse() throws IOException {
        if (this == DEFAULT_PAGE) {
            return "Hello world!";
        }

        final File file = new File(URLDecoder.decode(getClass().getClassLoader().getResource("static" + path).getPath(), "UTF-8"));
        final ContentType contentType = findContentType(this.path);
        String fileContents = new String(Files.readAllBytes(file.toPath()));
        final var response = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: " + contentType.getValue() +";charset=utf-8 ",
                "Content-Length: " + fileContents.getBytes().length + " ",
                "",
                fileContents);
        return response;
    }

    private ContentType findContentType(final String extractedPath) {
        final String[] pathInfos = extractedPath.split("\\.");
        final String fileExtension = pathInfos[pathInfos.length - 1];
        return ContentType.of(fileExtension);
    }

}
