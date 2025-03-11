package org.apache.coyote.http11.handler;

import com.techcourse.db.InMemoryUserRepository;
import com.techcourse.model.User;
import org.apache.coyote.http11.FilePath;

import java.io.IOException;
import java.util.Map;

public class LoginHandler implements ActionHandler {
    @Override
    public String action() {
        return "";
    }

    @Override
    public String action(Map<String, String> queryString) throws IOException {
        String account = queryString.get("account");
        String password = queryString.get("password");
        User user = InMemoryUserRepository.findByAccount(account).orElseThrow(() -> new IllegalArgumentException("user not found"));
        if (user.checkPassword(password)) {
            return FilePath.of("/index.html").getResponse();
        }
        throw new IllegalArgumentException("user not found");
    }
}
