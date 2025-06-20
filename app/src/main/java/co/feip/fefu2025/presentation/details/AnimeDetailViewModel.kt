package co.feip.fefu2025.presentation.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.feip.fefu2025.domain.model.Anime
import co.feip.fefu2025.domain.usecase.GetAnimeDetailUseCase
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.*
import kotlinx.coroutines.launch

sealed class AnimeDetailUiState {
    object Loading : AnimeDetailUiState()
    data class Success(val anime: Anime) : AnimeDetailUiState()
    data class Error(val message: String) : AnimeDetailUiState()
}

class AnimeDetailViewModel(
    private val getAnimeDetailUseCase: GetAnimeDetailUseCase,
    private val animeId: Int
) : ViewModel() {

    var uiState by mutableStateOf<AnimeDetailUiState>(AnimeDetailUiState.Loading)
        private set

    init {
        loadDetail()
    }

    fun loadDetail() {
        uiState = AnimeDetailUiState.Loading
        viewModelScope.launch {
            try {
                val result = getAnimeDetailUseCase(animeId)
                uiState = if (result != null) {
                    AnimeDetailUiState.Success(result)
                } else {
                    AnimeDetailUiState.Error("Ошибка")
                }
            } catch (e: Exception) {
                uiState = AnimeDetailUiState.Error(e.message ?: "Ошибка")
            }
        }
    }

    class Factory(
        private val getAnimeDetailUseCase: GetAnimeDetailUseCase,
        private val animeId: Int
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AnimeDetailViewModel(getAnimeDetailUseCase, animeId) as T
        }
    }
}