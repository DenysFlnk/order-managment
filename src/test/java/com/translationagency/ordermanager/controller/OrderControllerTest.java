package com.translationagency.ordermanager.controller;

import com.translationagency.ordermanager.AbstractTest;
import com.translationagency.ordermanager.JsonUtil;
import com.translationagency.ordermanager.entity.Order;
import com.translationagency.ordermanager.repository.OrderRepository;
import com.translationagency.ordermanager.to.order.OrderDetailTo;
import com.translationagency.ordermanager.to.order.OrderTo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.translationagency.ordermanager.data.OrderTestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.translationagency.ordermanager.TestUtil.*;

class OrderControllerTest extends AbstractTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void getAll() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(URL +
                        "?page=0&size=10")
                        .with(httpBasic()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        List<OrderTo> actual = JsonUtil.readValuesFromJson(getContentAsString(result), OrderTo.class);

        assertIterableEquals(firstTen, actual);
    }

    @Test
    void getAllCount() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(URL + "/count")
                        .with(httpBasic()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        Integer actual = JsonUtil.readValueFromJson(getContentAsString(result), Integer.class);

        assertEquals(ALL_ORDERS_COUNT, actual);
    }

    @Test
    void get() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(URL + "/2")
                        .with(httpBasic()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        OrderDetailTo actual = JsonUtil.readValueFromJson(getContentAsString(result), OrderDetailTo.class);

        assertEquals(getDetailTo(), actual);
    }

    @Test
    void create() throws Exception {
        Order newOrder = getNew();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(URL)
                        .with(httpBasic())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValueToJson(newOrder)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        String newLocation = result.getResponse().getHeader("Location");

        int newId = getNewId();
        assertEquals("http://localhost/orders/" + newId, newLocation);

        Order actual = orderRepository.findById(newId).orElse(null);
        newOrder.setId(newId);
        assertEquals(newOrder, actual);
    }

    @Test
    void update() throws Exception {
        Order updated = getUpdated();

        mockMvc.perform(MockMvcRequestBuilders.put(URL + "/" + updated.id())
                        .with(httpBasic())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValueToJson(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertEquals(updated, orderRepository.findById(updated.id()).orElse(null));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/" + karenOrder.id())
                        .with(httpBasic()))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertThrows(ClassNotFoundException.class, () -> orderRepository.findById(karenOrder.id())
                .orElseThrow(ClassNotFoundException::new));
    }
}