package com.translationagency.ordermanager.controller;

import com.translationagency.ordermanager.AbstractTest;
import com.translationagency.ordermanager.JsonUtil;
import com.translationagency.ordermanager.entity.User;
import com.translationagency.ordermanager.exception_handling.error.ErrorInfo;
import com.translationagency.ordermanager.exception_handling.error.ErrorType;
import com.translationagency.ordermanager.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.translationagency.ordermanager.data.UserTestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.translationagency.ordermanager.TestUtil.*;

class UserControllerTest extends AbstractTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void getAll() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(USER_REST_URL)
                        .with(httpBasic()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        List<User> actual = JsonUtil.readValuesFromJson(getContentAsString(result), User.class);

        assertIterableEquals(allUsers, actual);
    }

    @Test
    void get() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(USER_REST_URL + "/" + adminUser.id())
                        .with(httpBasic()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        User actual = JsonUtil.readValueFromJson(getContentAsString(result), User.class);

        assertEquals(adminUser, actual);
    }

    @Test
    void getNotFound() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(USER_REST_URL + "/" + 0)
                        .with(httpBasic()))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        ErrorInfo actual = JsonUtil.readValueFromJson(getContentAsString(result), ErrorInfo.class);

        assertEquals(ErrorType.NOT_FOUND, actual.type());
    }

    @Test
    void create() throws Exception {
        User created = getNew();
        mockMvc.perform(MockMvcRequestBuilders.post(USER_REST_URL)
                        .with(httpBasic())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonWithPassword(created, created.getPassword())))
                .andDo(print())
                .andExpect(status().isCreated());

        User actual = userRepository.findById(NEW_ID).orElse(null);
        created.setId(NEW_ID);

        assertEquals(created, actual);
    }

    @Test
    void createBindViolation() throws Exception {
        User created = getNew();
        created.setName(null);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(USER_REST_URL)
                        .with(httpBasic())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonWithPassword(created, created.getPassword())))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        ErrorInfo actual = JsonUtil.readValueFromJson(getContentAsString(result), ErrorInfo.class);

        assertEquals(ErrorType.BAD_DATA, actual.type());
    }

    @Test
    void createNotNew() throws Exception {
        User created = getNew();
        created.setId(0);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(USER_REST_URL)
                        .with(httpBasic())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonWithPassword(created, created.getPassword())))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        ErrorInfo actual = JsonUtil.readValueFromJson(getContentAsString(result), ErrorInfo.class);

        assertEquals(ErrorType.BAD_REQUEST, actual.type());
    }

    @Test
    void update() throws Exception {
        User updated = getUpdated();
        mockMvc.perform(MockMvcRequestBuilders.put(USER_REST_URL + "/" + updated.id())
                        .with(httpBasic())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValueToJson(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertEquals(updated, userRepository.findById(updated.id()).orElse(null));
    }

    @Test
    void updateWithoutPassword() throws Exception {
        User updated = getUpdated();
        updated.setPassword(null);
        mockMvc.perform(MockMvcRequestBuilders.put(USER_REST_URL + "/" + updated.id())
                        .with(httpBasic())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValueToJson(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        User actual = userRepository.findById(updated.id()).orElse(null);

        assertEquals(updated.getName(), actual.getName());
        assertIterableEquals(updated.getRoles(), actual.getRoles());

        String expectedPassword = "{noop}" + getUpdated().getPassword();
        assertEquals(expectedPassword, actual.getPassword());
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(USER_REST_URL + "/" + normalUser.id())
                        .with(httpBasic()))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertThrows(ClassNotFoundException.class, () -> userRepository.findById(normalUser.id())
                .orElseThrow(ClassNotFoundException::new));
    }

    @Test
    void enable() throws Exception {
        User banned = getBanned();
        mockMvc.perform(MockMvcRequestBuilders.patch(USER_REST_URL + "/" + banned.id() +
                        "?isEnabled=false")
                        .with(httpBasic()))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertEquals(banned, userRepository.findById(banned.id()).orElse(null));
    }
}