package com.translationagency.ordermanager.controller;

import com.translationagency.ordermanager.entity.Language;
import com.translationagency.ordermanager.entity.Translator;
import com.translationagency.ordermanager.service.TranslatorService;
import com.translationagency.ordermanager.to.TranslatorTo;
import com.translationagency.ordermanager.util.TranslatorUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = TranslatorController.TRANSLATOR_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class TranslatorController {

    public static final String TRANSLATOR_REST_URL = "rest-api/translators";

    private TranslatorService translatorService;

    @GetMapping("/document")
    public List<TranslatorTo> getAllActiveByLanguage(@RequestParam String language,
                                                     @RequestParam boolean isHardComplexity,
                                                     @RequestParam(defaultValue = "0") Integer page,
                                                     @RequestParam(defaultValue = "5") Integer size) {
        log.info("getAllActiveByLanguage language {}, isHardComplexity {}, page {}, size {}", language,
                isHardComplexity, page, size);

        PageRequest pageRequest = PageRequest.of(page, size);

        Language enumLanguage = Language.valueOf(language);
        List<Translator> translators = translatorService.getAllActiveByLanguageCount(enumLanguage, pageRequest);

        return TranslatorUtil.getTos(enumLanguage, isHardComplexity, translators);
    }

    @GetMapping("/document/count")
    public int getAllActiveByLanguageCount(@RequestParam String language) {
        log.info("getAllActiveByLanguageCount language {}", language);

        return translatorService.getAllActiveByLanguageCount(Language.valueOf(language));
    }

}
