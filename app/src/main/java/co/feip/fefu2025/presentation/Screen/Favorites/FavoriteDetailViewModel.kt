package co.feip.fefu2025.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.data.local.AnimeFavoriteEntity
import co.feip.fefu2025.domain.usecase.GetFavoriteDetailUseCase
import co.feip.fefu2025.presentation.details.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoriteDetailViewModel(
    private val getFavoriteDetailUseCase: GetFavoriteDetailUseCase,
    private val animeId: Int
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<AnimeFavoriteEntity>>(UiState.Loading)
    val uiState: StateFlow<UiState<AnimeFavoriteEntity>> = _uiState.asStateFlow()

    init {
        loadFavoriteDetail()
    }

    fun loadFavoriteDetail() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            getFavoriteDetailUseCase(animeId)
                .onSuccess { entity ->
                    _uiState.value = UiState.Success(entity)
                }
                .onFailure { exception ->
                    _uiState.value = UiState.Error(exception.message)
                }
        }
    }

    class Factory(
        private val getFavoriteDetailUseCase: GetFavoriteDetailUseCase,
        private val animeId: Int
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FavoriteDetailViewModel::class.java)) {
                return FavoriteDetailViewModel(getFavoriteDetailUseCase, animeId) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}