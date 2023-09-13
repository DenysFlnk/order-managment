package com.translationagency.ordermanager.controller;

import com.translationagency.ordermanager.entity.Language;
import com.translationagency.ordermanager.entity.Translator;
import com.translationagency.ordermanager.service.TranslatorService;
import com.translationagency.ordermanager.to.translator.TranslatorManageTo;
import com.translationagency.ordermanager.to.translator.TranslatorTo;
import com.translationagency.ordermanager.util.TranslatorUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = TranslatorController.TRANSLATOR_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class TranslatorController {

    public static final String TRANSLATOR_REST_URL = "rest-api/translators";

    private TranslatorService translatorService;

    @GetMapping
    public List<TranslatorManageTo> getAll(@RequestParam(defaultValue = "0") Integer page,
                                           @RequestParam(defaultValue = "10") Integer size) {
        log.info("getAll page {}, size {}", page, size);
        PageRequest pageRequest = PageRequest.of(page, size);
        return TranslatorUtil.getManageTos(translatorService.getAll(pageRequest));
    }

    @GetMapping("/count")
    public int getAllCount() {
        log.info("getAllCount");
        return translatorService.getAllCount();
    }

    @GetMapping("/{id}")
    public Translator get(@PathVariable int id) {
        log.info("get {}", id);
        return translatorService.get(id);
    }

    @GetMapping("/document")
    public List<TranslatorTo> getAllActiveByLanguage(@RequestParam String language,
                                                     @RequestParam Boolean isHardComplexity,
                                                     @RequestParam(defaultValue = "0") Integer page,
                                                     @RequestParam(defaultValue = "5") Integer size) {
        log.info("getAllActiveByLanguage language {}, isHardComplexity {}, page {}, size {}", language,
                isHardComplexity, page, size);

        PageRequest pageRequest = PageRequest.of(page, size);

        Language enumLanguage = Language.valueOf(language);
        List<Translator> translators = translatorService.getAllActiveByLanguage(enumLanguage, pageRequest);

        return TranslatorUtil.getTos(enumLanguage, isHardComplexity, translators);
    }

    @GetMapping("/document/count")
    public int getAllActiveByLanguageCount(@RequestParam String language) {
        log.info("getAllActiveByLanguageCount language {}", language);

        return translatorService.getAllActiveByLanguageCount(Language.valueOf(language));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Translator translator) {
        log.info("create {}", translator);
        translatorService.create(translator);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int id, @RequestBody Translator translator) {
        log.info("update id {}, data {}", id, translator);
        translatorService.update(translator);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete id {}", id);
        translatorService.delete(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeAvailability(@PathVariable int id, @RequestParam boolean isAvailable) {
        log.info("changeAvailability for id {}, available {}", id, isAvailable);
        translatorService.changeAvailability(id, isAvailable);
    }
}
