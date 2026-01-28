package com.example.architecturetask4.ui

import com.example.architecturetask4.domain.GetTranslationUsecase
import com.example.architecturetask4.domain.Translation
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class TranslationViewModelTest {

    private val usecase = mockk<GetTranslationUsecase>()
    lateinit var viewModel: TranslationViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = TranslationViewModel(usecase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `translation success result`() {
        // arrange
        val correctTranslation = mockk<Translation>()
        coEvery {
            usecase(
                text = "Translate",
                sourceLanguage = "en",
                targetLanguage = "ru"
            )
        } returns correctTranslation

        // act
        viewModel.launchTranslation("Translate")

        // assert
        val uiState = viewModel.uiState.value
        coVerify { usecase(any(), any(), any()) }
        assertTrue(uiState is UiState.Result)
        uiState as UiState.Result

        assertEquals(correctTranslation, uiState.translation)
    }

    @Test
    fun `translation error result`() {
        //arrange
        coEvery {  usecase(
            text = "Translate",
            sourceLanguage = "en",
            targetLanguage = "ru"
        )
        } throws Exception()

        //act
        viewModel.launchTranslation("Translate")

        //assert
        val uiState = viewModel.uiState.value
        coVerify { usecase(any(), any(), any()) }
        assertTrue(uiState is UiState.Error)
        uiState as UiState.Error
    }
}
