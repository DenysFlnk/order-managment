package com.translationagency.ordermanager.util;

import com.translationagency.ordermanager.entity.Language;
import com.translationagency.ordermanager.entity.LanguageRate;
import com.translationagency.ordermanager.entity.Translator;
import com.translationagency.ordermanager.to.translator.TranslatorManageTo;
import com.translationagency.ordermanager.to.translator.TranslatorTo;

import java.util.List;

public class TranslatorUtil {

    public TranslatorUtil() {
    }

    public static List<TranslatorManageTo> getManageTos(List<Translator> translators) {
        return translators.stream().map(TranslatorUtil::getManageTo).toList();
    }

    public static TranslatorManageTo getManageTo(Translator translator) {
        return new TranslatorManageTo(translator.id(), translator.getName(), translator.getEmail(),
                translator.getPhoneNumber(), getLanguages(translator.getRates()), translator.isAvailable());
    }

    private static List<Language> getLanguages(List<LanguageRate> languageRates) {
        return languageRates.stream().map(LanguageRate::getLanguage).toList();
    }

    public static List<TranslatorTo> getTos(Language language, boolean isHardComplexity,
                                                        List<Translator> translators) {
        return translators.stream().map(t -> getTo(language, isHardComplexity, t)).toList();
    }

    private static TranslatorTo getTo(Language language, boolean isHardComplexity, Translator translator) {
        return new TranslatorTo(translator.id(), translator.getName(), translator.getEmail(), language,
                computeTranslatorRate(translator, language, isHardComplexity));
    }

    public static String computeTranslatorRate (Translator translator, Language language, boolean isHardComplexity) {
        LanguageRate translatorRate = TranslatorUtil.getRateByLanguage(translator.getRates(),
                language);

        if (isHardComplexity){
            return TranslatorUtil.formatTranslatorRate(translatorRate.getHardRate(), translatorRate.getSigns());
        }

        return TranslatorUtil.formatTranslatorRate(translatorRate.getCommonRate(), translatorRate.getSigns());
    }

    private static LanguageRate getRateByLanguage(List<LanguageRate> rates, Language language) {
        return rates.stream().filter(rate -> rate.getLanguage() == language).findAny()
                .orElseThrow(() -> new RuntimeException("Rate not found"));
    }

    private static String formatTranslatorRate(int price, double singsInThousands) {
        String format = "%s/%s";
        return String.format(format, price, (int) (singsInThousands * 1000));
    }
}
