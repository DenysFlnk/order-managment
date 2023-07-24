package com.translationagency.ordermanager.repository;

import com.translationagency.ordermanager.entity.Translator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TranslatorRepository extends JpaRepository<Translator, Integer> {
}
