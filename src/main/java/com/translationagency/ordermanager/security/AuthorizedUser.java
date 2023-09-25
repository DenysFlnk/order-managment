package com.translationagency.ordermanager.security;

import com.translationagency.ordermanager.entity.User;
import jakarta.validation.constraints.NotNull;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User {

    @NotNull
    private final User user;

    public AuthorizedUser(User user) {
        super(user.getName(), user.getPassword(), user.isEnabled(), true,
                true, true, user.getRoles());
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public int id() {
        return user.id();
    }
}
