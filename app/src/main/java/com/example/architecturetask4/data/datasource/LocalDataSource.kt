package com.example.architecturetask4.data.datasource

import androidx.collection.LruCache
import com.example.architecturetask4.data.model.TranslationDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor() {
    private val cache: LruCache<String, TranslationDto> = LruCache(10)

    fun getTranslation(text: String): TranslationDto? {
        return cache[text]
    }

    fun putTranslation(dto: TranslationDto){
        cache.put(dto.text, dto)
    }
}
