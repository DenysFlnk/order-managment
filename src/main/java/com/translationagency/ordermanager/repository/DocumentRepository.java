package com.translationagency.ordermanager.repository;

import com.translationagency.ordermanager.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Integer> {

    List<Document> getAllByOrderId(int orderId);

    Optional<Document> getDocumentByIdAndOrderId(int documentId, int orderId);
}
