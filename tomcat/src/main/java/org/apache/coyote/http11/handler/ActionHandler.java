package org.apache.coyote.http11.handler;

import java.io.IOException;
import java.util.Map;

public interface ActionHandler {
    String action();
    String action(Map<String, String> queryString) throws IOException;
}