package com.example.architecturetask4.domain.repository

import com.example.architecturetask4.domain.Translation

interface IRemoteTranslationRepository {
    suspend fun getTranslation(
        text: String,
        source: String,
        target: String
    ): Translation
}
