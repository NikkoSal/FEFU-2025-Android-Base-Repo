package co.feip.fefu2025.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.domain.model.AnimeCardData
import co.feip.fefu2025.domain.usecase.GetFavoritesUseCase
import co.feip.fefu2025.presentation.details.UiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class FavoritesViewModel(
    getFavoritesUseCase: GetFavoritesUseCase
) : ViewModel() {
    val favoritesState: StateFlow<UiState<List<AnimeCardData>>> =
        getFavoritesUseCase()
            .map { list -> UiState.Success(list) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = UiState.Loading
            )

    class Factory(
        private val getFavoritesUseCase: GetFavoritesUseCase
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
                return FavoritesViewModel(getFavoritesUseCase) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}