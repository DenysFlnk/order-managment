package com.translationagency.ordermanager.controller;

import com.translationagency.ordermanager.entity.LanguageRate;
import com.translationagency.ordermanager.service.LanguageRateService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.translationagency.ordermanager.util.validation.ValidationUtil.*;

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

    @GetMapping("/{rateId}")
    public LanguageRate get(@PathVariable int id, @PathVariable int rateId) {
        log.info("get for translator {}, rate {}", id, rateId);
        return languageRateService.get(id, rateId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@PathVariable int id, @Valid @RequestBody LanguageRate rate) {
        log.info("create for translator {}, rate {}", id, rate);
        languageRateService.create(id, rate);
    }

    @PutMapping("/{rateId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int id, @PathVariable int rateId, @Valid @RequestBody LanguageRate rate) {
        log.info("update rate {} for translator {} with data {}", rateId, id, rate);
        assureIdConsistent(rate, rateId);
        languageRateService.update(id, rate);
    }

    @DeleteMapping("/{rateId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @PathVariable int rateId) {
        log.info("delete rate {}, translator {}", rateId, id);
        languageRateService.delete(id, rateId);
    }
}
