package co.feip.fefu2025.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.compose.runtime.*
import co.feip.fefu2025.domain.usecase.GetAnimeListUseCase
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class MainViewModel(private val getAnimeListUseCase: GetAnimeListUseCase) : ViewModel() {

    var uiState by mutableStateOf<AnimeUiState>(AnimeUiState.Loading)
        private set
    init {
        loadAnime()
    }

    fun loadAnime() {
        uiState = AnimeUiState.Loading
        viewModelScope.launch {
            try {
                val result = getAnimeListUseCase()
                uiState = AnimeUiState.Success(result)
            } catch (e: Exception) {
                uiState = AnimeUiState.Error("Ошибка")
            }
        }
    }




    class Factory(private val useCase: GetAnimeListUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(useCase) as T
        }
    }
}
