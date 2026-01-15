package com.example.architecturetask4.domain

import com.example.architecturetask4.domain.repository.ILocalTranslationRepository
import com.example.architecturetask4.domain.repository.IRemoteTranslationRepository
import javax.inject.Inject

class GetTranslationUsecase @Inject constructor(
    private val remoteTranslationRepository: IRemoteTranslationRepository,
    private val localTranslationRepository: ILocalTranslationRepository
) {
    suspend operator fun invoke(
        text: String,
        sourceLanguage: String = "en",
        targetLanguage: String = "ru"
    ): Translation {
        val localTranslation =
            localTranslationRepository.getTranslation(text, sourceLanguage, targetLanguage)
        if (localTranslation != null) {
            return localTranslation
        }
        val remoteTranslation =
            remoteTranslationRepository.getTranslation(text, sourceLanguage, targetLanguage)

        localTranslationRepository.insertTranslation(
            text = text,
            sourceLanguage = sourceLanguage,
            targetLanguage = targetLanguage,
            translation = remoteTranslation.translation,
            time = System.currentTimeMillis()
        )

        return Translation(
            text = text,
            sourceLanguage = sourceLanguage,
            targetLanguage = targetLanguage,
            translation = remoteTranslation.translation
        )
    }
}
