package com.translationagency.ordermanager.to;

import com.translationagency.ordermanager.entity.Language;

public record TranslatorTo(int id, String name, String email, Language language, String rate) {
}
