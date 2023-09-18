package com.translationagency.ordermanager.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.translationagency.ordermanager.JsonUtil;
import com.translationagency.ordermanager.entity.Role;
import com.translationagency.ordermanager.entity.User;

import java.util.List;
import java.util.Set;

public class UserTestData {

    public static final String USER_REST_URL = "/rest-api/users";

    public static User normalUser = new User(1, "normal user",null, true, Set.of(Role.USER));

    public static User adminUser = new User(2, "admin", null, true, Set.of(Role.ADMIN, Role.USER));

    public static List<User> allUsers = List.of(normalUser,adminUser);

    public static User getNew() {
        return new User("John", "appleOrange123", true, Set.of(Role.ADMIN, Role.USER));
    }

    public static final int NEW_ID = adminUser.id() + 1;

    public static User getUpdated() {
        User updated = new User(normalUser);
        updated.setName("user");

        return updated;
    }

    public static User getBanned() {
        User updated = new User(normalUser);
        updated.setEnabled(false);

        return updated;
    }

    public static String jsonWithPassword(User user, String password) throws JsonProcessingException {
        return JsonUtil.writeAdditionProps(user, "password", password);
    }
}
