package com.translationagency.ordermanager.util;

import com.translationagency.ordermanager.entity.User;

public class UserUtil {
    public static void setSHA1Encoder(User user) {
        String passwordWithEncoder = "{SHA-1}" + user.getPassword();
        user.setPassword(passwordWithEncoder);
    }
}
