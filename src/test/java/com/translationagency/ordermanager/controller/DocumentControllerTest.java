package com.translationagency.ordermanager.controller;

import com.translationagency.ordermanager.AbstractTest;
import com.translationagency.ordermanager.JsonUtil;
import com.translationagency.ordermanager.TestUtil;
import com.translationagency.ordermanager.data.OrderTestData;
import com.translationagency.ordermanager.data.TranslatorTestData;
import com.translationagency.ordermanager.entity.Document;
import com.translationagency.ordermanager.exception_handling.error.ErrorInfo;
import com.translationagency.ordermanager.exception_handling.error.ErrorType;
import com.translationagency.ordermanager.repository.DocumentRepository;
import com.translationagency.ordermanager.to.document.DocumentTo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.translationagency.ordermanager.TestUtil.httpBasic;
import static com.translationagency.ordermanager.data.DocumentTestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DocumentControllerTest extends AbstractTest {

    @Autowired
    private DocumentRepository documentRepository;

    @Test
    void getAll() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(OrderTestData.URL + "/" +
                                OrderTestData.carolOrder.id() + "/documents")
                        .with(httpBasic()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        List<DocumentTo> actual = JsonUtil.readValuesFromJson(getContentAsString(result), DocumentTo.class);

        assertIterableEquals(carolDocsTo, actual);
    }

    @Test
    void get() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(OrderTestData.URL + "/" +
                                OrderTestData.michelleOrder.id() + "/documents/" + michelleOrder_doc.id())
                        .with(httpBasic()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        DocumentTo actual = JsonUtil.readValueFromJson(getContentAsString(result), DocumentTo.class);

        assertEquals(getTo(), actual);
    }

    @Test
    void getNotFound() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(OrderTestData.URL + "/" +
                                OrderTestData.michelleOrder.id() + "/documents/" + 88)
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
        Document newDoc = getNew();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(OrderTestData.URL + "/" +
                                OrderTestData.joyOrder.id() + "/documents")
                        .with(httpBasic())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValueToJson(newDoc)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        Document actual = JsonUtil.readValueFromJson(getContentAsString(result), Document.class);
        newDoc.setId(actual.getId());

        assertEquals(newDoc, documentRepository.findById(newDoc.id()).orElse(null));
        assertEquals(newDoc, actual);
    }

    @Test
    void createNotNew() throws Exception {
        Document newDoc = getNew();
        newDoc.setId(88);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(OrderTestData.URL + "/" +
                                OrderTestData.joyOrder.id() + "/documents")
                        .with(httpBasic())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValueToJson(newDoc)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        ErrorInfo actual = JsonUtil.readValueFromJson(getContentAsString(result), ErrorInfo.class);

        assertEquals(ErrorType.BAD_REQUEST, actual.type());
    }

    @Test
    void update() throws Exception {
        Document updated = getUpdated();
        mockMvc.perform(MockMvcRequestBuilders.put(OrderTestData.URL + "/" +
                                OrderTestData.michelleOrder.id() + "/documents/" + michelleOrder_doc.id())
                        .with(httpBasic())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValueToJson(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertEquals(updated, documentRepository.findById(updated.id()).orElse(null));
    }

    @Test
    void updateNotConsistentId() throws Exception {
        Document updated = getUpdated();
        updated.setId(88);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(OrderTestData.URL + "/" +
                                OrderTestData.michelleOrder.id() + "/documents/" + michelleOrder_doc.id())
                        .with(httpBasic())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValueToJson(updated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        ErrorInfo actual = JsonUtil.readValueFromJson(getContentAsString(result), ErrorInfo.class);

        assertEquals(ErrorType.BAD_REQUEST, actual.type());
    }

    @Test
    void updateBindViolation() throws Exception {
        Document updated = getUpdated();
        updated.setDocumentLanguage(null);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(OrderTestData.URL + "/" +
                                OrderTestData.michelleOrder.id() + "/documents/" + michelleOrder_doc.id())
                        .with(httpBasic())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValueToJson(updated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        ErrorInfo actual = JsonUtil.readValueFromJson(getContentAsString(result), ErrorInfo.class);

        assertEquals(ErrorType.BAD_DATA, actual.type());
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(OrderTestData.URL + "/" +
                                OrderTestData.michelleOrder.id() + "/documents/" + michelleOrder_doc.id())
                        .with(httpBasic()))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertThrows(ClassNotFoundException.class, () -> documentRepository.findById(michelleOrder_doc.id())
                .orElseThrow(ClassNotFoundException::new));
    }

    @Test
    void changeComplexity() throws Exception {
        Document updated = getWithChangedComplexity();
        mockMvc.perform(MockMvcRequestBuilders.patch(OrderTestData.URL + "/" +
                                OrderTestData.sarahOrder.id() + "/documents/" + sarahOrder_doc.id() +
                                "/complexity?isHardComplexity=true&updateRate=true")
                        .with(httpBasic()))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertEquals(updated, documentRepository.findById(updated.id()).orElse(null));
    }

    @Test
    void changeTranslator() throws Exception {
        Document updated = getWithChangedTranslator();
        mockMvc.perform(MockMvcRequestBuilders.patch(OrderTestData.URL + "/" +
                                OrderTestData.sarahOrder.id() + "/documents/" + sarahOrder_doc.id() +
                                "/translator?translatorId=" + TranslatorTestData.jared.id())
                        .with(httpBasic()))
                .andDo(print())
                .andExpect(status().isNoContent());

        TestUtil.assertDocumentTranslator(updated, documentRepository.findById(updated.id())
                .orElseThrow(ClassNotFoundException::new));
    }
}