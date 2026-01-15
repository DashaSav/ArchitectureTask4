package com.example.architecturetask4.data.repository

import com.example.architecturetask4.data.datasource.RemoteDataSource
import com.example.architecturetask4.domain.Translation
import com.example.architecturetask4.domain.repository.IRemoteTranslationRepository
import javax.inject.Inject

class RemoteTranslationRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : IRemoteTranslationRepository {
    override suspend fun getTranslation(
        text: String,
        source: String,
        target: String
    ): Translation {

        val remoteTranslation = remoteDataSource.getTranslation(text, source, target)

        val translated = remoteTranslation.joinToString(separator = " ")

        return Translation(
            text = text,
            sourceLanguage = source,
            targetLanguage = target,
            translation = translated
        )
    }
}
