package com.translationagency.ordermanager.to;

import com.translationagency.ordermanager.entity.Language;

public record DocumentTo(int id, int orderId, Language language, boolean isHardComplexity, int officeRate, Double signsNumber,
                         int notarizationCost, int officeCost, Integer translatorId, String translatorName,
                         String translatorRate, Integer translatorTax) {
}
