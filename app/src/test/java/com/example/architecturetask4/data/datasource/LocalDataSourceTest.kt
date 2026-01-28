package com.example.architecturetask4.data.datasource

import com.example.architecturetask4.data.model.TranslationDto
import junit.framework.TestCase.assertNull
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LocalDataSourceTest {
    private lateinit var localDataSource: LocalDataSource

    @Before
    fun setUp(){
        localDataSource = LocalDataSource()
    }

    @Test
    fun `getTranslation returns null when cache is empty`(){
        // act
        val result = localDataSource.getTranslation("Translation")

        // assert
        assertNull(result)
    }

    @Test
    fun `putTranslation saves item and getTranslation retrieves it`(){
        // arrange
        val translation = TranslationDto(
            text = "Translate",
            sourceLanguage = "en",
            targetLanguage = "ru",
            translation = "Перевод",
            time = System.currentTimeMillis()
        )

        // act
        localDataSource.putTranslation(translation)
        val result = localDataSource.getTranslation(translation.text)

        // assert
        assertEquals(result, translation)
    }

    @Test
    fun `putTranslation overwrites existing key`(){
        // arrange
        val translation1 = TranslationDto(
            text = "Translate",
            sourceLanguage = "en",
            targetLanguage = "ru",
            translation = "Перевод",
            time = System.currentTimeMillis()
        )

        val translation2 = TranslationDto(
            text = "Translate",
            sourceLanguage = "en",
            targetLanguage = "ru",
            translation = "Привет",
            time = System.currentTimeMillis()
        )

        // act
        localDataSource.putTranslation(translation1)
        localDataSource.putTranslation(translation2)
        val result = localDataSource.getTranslation("Translate")

        // assert
        assertEquals(result, translation2)
    }
}
