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

    public List<Translator> getAll(Pageable pageable) {
        return translatorRepository.getAllWithRates(pageable);
    }

    public int getAllCount() {
        return translatorRepository.findAll().size();
    }

    public List<Translator> getAllActive(Pageable pageable) {
        return translatorRepository.getAllActiveWithRates(pageable);
    }

    public List<Translator> getAllActiveByLanguage(Language language, Pageable pageable) {
        return translatorRepository.getAllActiveWithRatesByLanguage(language, pageable);
    }

    public int getAllActiveByLanguageCount(Language language) {
        return translatorRepository.getAllActiveWithRatesByLanguage(language).size();
    }

    public Translator get(int id) {
        return translatorRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }

    public Translator getWithRates(int id) {
        return translatorRepository.getWithRates(id).orElseThrow(() -> new RuntimeException("Not found"));
    }

    public Translator getReferenceById(int id) {
        return translatorRepository.getReferenceById(id);
    }

    public void create(Translator translator) {
        translatorRepository.save(translator);
    }

    public void update(Translator translator) {
        translatorRepository.save(translator);
    }

    public void delete(int id) {
        Translator delete = translatorRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        translatorRepository.delete(delete);
    }

    public void changeAvailability(int id, boolean isAvailable) {
        Translator translator = translatorRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        translator.setAvailable(isAvailable);
        translatorRepository.save(translator);
    }
}
