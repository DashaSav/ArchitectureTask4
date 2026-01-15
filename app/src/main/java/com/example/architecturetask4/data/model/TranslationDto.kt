package com.example.architecturetask4.data.model

data class TranslationDto (
    val text: String,
    val sourceLanguage: String,
    val targetLanguage: String,
    val translation: String,
    val time: Long
)
