package co.feip.fefu2025.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.domain.model.AnimeCardData
import co.feip.fefu2025.domain.usecase.GetAnimeListUseCase
import co.feip.fefu2025.presentation.details.PaginationState
import co.feip.fefu2025.presentation.details.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val getAnimeListUseCase: GetAnimeListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<PaginationState<AnimeCardData>>>(UiState.Loading)
    val uiState: StateFlow<UiState<PaginationState<AnimeCardData>>> = _uiState.asStateFlow()


    private var currentPage = 1
    private var isLoading = false

    init {
        loadNextPage(isInitialLoad = true)
    }

    fun loadNextPage(isInitialLoad: Boolean = false) {
        if (isLoading) return

        val currentState = _uiState.value
        if (!isInitialLoad && currentState is UiState.Success && !currentState.data.canLoadMore) {
            return
        }

        isLoading = true
        viewModelScope.launch {
            if (isInitialLoad) {
                currentPage = 1
                _uiState.value = UiState.Loading
            } else if (currentState is UiState.Success) {
                // Показываем нижний лоадер
                val paginationState = currentState.data
                _uiState.value = UiState.Success(paginationState.copy(isLoadingNextPage = true))
            }

            getAnimeListUseCase(page = currentPage)
                .onSuccess { paginatedResult ->
                    val newItems = paginatedResult.data.distinctBy { it.id }

                    val currentItems = if (isInitialLoad) {
                        emptyList()
                    } else {
                        (uiState.value as? UiState.Success)?.data?.items ?: emptyList()
                    }

                    _uiState.value = UiState.Success(
                        PaginationState(
                            items = currentItems + newItems,
                            isLoadingNextPage = false,
                            canLoadMore = paginatedResult.hasNextPage
                        )
                    )

                    if (paginatedResult.hasNextPage) {
                        currentPage++
                    }
                }
                .onFailure { exception ->
                    if (isInitialLoad) {
                        _uiState.value = UiState.Error(exception.message ?: "Произошла неизвестная ошибка")
                    } else if (currentState is UiState.Success) {
                        val paginationState = currentState.data
                        _uiState.value = UiState.Success(paginationState.copy(isLoadingNextPage = false))
                    }
                }
            isLoading = false
        }
    }

    fun refresh() {
        loadNextPage(isInitialLoad = true)
    }

    // Фабрика остается без изменений
    class Factory(private val useCase: GetAnimeListUseCase) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                return MainViewModel(useCase) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}