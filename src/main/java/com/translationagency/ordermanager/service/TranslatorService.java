package com.translationagency.ordermanager.service;

import com.translationagency.ordermanager.entity.Language;
import com.translationagency.ordermanager.entity.Translator;
import com.translationagency.ordermanager.repository.TranslatorRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TranslatorService {

    private TranslatorRepository translatorRepository;

    public List<Translator> getAllActive(Pageable pageable) {
        return translatorRepository.getAllActiveWithRates(pageable);
    }

    public int getAllActiveByLanguageCount(Language language) {
        return translatorRepository.getAllActiveWithRatesByLanguage(language).size();
    }

    public List<Translator> getAllActiveByLanguageCount(Language language, Pageable pageable) {
        return translatorRepository.getAllActiveWithRatesByLanguage(language, pageable);
    }

    public Translator get(int id) {
        return translatorRepository.getWithRates(id).orElseThrow(() -> new RuntimeException("Not found"));
    }

    public Translator getReferenceById(int id) {
        return translatorRepository.getReferenceById(id);
    }
}
