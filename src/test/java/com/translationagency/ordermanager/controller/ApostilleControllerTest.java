package com.translationagency.ordermanager.controller;

import com.translationagency.ordermanager.AbstractTest;
import com.translationagency.ordermanager.JsonUtil;
import com.translationagency.ordermanager.data.OrderTestData;
import com.translationagency.ordermanager.entity.Apostille;
import com.translationagency.ordermanager.exception_handling.error.ErrorInfo;
import com.translationagency.ordermanager.exception_handling.error.ErrorType;
import com.translationagency.ordermanager.repository.ApostilleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.translationagency.ordermanager.data.ApostilleTestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.translationagency.ordermanager.TestUtil.*;

class ApostilleControllerTest extends AbstractTest {

    @Autowired
    private ApostilleRepository apostilleRepository;

    @Test
    void getAll() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(OrderTestData.URL + "/" +
                                OrderTestData.markOrder.id() + "/apostilles")
                        .with(httpBasic()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        List<Apostille> actual = JsonUtil.readValuesFromJson(getContentAsString(result), Apostille.class);

        assertIterableEquals(markApostilles, actual);
    }

    @Test
    void get() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(OrderTestData.URL + "/" +
                        OrderTestData.markOrder.id() + "/apostilles/" + markOrder_apos.id())
                        .with(httpBasic()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        Apostille actual = JsonUtil.readValueFromJson(getContentAsString(result), Apostille.class);

        assertEquals(markOrder_apos, actual);
    }

    @Test
    void getNotFound() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(OrderTestData.URL + "/" +
                                OrderTestData.markOrder.id() + "/apostilles/" + 88)
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
        Apostille newApos = getNew();
        mockMvc.perform(MockMvcRequestBuilders.post(OrderTestData.URL + "/" +
                                OrderTestData.markOrder.id() + "/apostilles")
                        .with(httpBasic())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValueToJson(newApos)))
                .andDo(print())
                .andExpect(status().isCreated());

        int newId = getNewId();
        newApos.setId(newId);

        assertEquals(newApos, apostilleRepository.findById(newApos.id()).orElse(null));
    }

    @Test
    void createBindViolation() throws Exception {
        Apostille newApos = getNew();
        newApos.setSubmissionCountry(null);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(OrderTestData.URL + "/" +
                                OrderTestData.markOrder.id() + "/apostilles")
                        .with(httpBasic())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValueToJson(newApos)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        ErrorInfo actual = JsonUtil.readValueFromJson(getContentAsString(result), ErrorInfo.class);

        assertEquals(ErrorType.BAD_DATA, actual.type());
    }

    @Test
    void createNotNew() throws Exception {
        Apostille newApos = getNew();
        newApos.setId(88);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(OrderTestData.URL + "/" +
                                OrderTestData.markOrder.id() + "/apostilles")
                        .with(httpBasic())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValueToJson(newApos)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        ErrorInfo actual = JsonUtil.readValueFromJson(getContentAsString(result), ErrorInfo.class);

        assertEquals(ErrorType.BAD_REQUEST, actual.type());
    }

    @Test
    void update() throws Exception {
        Apostille updated = getUpdated();
        mockMvc.perform(MockMvcRequestBuilders.put(OrderTestData.URL + "/" +
                                OrderTestData.stevenOrder.id() + "/apostilles/" + stevenOrder_apos.id())
                        .with(httpBasic())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValueToJson(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertEquals(updated, apostilleRepository.findById(updated.id()).orElse(null));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(OrderTestData.URL + "/" +
                        OrderTestData.stevenOrder.id() + "/apostilles/" + stevenOrder_apos.id())
                        .with(httpBasic()))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertThrows(ClassNotFoundException.class, () -> apostilleRepository.findById(stevenOrder_apos.id())
                .orElseThrow(ClassNotFoundException::new));
    }
}