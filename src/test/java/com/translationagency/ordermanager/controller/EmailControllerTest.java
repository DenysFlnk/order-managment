package com.translationagency.ordermanager.controller;

import com.translationagency.ordermanager.AbstractTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.translationagency.ordermanager.TestUtil.httpBasic;
import static com.translationagency.ordermanager.data.EmailTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class EmailControllerTest extends AbstractTest {

    @Disabled
    @Test
    void sendEmailWithAttachments() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.multipart("/translators/email")
                        .file(file1)
                        .file(file2)
                        .params(allParams)
                        .with(httpBasic()))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }
}