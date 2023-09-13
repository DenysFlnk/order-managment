package com.translationagency.ordermanager.controller;

import com.translationagency.ordermanager.entity.LanguageRate;
import com.translationagency.ordermanager.service.LanguageRateService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = LanguageRateController.LANGUAGE_RATE_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class LanguageRateController {

    public static final String LANGUAGE_RATE_REST_URL = "rest-api/translators/{id}/language-rates";

    private LanguageRateService languageRateService;

    @GetMapping
    public List<LanguageRate> getAllByTranslator(@PathVariable int id) {
        log.info("getAllByTranslator id {}", id);
        return languageRateService.getAllByTranslator(id);
    }
}
