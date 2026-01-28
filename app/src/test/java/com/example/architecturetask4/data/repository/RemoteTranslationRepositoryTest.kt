package com.example.architecturetask4.data.repository

import com.example.architecturetask4.data.datasource.RemoteDataSource
import com.example.architecturetask4.data.model.TranslationDto
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RemoteTranslationRepositoryTest {
    private val remoteDataSource: RemoteDataSource = mockk()
    lateinit var remoteTranslationRepository: RemoteTranslationRepository

    @Before
    fun setUp() {
        remoteTranslationRepository = RemoteTranslationRepository(
            remoteDataSource = remoteDataSource
        )
    }
    
    @Test
    fun `get translation success`() = runTest {
        // arrange
        val expectedResult = listOf("Перевод")

        coEvery {
            remoteDataSource.getTranslation("Translation")
        } returns expectedResult

        // act
        val result = remoteTranslationRepository.getTranslation("Translation", "en", "ru")

        // assert
        coVerify {
            remoteDataSource.getTranslation("Translation")
        }

        assertEquals(expectedResult.joinToString(" "), result.translation)
    }

    @Test(expected = RuntimeException::class)
    fun `get translation error`() = runTest {
        // arrange
        coEvery { remoteDataSource.getTranslation(any()) } throws RuntimeException()

        // act
        remoteTranslationRepository.getTranslation(
            text = "test",
            source = "en",
            target = "ru",
        )
    }
}
