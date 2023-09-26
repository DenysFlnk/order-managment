package com.translationagency.ordermanager;

import com.translationagency.ordermanager.entity.Role;
import com.translationagency.ordermanager.entity.User;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.util.Set;

public class TestUtil {

    private static final User user = new User("admin", "password", true, Set.of(Role.ADMIN, Role.USER));

    public static RequestPostProcessor httpBasic() {
        return SecurityMockMvcRequestPostProcessors.httpBasic(user.getName(), user.getPassword());
    }
}
