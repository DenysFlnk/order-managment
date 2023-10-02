package com.translationagency.ordermanager;

import com.translationagency.ordermanager.entity.Document;
import com.translationagency.ordermanager.entity.Role;
import com.translationagency.ordermanager.entity.Translator;
import com.translationagency.ordermanager.entity.User;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TestUtil {

    private static final User user = new User("admin", "password", true, Set.of(Role.ADMIN, Role.USER));

    public static RequestPostProcessor httpBasic() {
        return SecurityMockMvcRequestPostProcessors.httpBasic(user.getName(), user.getPassword());
    }

    public static void assertDocumentTranslator(Document expected, Document actual) {
        Translator expectedTranslator = expected.getTranslator();
        Translator actualTranslator = actual.getTranslator();

        assertEquals(expectedTranslator.getName(), actualTranslator.getName());
        assertEquals(expectedTranslator.getEmail(), actualTranslator.getEmail());
        assertEquals(expectedTranslator.getPhoneNumber(), actualTranslator.getPhoneNumber());
        assertEquals(expectedTranslator.isAvailable(), actualTranslator.isAvailable());
    }
}
