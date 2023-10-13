package com.translationagency.ordermanager.controller;

import com.translationagency.ordermanager.AbstractTest;
import com.translationagency.ordermanager.JsonUtil;
import com.translationagency.ordermanager.entity.Translator;
import com.translationagency.ordermanager.exception_handling.error.ErrorInfo;
import com.translationagency.ordermanager.exception_handling.error.ErrorType;
import com.translationagency.ordermanager.repository.TranslatorRepository;
import com.translationagency.ordermanager.to.translator.TranslatorManageTo;
import com.translationagency.ordermanager.to.translator.TranslatorTo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.translationagency.ordermanager.data.TranslatorTestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.translationagency.ordermanager.TestUtil.*;

class TranslatorControllerTest extends AbstractTest {

    @Autowired
    private TranslatorRepository translatorRepository;

    @Test
    void getAll() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(TRANSLATOR_REST_URL +
                        "?page=0&size=10")
                        .with(httpBasic()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        List<TranslatorManageTo> actual = JsonUtil.readValuesFromJson(getContentAsString(result), TranslatorManageTo.class);

        assertIterableEquals(firstTenManageTos, actual);
    }

    @Test
    void getAllCount() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(TRANSLATOR_REST_URL +
                        "/count")
                        .with(httpBasic()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        Integer actual = JsonUtil.readValueFromJson(getContentAsString(result), Integer.class);

        assertEquals(ALL_COUNT, actual);
    }

    @Test
    void get() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(TRANSLATOR_REST_URL +
                        "/" + jared.id())
                        .with(httpBasic()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        Translator actual = JsonUtil.readValueFromJson(getContentAsString(result), Translator.class);

        assertEquals(jared, actual);
    }

    @Test
    void getAllActiveByLanguage() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(TRANSLATOR_REST_URL +
                        "/document" + "?language=ENGLISH&isHardComplexity=false&page=0&size=3")
                        .with(httpBasic()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        List<TranslatorTo> actual = JsonUtil.readValuesFromJson(getContentAsString(result), TranslatorTo.class);

        assertIterableEquals(firstThreeEnglishTranslatorTos, actual);
    }

    @Test
    void getAllActiveByLanguageCount() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(TRANSLATOR_REST_URL +
                        "/document/count?language=ENGLISH")
                        .with(httpBasic()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        Integer actual = JsonUtil.readValueFromJson(getContentAsString(result), Integer.class);

        assertEquals(ENGLISH_COUNT, actual);
    }

    @Test
    void create() throws Exception {
        Translator created = getNew();
        mockMvc.perform(MockMvcRequestBuilders.post(TRANSLATOR_REST_URL)
                        .with(httpBasic())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValueToJson(created)))
                .andDo(print())
                .andExpect(status().isCreated());

        Translator actual = translatorRepository.findById(NEW_ID).orElse(null);
        created.setId(NEW_ID);

        assertEquals(created, actual);
    }

    @Test
    void createNotNew() throws Exception {
        Translator created = getNew();
        created.setId(0);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(TRANSLATOR_REST_URL)
                        .with(httpBasic())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValueToJson(created)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andReturn();

        ErrorInfo actual = JsonUtil.readValueFromJson(getContentAsString(result), ErrorInfo.class);

        assertEquals(ErrorType.BAD_REQUEST, actual.type());
    }

    @Test
    void update() throws Exception {
        Translator updated = getUpdated();
        mockMvc.perform(MockMvcRequestBuilders.put(TRANSLATOR_REST_URL + "/" + updated.id())
                        .with(httpBasic())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValueToJson(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        Translator actual = translatorRepository.findById(updated.id()).orElse(null);

        assertEquals(updated, actual);
    }

    @Test
    void updateBindViolation() throws Exception {
        Translator updated = getUpdated();
        updated.setName(null);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(TRANSLATOR_REST_URL + "/" + updated.id())
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
        mockMvc.perform(MockMvcRequestBuilders.delete(TRANSLATOR_REST_URL + "/" + jared.id())
                        .with(httpBasic()))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertThrows(ClassNotFoundException.class, () -> translatorRepository.findById(jared.id())
                .orElseThrow(ClassNotFoundException::new));
    }

    @Test
    void deleteNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(TRANSLATOR_REST_URL + "/" + 0)
                        .with(httpBasic()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void changeAvailability() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch(TRANSLATOR_REST_URL + "/" + jared.id() +
                        "?isAvailable=false")
                        .with(httpBasic()))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertEquals(getWithChangedAvailability(), translatorRepository.findById(jared.id()).orElse(null));
    }
}