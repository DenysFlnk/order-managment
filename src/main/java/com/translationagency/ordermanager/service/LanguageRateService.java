package com.translationagency.ordermanager.service;

import com.translationagency.ordermanager.entity.LanguageRate;
import com.translationagency.ordermanager.repository.LanguageRateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class LanguageRateService {

    private LanguageRateRepository languageRateRepository;

    private TranslatorService translatorService;

    public List<LanguageRate> getAllByTranslator(int translatorId) {
        return languageRateRepository.findAllByTranslatorId(translatorId);
    }

    public LanguageRate get(int translatorId, int id) {
        return languageRateRepository.getByTranslatorIdAndId(translatorId, id)
                .orElseThrow(() -> new NoSuchElementException("Not found"));
    }

    public void create(int translatorId, LanguageRate languageRate) {
        languageRate.setTranslator(translatorService.getReferenceById(translatorId));
        languageRateRepository.save(languageRate);
    }

    public void update(int translatorId, LanguageRate languageRate) {
        languageRate.setTranslator(translatorService.getReferenceById(translatorId));
        languageRateRepository.save(languageRate);
    }

    public void delete(int translatorId, int id) {
        LanguageRate delete = languageRateRepository.getByTranslatorIdAndId(translatorId, id)
                .orElseThrow(() -> new NoSuchElementException("Not found"));
        languageRateRepository.delete(delete);
    }
}
