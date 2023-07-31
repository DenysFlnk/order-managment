package com.translationagency.ordermanager.to;

import com.translationagency.ordermanager.entity.Language;

public record DocumentTo(int id, int orderId, Language language, int officeRate, double signsNumber,
                         int notarizationCost, int officeCost, String translatorName,
                         String translatorRate, int translatorTax) {
}
