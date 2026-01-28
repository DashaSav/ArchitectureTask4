package com.example.architecturetask4.data.datasource

import com.example.architecturetask4.data.TranslationApi
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class RemoteDataSourceTest {
    private val translationApi = mockk<TranslationApi>()
    private val remoteDataSource = RemoteDataSource(translationApi)

    @Test
    fun `getTranslation returns list from api successfully`() = runTest {
        // arrange
        val expectedResult = listOf<String>()
        val input = "Hi"
        coEvery {
            translationApi.translate(any(), any(), any(), any())
        } returns expectedResult

        // act
        val result = remoteDataSource.getTranslation(input)

        // assert
        assertEquals(expectedResult, result)

        coVerify(exactly = 1) {
            translationApi.translate(
                sourceLanguage = "en",
                targetLanguage = "ru",
                text = input
            )
        }
    }

    @Test
    fun `getTranslation uses custom languages correctly`() = runTest {

        // arrange
        val inputText = "Bonjour"
        val source = "fr"
        val target = "de"
        val expectedResponse = listOf("Guten Tag")

        coEvery {
            translationApi.translate(
                sourceLanguage = source,
                targetLanguage = target,
                text = inputText
            )
        } returns expectedResponse

        // act
        val result = remoteDataSource.getTranslation(inputText, source, target)

        // assert
        assertEquals(expectedResponse, result)
        coVerify {
            translationApi.translate(
                sourceLanguage = source,
                targetLanguage = target,
                text = inputText
            )
        }

    }
}
