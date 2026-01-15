package com.example.architecturetask4.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun MainScreen(
    viewModel: TranslationViewModel
) {
    LaunchedEffect(viewModel) {
        viewModel.launchTranslation("Hello, How Are You?")
    }
}
