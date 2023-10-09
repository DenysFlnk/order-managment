package com.translationagency.ordermanager.service;

import com.translationagency.ordermanager.entity.Document;
import com.translationagency.ordermanager.entity.Translator;
import com.translationagency.ordermanager.exception_handling.error.IllegalRequestDataException;
import com.translationagency.ordermanager.repository.DocumentRepository;
import com.translationagency.ordermanager.util.TranslatorUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.translationagency.ordermanager.util.validation.ValidationUtil.*;

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
        return checkNotFoundWithId(documentRepository.getDocumentByIdAndOrderId(documentId, orderId).orElse(null),
                documentId);
    }

    public Document create(Document document, int orderId) {
        checkNew(document);
        document.setOrder(orderService.getReference(orderId));
        Document created = documentRepository.save(document);

        orderService.recalculateOrderCostAndSave(orderId);

        return created;
    }

    public Document createWithTranslator(Document document, int orderId, int translatorId) {
        checkNew(document);
        document.setOrder(checkNotFoundWithId(orderService.getReference(orderId), orderId));
        document.setTranslator(checkNotFoundWithId(translatorService.getWithRates(translatorId), translatorId));

        Document created = documentRepository.save(document);

        orderService.recalculateOrderCostAndSave(orderId);

        return created;
    }

    public void update(Document document, int orderId) {
        document.setOrder(checkNotFoundWithId(orderService.getReference(orderId), orderId));
        documentRepository.save(document);

        orderService.recalculateOrderCostAndSave(orderId);
    }

    public void updateWithTranslator(Document document, int orderId, int translatorId) {
        document.setOrder(checkNotFoundWithId(orderService.getReference(orderId), orderId));
        document.setTranslator(checkNotFoundWithId(translatorService.getWithRates(translatorId), translatorId));
        documentRepository.save(document);

        orderService.recalculateOrderCostAndSave(orderId);
    }

    public void delete(int orderId, int documentId) {
        Document delete = checkNotFoundWithId(documentRepository.getDocumentByIdAndOrderId(documentId, orderId)
                        .orElse(null), documentId);
        documentRepository.delete(delete);
        orderService.recalculateOrderCostAndSave(orderId);
    }

    public void changeTranslator(int orderId, int documentId, int translatorId) {
        Document document = checkNotFoundWithId(documentRepository.getDocumentByIdAndOrderId(documentId, orderId)
                .orElse(null), documentId);

        document.setTranslator(checkNotFoundWithId(translatorService.getReferenceById(translatorId), translatorId));
        updateTranslatorRate(document);

        documentRepository.save(document);
    }

    public Document changeComplexity(int orderId, int documentId, boolean isHardComplexity, boolean updateRate) {
        Document document = checkNotFoundWithId(documentRepository.getDocumentByIdAndOrderId(documentId, orderId)
                .orElse(null), documentId);
        document.setIsHardComplexity(isHardComplexity);

        if (updateRate) {
            updateTranslatorRate(document);
        }

        return documentRepository.save(document);
    }

    private void updateTranslatorRate(Document document) {
        if (document.getTranslator() == null) {
            throw new IllegalRequestDataException("Document must has translator");
        }
        Translator translatorWithRates = translatorService.getWithRates(document.getTranslator().getId());
        String updatedRate = TranslatorUtil.computeTranslatorRate(translatorWithRates,
                document.getDocumentLanguage(), document.getIsHardComplexity());
        document.setTranslatorRate(updatedRate);
    }
}
