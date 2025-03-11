package org.apache.coyote.http11;

import org.apache.coyote.http11.handler.ActionHandler;
import org.apache.coyote.http11.handler.LoginHandler;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum ServerPath {
    LOGIN("/login", new LoginHandler());

    private static final Map<String, ServerPath> pathMap =
            Arrays.stream(values())
                    .collect(Collectors.toMap(p -> p.path, Function.identity()));

    private final String path;
    private final ActionHandler handler;

    ServerPath(final String path, final ActionHandler handler){
        this.path = path;
        this.handler = handler;
    }

    public static ServerPath of(String path) {
        return Optional.ofNullable(pathMap.get(path))
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                String.format("존재하지 않는 경로입니다. -> path: %s", path)
                        )
                );
    }

    public String execute() {
        return handler.action();
    }

    public String execute(Map<String, String> parameters) throws IOException {
        return handler.action(parameters);
    }
}
