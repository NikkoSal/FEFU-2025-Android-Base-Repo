package co.feip.fefu2025.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.domain.usecase.GetAnimeListUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class SearchViewModel(
    private val getAnimeListUseCase: GetAnimeListUseCase
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _uiState = MutableStateFlow<AnimeUiState>(AnimeUiState.Success(emptyList()))
    val uiState: StateFlow<AnimeUiState> = _uiState

    init {
        viewModelScope.launch {
            _query
                .debounce(600)
                .distinctUntilChanged()
                .collectLatest { newQuery ->
                    if (newQuery.isBlank()) {
                        _uiState.value = AnimeUiState.Success(emptyList())
                        return@collectLatest
                    }

                    _uiState.value = AnimeUiState.Loading

                    try {
                        val result = getAnimeListUseCase()
                        val filtered = result.filter { anime ->
                            anime.title.contains(newQuery, ignoreCase = true) ||
                                    anime.genreNames.any { genre ->
                                        genre.contains(newQuery, ignoreCase = true)
                                    } ||
                                    anime.description?.contains(newQuery, ignoreCase = true) == true ||
                                    anime.year?.contains(newQuery) == true
                        }
                        _uiState.value = AnimeUiState.Success(filtered)
                    } catch (e: Exception) {
                        _uiState.value = AnimeUiState.Error("Не удалось загрузить результаты поиска")
                    }
                }
        }
    }

    fun updateQuery(value: String) {
        _query.value = value
    }

    class Factory(private val useCase: GetAnimeListUseCase) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchViewModel(useCase) as T
        }
    }
}