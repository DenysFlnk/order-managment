package com.translationagency.ordermanager.util;

import com.translationagency.ordermanager.entity.Document;
import com.translationagency.ordermanager.entity.Language;
import com.translationagency.ordermanager.entity.LanguageRate;
import com.translationagency.ordermanager.entity.Translator;
import com.translationagency.ordermanager.to.DocumentTo;

import java.util.ArrayList;
import java.util.List;

public class DocumentUtil {

    private DocumentUtil(){}

    public static List<DocumentTo> getTos(List<Document> documents) {
        if (documents == null || documents.size() == 0) {
            return new ArrayList<>();
        }
        return documents.stream().map(DocumentUtil::getTo).toList();
    }

    public static DocumentTo getTo(Document document) {
        return new DocumentTo(document.getOrder().id(), document.id(), document.getDocumentLanguage(), document.isHardComplexity(),
                document.getOfficeRate(), document.getSignsNumber(), document.getNotarizationCost(),
                document.getOfficeCost(), document.getTranslator() != null ? document.getTranslator().getName() : null,
                document.getTranslatorRate(),
                document.getTranslatorTax());
    }

    public static String computeTranslatorRate (Document document, Translator translator) {
        LanguageRate translatorRate = getRateByLanguage(translator.getRates(),
                document.getDocumentLanguage());

        if (document.isHardComplexity()){
            return formatTranslatorRate(translatorRate.getHardRate(), translatorRate.getSigns());
        }

        return formatTranslatorRate(translatorRate.getCommonRate(), translatorRate.getSigns());
    }

    private static LanguageRate getRateByLanguage(List<LanguageRate> rates, Language language) {
        //TODO create reasonable exception
        return rates.stream().filter(rate -> rate.getLanguage() == language).findAny().orElseThrow(() -> new RuntimeException("Rate not found"));
    }

    private static String formatTranslatorRate(int price, double singsInThousands) {
        String format = "%s/%s";
        return String.format(format, price, (int) (singsInThousands * 1000));

    }
}
