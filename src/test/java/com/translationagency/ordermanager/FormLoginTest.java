package com.translationagency.ordermanager;

import com.translationagency.ordermanager.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.translationagency.ordermanager.data.UserTestData.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("loginTest")
public class FormLoginTest extends AbstractTest {

    private final String LOGIN_CHECK_URL = "/security-check";

    @Test
    void successLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(LOGIN_CHECK_URL)
                        .with(csrf())
                        .param("username", adminUser.getName())
                        .param("password", adminUser.getPassword()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/orders"));
    }

    @Test
    void failLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(LOGIN_CHECK_URL)
                        .with(csrf())
                        .param("username", invalidUser.getName())
                        .param("password", invalidUser.getPassword()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login-form?error"));
    }

    @Test
    void successAuthorization() throws Exception {
        MvcResult result = getLoginResult(adminUser);
        MockHttpSession session = (MockHttpSession) result.getRequest().getSession();

        mockMvc.perform(MockMvcRequestBuilders.get("/users").session(session))
                .andExpect(status().isOk());
    }

    @Test
    void failAuthorization() throws Exception {
        MvcResult result = getLoginResult(normalUser);
        MockHttpSession session = (MockHttpSession) result.getRequest().getSession();

        mockMvc.perform(MockMvcRequestBuilders.get("/users").session(session))
                .andExpect(status().is4xxClientError());
    }

    private MvcResult getLoginResult(User user) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post(LOGIN_CHECK_URL)
                        .with(csrf())
                        .param("username", user.getName())
                        .param("password", user.getPassword()))
                .andReturn();
    }
}
