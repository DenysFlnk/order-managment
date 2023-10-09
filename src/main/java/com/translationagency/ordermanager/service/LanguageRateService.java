package com.translationagency.ordermanager.service;

import com.translationagency.ordermanager.entity.LanguageRate;
import com.translationagency.ordermanager.repository.LanguageRateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.translationagency.ordermanager.util.validation.ValidationUtil.*;

@Service
@AllArgsConstructor
public class LanguageRateService {

    private LanguageRateRepository languageRateRepository;

    private TranslatorService translatorService;

    public List<LanguageRate> getAllByTranslator(int translatorId) {
        return languageRateRepository.findAllByTranslatorId(translatorId);
    }

    public LanguageRate get(int translatorId, int id) {
        return checkNotFoundWithId(languageRateRepository.getByTranslatorIdAndId(translatorId, id).orElse(null),
                translatorId);
    }

    public void create(int translatorId, LanguageRate languageRate) {
        checkNew(languageRate);
        languageRate.setTranslator(checkNotFoundWithId(translatorService.getReferenceById(translatorId), translatorId));
        languageRateRepository.save(languageRate);
    }

    public void update(int translatorId, LanguageRate languageRate) {
        languageRate.setTranslator(checkNotFoundWithId(translatorService.getReferenceById(translatorId), translatorId));
        languageRateRepository.save(languageRate);
    }

    public void delete(int translatorId, int id) {
        LanguageRate delete = checkNotFoundWithId(languageRateRepository.getByTranslatorIdAndId(translatorId, id)
                        .orElse(null), translatorId);
        languageRateRepository.delete(delete);
    }
}
