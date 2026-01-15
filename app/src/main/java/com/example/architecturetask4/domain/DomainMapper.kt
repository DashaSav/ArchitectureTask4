package com.example.architecturetask4.domain

import com.example.architecturetask4.data.model.TranslationDto

fun TranslationDto.toDomain() =
    Translation(
        text = text,
        sourceLanguage = sourceLanguage,
        targetLanguage = targetLanguage,
        translation = translation
    )

