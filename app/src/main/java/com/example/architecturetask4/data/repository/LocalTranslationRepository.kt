package com.example.architecturetask4.data.repository

import com.example.architecturetask4.data.datasource.LocalDataSource
import com.example.architecturetask4.data.model.TranslationDto
import com.example.architecturetask4.domain.Translation
import com.example.architecturetask4.domain.repository.ILocalTranslationRepository
import com.example.architecturetask4.domain.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalTranslationRepository @Inject constructor(
    private val localDataSource: LocalDataSource
): ILocalTranslationRepository {

    override fun insertTranslation(
        text: String,
        sourceLanguage: String,
        targetLanguage: String,
        translation: String,
        time: Long
    ) {
        val dto = TranslationDto(
            text = text,
            sourceLanguage = sourceLanguage,
            targetLanguage = targetLanguage,
            translation = translation,
            time = time
        )

        localDataSource.putTranslation(dto)
    }

    override suspend fun getTranslation(
        text: String,
        sourceLanguage: String,
        targetLanguage: String,
    ): Translation? = withContext(Dispatchers.IO) {
        val translation = localDataSource.getTranslation(text)

        return@withContext if (translation?.sourceLanguage == sourceLanguage &&
            translation.targetLanguage == targetLanguage
        ) {
            translation.toDomain()
        } else null
    }

}
