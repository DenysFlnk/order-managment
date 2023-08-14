package com.translationagency.ordermanager.util;

import com.translationagency.ordermanager.entity.Document;
import com.translationagency.ordermanager.to.DocumentTo;

import java.util.List;

public class DocumentUtil {

    private DocumentUtil() {
    }

    public static List<DocumentTo> getTos(List<Document> documents) {
        return documents.stream().map(DocumentUtil::getTo).toList();
    }

    public static DocumentTo getTo(Document document) {
        return new DocumentTo( document.id(), document.getOrder().id(), document.getDocumentLanguage(), document.isHardComplexity(),
                document.getOfficeRate(), document.getSignsNumber(), document.getNotarizationCost(),
                document.getOfficeCost(), document.getTranslator() != null ? document.getTranslator().getName() : null,
                document.getTranslatorRate(),
                document.getTranslatorTax());
    }
}
