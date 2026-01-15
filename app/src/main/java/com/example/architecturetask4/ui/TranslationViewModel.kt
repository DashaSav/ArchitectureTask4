package com.example.architecturetask4.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.architecturetask4.domain.GetTranslationUsecase
import com.example.architecturetask4.domain.Translation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface UiState {
    data object Idle : UiState
    data object Loading : UiState
    data class Error(val code: String, val description: String?) : UiState
    data class Result(val translation: Translation) : UiState
}

@HiltViewModel
class TranslationViewModel @Inject constructor(
    private val usecase: GetTranslationUsecase
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState = _uiState.asStateFlow()

    fun launchTranslation(text: String) {
        viewModelScope.launch {
            try {
                _uiState.update { UiState.Loading }
            val res = usecase(text)
                _uiState.update { UiState.Result(res) }
            } catch (e: Throwable){
                _uiState.update { UiState.Error("300", e.message) }
            }
        }
    }
}
