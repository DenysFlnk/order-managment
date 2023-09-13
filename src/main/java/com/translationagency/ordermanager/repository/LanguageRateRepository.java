package com.translationagency.ordermanager.repository;

import com.translationagency.ordermanager.entity.LanguageRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LanguageRateRepository extends JpaRepository<LanguageRate, Integer> {

    List<LanguageRate> findAllByTranslatorId(int translatorId);

    Optional<LanguageRate> getByTranslatorIdAndId(int translatorId, int id);
}
