package com.translationagency.ordermanager.entity;

import lombok.Getter;

@Getter
public enum Language {
    ALBANIAN("alb"),
    BASQUE("baq"),
    BULGARIAN("bul"),
    CATALAN("cat"),
    CROATIAN("hrv"),
    CZECH("ces"),
    DANISH("dan"),
    DUTCH("nld"),
    ENGLISH("eng"),
    ESTONIAN("est"),
    FINNISH("fin"),
    FRENCH("fra"),
    GERMAN("deu"),
    GREEK("ell"),
    HUNGARIAN("hun"),
    IRISH("gle"),
    ITALIAN("ita"),
    LATVIAN("lav"),
    LITHUANIAN("lit"),
    MALTESE("mlt"),
    NORWEGIAN("nor"),
    POLISH("pol"),
    PORTUGUESE("por"),
    ROMANIAN("ron"),
    RUSSIAN("rus"),
    SERBIAN("srp"),
    SLOVAK("slk"),
    SLOVENIAN("slv"),
    SPANISH("spa"),
    SWEDISH("swe"),
    TURKISH("tur"),
    UKRAINIAN("ukr"),
    WELSH("cym"),
    CHINESE("chi"),
    JAPANESE("jpn"),
    KOREAN("kor"),
    HINDI("hin"),
    ARABIC("ara"),
    URDU("urd"),
    BENGALI("ben");

    private final String abbreviation;

    Language(String abbreviation) {
        this.abbreviation = abbreviation;
    }
}
