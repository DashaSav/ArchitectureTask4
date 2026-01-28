package com.example.architecturetask4.domain

import com.example.architecturetask4.domain.repository.ILocalTranslationRepository
import com.example.architecturetask4.domain.repository.IRemoteTranslationRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetTranslationUsecaseTest {
    private val remoteTranslationRepository = mockk<IRemoteTranslationRepository>()
    private val localTranslationRepository = mockk<ILocalTranslationRepository>(relaxed = true)
    lateinit var usecase: GetTranslationUsecase

    @Before
    fun setUp() {
        usecase = GetTranslationUsecase(remoteTranslationRepository, localTranslationRepository)
    }

    @Test
    fun `get translation should return correct result from remote`() = runTest {
        // arrange
        val expectedResult = Translation(
            text = "Translate",
            sourceLanguage = "en",
            targetLanguage = "ru",
            translation = "Перевод"
        )

        coEvery {
            remoteTranslationRepository.getTranslation(any(), any(), any())
        } returns expectedResult

        coEvery {
            localTranslationRepository.getTranslation(any(), any(), any())
        } returns null

        // act
        val result = usecase(
            text = "Translate",
            sourceLanguage = "en",
            targetLanguage = "ru"
        )

        // assert
        coVerify {
            localTranslationRepository.getTranslation(any(), any(), any())
            remoteTranslationRepository.getTranslation(
                text = "Translate",
                source = "en",
                target = "ru"
            )
            localTranslationRepository.insertTranslation(
                text = "Translate",
                sourceLanguage = "en",
                targetLanguage = "ru",
                translation = any(),
                time = any(),
            )
        }

        assertEquals(expectedResult, result)
    }

    @Test
    fun `get translation should return correct result from local`() = runTest {
        // arrange
        val expectedResult = Translation(
            text = "Translate",
            sourceLanguage = "en",
            targetLanguage = "ru",
            translation = "Перевод"
        )
        coEvery {
            localTranslationRepository.getTranslation(any(), any(), any())
        } returns expectedResult

        // act
        val result = usecase(
            text = "Translate",
            sourceLanguage = "en",
            targetLanguage = "ru"
        )

        // assert
        coVerify {
            localTranslationRepository.getTranslation(any(), any(), any())
        }

        coVerify(exactly = 0) {
            remoteTranslationRepository.getTranslation(any(), any(), any())
            localTranslationRepository.insertTranslation(any(), any(), any(), any(), any())
        }

        assertEquals(expectedResult, result)
    }

    @Test(expected = Exception::class)
    fun `get translation from remote should return error`() = runTest {
        // arrange
        coEvery {
            localTranslationRepository.getTranslation(any(), any(), any())
        } returns null

        coEvery {
            remoteTranslationRepository.getTranslation(any(), any(), any())
        } throws Exception()

        // act
        usecase("123")

        // assert
        coVerify(exactly = 0) {
            localTranslationRepository.insertTranslation(any(), any(), any(), any(), any())
        }
    }

    @Test(expected = Exception::class)
    fun `get translation from local should return error`() = runTest {
        // arrange
        coEvery {
            localTranslationRepository.getTranslation(any(), any(), any())
        } throws Exception()

        // act
        usecase("123")

        // assert
        coVerify(exactly = 0) {
            localTranslationRepository.insertTranslation(any(), any(), any(), any(), any())
            remoteTranslationRepository.getTranslation(any(), any(), any())
        }
    }
}
