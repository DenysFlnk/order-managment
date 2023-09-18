package com.translationagency.ordermanager.controller;

import com.translationagency.ordermanager.AbstractControllerTest;
import com.translationagency.ordermanager.JsonUtil;
import com.translationagency.ordermanager.entity.User;
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

class UserControllerTest extends AbstractControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void getAll() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(USER_REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        List<User> actual = JsonUtil.readValuesFromJson(getContentAsString(result), User.class);

        assertIterableEquals(allUsers, actual);
    }

    @Test
    void get() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(USER_REST_URL + "/" + adminUser.id()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        User actual = JsonUtil.readValueFromJson(getContentAsString(result), User.class);

        assertEquals(adminUser, actual);
    }

    @Test
    void create() throws Exception {
        User created = getNew();
        mockMvc.perform(MockMvcRequestBuilders.post(USER_REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonWithPassword(created, created.getPassword())))
                .andDo(print())
                .andExpect(status().isCreated());

        User actual = userRepository.findById(NEW_ID).orElse(null);
        created.setId(NEW_ID);

        assertEquals(created, actual);
    }

    @Test
    void update() throws Exception {
        User updated = getUpdated();
        mockMvc.perform(MockMvcRequestBuilders.put(USER_REST_URL + "/" + updated.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValueToJson(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertEquals(updated, userRepository.findById(updated.id()).orElse(null));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(USER_REST_URL + "/" + normalUser.id()))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertThrows(ClassNotFoundException.class, () -> userRepository.findById(normalUser.id())
                .orElseThrow(ClassNotFoundException::new));
    }

    @Test
    void enable() throws Exception {
        User banned = getBanned();
        mockMvc.perform(MockMvcRequestBuilders.patch(USER_REST_URL + "/" + banned.id() +
                        "?isEnabled=false"))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertEquals(banned, userRepository.findById(banned.id()).orElse(null));
    }
}