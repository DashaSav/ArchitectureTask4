package com.example.architecturetask4.data.datasource

import com.example.architecturetask4.data.TranslationApi
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class RemoteDataSource @Inject constructor(private val api: TranslationApi) {

    suspend fun getTranslation(
        text: String,
        sourceLanguage: String = "en",
        targetLanguage: String = "ru"
    ): List<String> {
        val response = api.translate(
            sourceLanguage = sourceLanguage,
            targetLanguage = targetLanguage,
            text = text
        )
        delay(Random.nextLong(300, 1000))
        return response
    }
}
