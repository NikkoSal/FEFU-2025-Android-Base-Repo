package co.feip.fefu2025.presentation.details

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.domain.model.Anime
import co.feip.fefu2025.domain.usecase.GetAnimeDetailUseCase
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

    fun retryLoad() {
        loadDetail()
    }
    private fun loadDetail() {
        uiState = AnimeDetailUiState.Loading
        viewModelScope.launch {
            getAnimeDetailUseCase(animeId)
                .onSuccess { anime ->
                    uiState = AnimeDetailUiState.Success(anime)
                }
                .onFailure { exception ->
                    uiState = AnimeDetailUiState.Error(exception.message ?: "An unknown error occurred") }
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