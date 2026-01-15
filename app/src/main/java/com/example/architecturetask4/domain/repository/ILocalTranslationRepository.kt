package com.example.architecturetask4.domain.repository

import com.example.architecturetask4.domain.Translation

interface ILocalTranslationRepository {
    suspend fun getTranslation(
        text: String,
        sourceLanguage: String,
        targetLanguage: String,
    ): Translation?

    fun insertTranslation(
        text: String,
        sourceLanguage: String,
        targetLanguage: String,
        translation: String,
        time: Long
    )
}
