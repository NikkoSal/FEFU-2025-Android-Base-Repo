package co.feip.fefu2025.presentation.details

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.domain.model.Anime
import co.feip.fefu2025.domain.usecase.GetAnimeDetailUseCase
import co.feip.fefu2025.domain.usecase.AddToFavoritesUseCase
import co.feip.fefu2025.domain.usecase.IsFavoriteUseCase
import co.feip.fefu2025.domain.usecase.RemoveFromFavoritesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class AnimeDetailUiState {
    object Loading : AnimeDetailUiState()
    data class Success(val anime: Anime) : AnimeDetailUiState()
    data class Error(val message: String?) : AnimeDetailUiState()
}

class AnimeDetailViewModel(
    private val getAnimeDetailUseCase: GetAnimeDetailUseCase,
    private val isFavoriteUseCase: IsFavoriteUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase,
    private val animeId: Int
) : ViewModel() {

    var uiState by mutableStateOf<AnimeDetailUiState>(AnimeDetailUiState.Loading)
        private set

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    init {
        loadDetail()
        observeFavoriteStatus()
    }

    private fun observeFavoriteStatus() {
        viewModelScope.launch {
            isFavoriteUseCase(animeId).collect { isFav ->
                _isFavorite.value = isFav
            }
        }
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
                    uiState = AnimeDetailUiState.Error(exception.message)
                }
        }
    }

    fun onToggleFavorite() {
        viewModelScope.launch {
            val currentState = uiState
            if (currentState is AnimeDetailUiState.Success) {
                val anime = currentState.anime
                if (_isFavorite.value) {
                    removeFromFavoritesUseCase(anime.id)
                } else {
                    addToFavoritesUseCase(anime)
                }
            }
        }
    }

    class Factory(
        private val getAnimeDetailUseCase: GetAnimeDetailUseCase,
        private val isFavoriteUseCase: IsFavoriteUseCase,
        private val addToFavoritesUseCase: AddToFavoritesUseCase,
        private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase,
        private val animeId: Int
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AnimeDetailViewModel::class.java)) {
                return AnimeDetailViewModel(
                    getAnimeDetailUseCase,
                    isFavoriteUseCase,
                    addToFavoritesUseCase,
                    removeFromFavoritesUseCase,
                    animeId
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}