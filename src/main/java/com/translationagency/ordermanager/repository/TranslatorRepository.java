package com.translationagency.ordermanager.repository;

import com.translationagency.ordermanager.entity.Language;
import com.translationagency.ordermanager.entity.Translator;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface TranslatorRepository extends JpaRepository<Translator, Integer>, PagingAndSortingRepository<Translator, Integer> {

    @Query("SELECT t FROM Translator t LEFT JOIN FETCH t.rates r WHERE t.id=?1")
    Optional<Translator> getWithRates(int id);

    @Query("SELECT t FROM Translator t LEFT JOIN FETCH t.rates")
    List<Translator> getAllWithRates(Pageable pageable);

    @Query("SELECT t FROM Translator t LEFT JOIN FETCH t.rates WHERE t.available = true")
    List<Translator> getAllActiveWithRates(Pageable pageable);

    @Query("SELECT t FROM Translator t LEFT JOIN FETCH t.rates r WHERE t.available = true AND r.language=?1")
    List<Translator> getAllActiveWithRatesByLanguage(Language language);

    @Query("SELECT t FROM Translator t LEFT JOIN FETCH t.rates r WHERE t.available = true AND r.language=?1")
    List<Translator> getAllActiveWithRatesByLanguage(Language language, Pageable pageable);
}