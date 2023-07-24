package com.translationagency.ordermanager.repository;

import com.translationagency.ordermanager.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Integer> {
}
