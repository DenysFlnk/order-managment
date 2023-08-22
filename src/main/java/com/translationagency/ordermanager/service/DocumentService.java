package com.translationagency.ordermanager.service;

import com.translationagency.ordermanager.entity.Document;
import com.translationagency.ordermanager.entity.Translator;
import com.translationagency.ordermanager.repository.DocumentRepository;
import com.translationagency.ordermanager.util.TranslatorUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DocumentService {

    private DocumentRepository documentRepository;

    private OrderService orderService;

    private TranslatorService translatorService;

    public List<Document> getAllByOrder(int orderId) {
        return documentRepository.getAllByOrderId(orderId);
    }

    public Document get(int orderId, int documentId) {
        return documentRepository.getDocumentByIdAndOrderId(documentId, orderId)
                .orElseThrow(() -> new RuntimeException("Not found"));
    }

    public Document create(Document document, int orderId) {
        document.setOrder(orderService.getReference(orderId));
        Document created = documentRepository.save(document);

        orderService.recalculateOrderCost(orderId);

        return created;
    }

    public Document create(Document document, int orderId, int translatorId) {
        document.setOrder(orderService.getReference(orderId));
        document.setTranslator(translatorService.get(translatorId));

        Document created = documentRepository.save(document);

        orderService.recalculateOrderCost(orderId);

        return created;
    }

    public void update(Document document, int orderId) {
        document.setOrder(orderService.getReference(orderId));
        documentRepository.save(document);
        orderService.recalculateOrderCost(orderId);
    }

    public void delete(int orderId, int documentId) {
        Document delete = documentRepository.getDocumentByIdAndOrderId(documentId, orderId)
                .orElseThrow(() -> new RuntimeException("Not found"));
        documentRepository.delete(delete);
        orderService.recalculateOrderCost(orderId);
    }

    public void changeTranslator(int orderId, int documentId, int translatorId) {
        Document document = documentRepository.getDocumentByIdAndOrderId(documentId, orderId)
                .orElseThrow(() -> new RuntimeException("Not found"));

        document.setTranslator(translatorService.getReferenceById(translatorId));
        updateTranslatorRate(document);

        documentRepository.save(document);
    }

    public void changeComplexity(int orderId, int documentId, boolean isHardComplexity) {
        Document document = documentRepository.getDocumentByIdAndOrderId(documentId, orderId)
                .orElseThrow(() -> new RuntimeException("Not found"));
        document.setIsHardComplexity(isHardComplexity);
        updateTranslatorRate(document);

        documentRepository.save(document);
    }

    private void updateTranslatorRate(Document document) {
        if (document.getTranslator() == null) {
            return;
        }
        Translator translatorWithRates = translatorService.get(document.getTranslator().getId());
        String updatedRate = TranslatorUtil.computeTranslatorRate(translatorWithRates,
                document.getDocumentLanguage(), document.getIsHardComplexity());
        document.setTranslatorRate(updatedRate);
    }
}
