package com.example.architecturetask4.data.repository

import com.example.architecturetask4.data.datasource.LocalDataSource
import com.example.architecturetask4.data.model.TranslationDto
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LocalTranslationRepositoryTest {
    private val localDataSource: LocalDataSource = mockk()
    lateinit var localTranslationRepository: LocalTranslationRepository

    @Before
    fun setUp() {
        localTranslationRepository = LocalTranslationRepository(
            localDataSource = localDataSource
        )
    }

    @Test
    fun `insert translation success`() = runTest {
        // arrange
        coJustRun {
            localDataSource.putTranslation(any())
        }

        // act
        localTranslationRepository.insertTranslation(
            "Translate",
            sourceLanguage = "en",
            targetLanguage = "ru",
            translation = "Перевод",
            time = 12,
        )

        // assert
        coVerify {
            localDataSource.putTranslation(any())
        }
    }

    @Test
    fun `get translation success`() = runTest {
        // arrange
        val expectedResult = TranslationDto(
            text = "Translation",
            sourceLanguage = "en",
            targetLanguage = "ru",
            translation = "Перевод",
            time = 1234
        )

        coEvery {
            localDataSource.getTranslation("Translation")
        } returns expectedResult

        // act
        val result = localTranslationRepository.getTranslation("Translation", "en", "ru")

        // assert
        coVerify {
            localDataSource.getTranslation("Translation")
        }

        assertEquals(expectedResult.translation, result?.translation)
        assertEquals(expectedResult.text, result?.text)
        assertEquals(expectedResult.targetLanguage, result?.targetLanguage)
        assertEquals(expectedResult.sourceLanguage, result?.sourceLanguage)
    }

    @Test(expected = RuntimeException::class)
    fun `insert translation error`() = runTest {
        // arrange
        coEvery { localDataSource.putTranslation(any()) } throws RuntimeException()

        // act
        localTranslationRepository.insertTranslation(
            text = "test",
            sourceLanguage = "en",
            targetLanguage = "ru",
            translation = "тест",
            time = 1,
        )
    }

    @Test(expected = RuntimeException::class)
    fun `get translation error`() = runTest {
        // arrange
        coEvery { localDataSource.getTranslation(any()) } throws RuntimeException()

        // act
        localTranslationRepository.getTranslation(
            text = "test",
            sourceLanguage = "en",
            targetLanguage = "ru",
        )
    }
}
