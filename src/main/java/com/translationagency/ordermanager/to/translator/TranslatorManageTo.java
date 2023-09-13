package com.translationagency.ordermanager.to.translator;

import com.translationagency.ordermanager.entity.Language;

import java.util.List;

public record TranslatorManageTo(int id, String name, String email, String phoneNumber, List<Language> languages,
                                 boolean available) {
}
