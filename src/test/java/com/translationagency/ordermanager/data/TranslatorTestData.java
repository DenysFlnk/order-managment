package com.translationagency.ordermanager.data;

import com.translationagency.ordermanager.entity.Language;
import com.translationagency.ordermanager.entity.LanguageRate;
import com.translationagency.ordermanager.entity.Translator;

import java.util.List;

public class TranslatorTestData {
    public static final Translator jared = new Translator(
            1, "Jared", "jared.ely@gmail.com", "+380692222111",
            null, true
    );

    public static final Translator mary = new Translator(
            2, "Mary", "mary.smith@gmail.com", "+380698883122",
            null, true
    );

    public static final Translator patricia = new Translator(
            3, "Patricia", "patricia.johnson@gmail.com", "+380692232111",
            null, true
    );

    public static final Translator linda = new Translator(
            4, "Linda", "linda.williams@gmail.com", "+380693562145",
            null, true
    );

    public static final Translator barbara = new Translator(
            5, "Barbara", "barbara.jones@gmail.com", "+380622222444",
            null, true
    );

    public static final Translator elizabeth = new Translator(
            6, "Elizabeth", "elizabeth.brown@gmail.com", "+380682782777",
            null, true
    );

    public static final Translator jennifer = new Translator(
            7, "Jennifer", "jennifer.davis@gmail.com", "+380692212345",
            null, true
    );

    public static final Translator maria = new Translator(
            8, "Maria", "maria.miller@gmail.com", "+380633232111",
            null, true
    );

    public static final Translator susan = new Translator(
            9, "Susan", "susan.wilson@gmail.com", "+380995552323",
            null, true
    );

    public static final Translator margaret = new Translator(
            10, "Margaret", "margaret.moore@gmail.com", "+380986567878",
            null, true
    );

    public static final Translator lisa = new Translator(
            11, "Lisa", "lisa.anderson@gmail.com", "+380334337689",
            null, true
    );

    public static final List<LanguageRate> jaredRates = List.of(new LanguageRate(1, jared, Language.ENGLISH,
            180, 200, 1.0));

    public static final List<LanguageRate> maryRates = List.of(new LanguageRate(2, mary, Language.GERMAN,
            200, 250, 1.0), new LanguageRate(3, mary, Language.KOREAN,
            400, 500, 1.5));

    public static final List<LanguageRate> patriciaRates = List.of(new LanguageRate(4, patricia, Language.ENGLISH,
            170, 190, 1.0), new LanguageRate(5, patricia, Language.GERMAN,
            210, 220, 1.0));

    public static final List<LanguageRate> lindaRates = List.of(new LanguageRate(6, linda, Language.ENGLISH,
            300, 310, 1.0), new LanguageRate(7, linda, Language.UKRAINIAN,
            100, 130, 1.0));

    public static final List<LanguageRate> barbaraRates = List.of(new LanguageRate(8, barbara, Language.FRENCH,
            330, 350, 1.5), new LanguageRate(9, barbara, Language.ESTONIAN,
            400, 420, 1.5));

    public static final List<LanguageRate> elizabethRates = List.of(new LanguageRate(10, elizabeth, Language.ITALIAN,
            220, 250, 1.0), new LanguageRate(11, elizabeth, Language.ENGLISH,
            100, 120, 1.0));

    public static final List<LanguageRate> jenniferRates = List.of(new LanguageRate(12, jennifer, Language.FRENCH,
            250, 270, 1.0));

    public static final List<LanguageRate> mariaRates = List.of(new LanguageRate(13, maria, Language.ITALIAN,
            400, 430, 1.5));

    public static final List<LanguageRate> susanRates = List.of(new LanguageRate(14, susan, Language.ITALIAN,
            330, 350, 1.0));

    public static final List<LanguageRate> margaretRates = List.of(new LanguageRate(15, margaret, Language.POLISH,
            150, 200, 1.0));

    public static final List<LanguageRate> lisaRates = List.of(new LanguageRate(16, lisa, Language.POLISH,
            200, 210, 1.0));

    static {
        jared.setRates(jaredRates);
        mary.setRates(maryRates);
        patricia.setRates(patriciaRates);
        linda.setRates(lindaRates);
        barbara.setRates(barbaraRates);
        elizabeth.setRates(elizabethRates);
        jennifer.setRates(jenniferRates);
        maria.setRates(mariaRates);
        susan.setRates(susanRates);
        margaret.setRates(margaretRates);
        lisa.setRates(lisaRates);
    }
}
