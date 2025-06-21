package co.feip.fefu2025.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.domain.model.AnimeCardData
import co.feip.fefu2025.domain.usecase.GetRecommendationsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecommendationViewModel(
    private val getRecommendationsUseCase: GetRecommendationsUseCase,
    private val animeId: Int
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<AnimeCardData>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<AnimeCardData>>> = _uiState.asStateFlow()

    init {
        loadRecommendations()
    }

    fun retryLoad() {
        loadRecommendations()
    }

    private fun loadRecommendations() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            getRecommendationsUseCase(animeId = animeId)
                .onSuccess { recommendationsList ->
                    _uiState.value = UiState.Success(recommendationsList)
                }
                .onFailure { exception ->
                    _uiState.value = UiState.Error(exception.message ?: "Не удалось загрузить рекомендации")
                }
        }
    }

    class Factory(
        private val getRecommendationsUseCase: GetRecommendationsUseCase,
        private val animeId: Int
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return RecommendationViewModel(getRecommendationsUseCase, animeId) as T
        }
    }
}