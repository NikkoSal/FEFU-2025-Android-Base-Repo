package co.feip.fefu2025.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.domain.model.AnimeCardData
import co.feip.fefu2025.domain.usecase.SearchAnimeUseCase
import co.feip.fefu2025.presentation.details.PaginationState
import co.feip.fefu2025.presentation.details.UiState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class SearchViewModel(
    private val searchAnimeUseCase: SearchAnimeUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _uiState = MutableStateFlow<UiState<PaginationState<AnimeCardData>>>(UiState.Success(PaginationState(emptyList())))
    val uiState: StateFlow<UiState<PaginationState<AnimeCardData>>> = _uiState.asStateFlow()

    private var currentPage = 1
    private var isLoading = false

    init {
        observeSearchQuery()
    }

    private fun observeSearchQuery() {
        viewModelScope.launch {
            _searchQuery
                .debounce(500L)
                .distinctUntilChanged()
                .collectLatest { query ->
                    searchAnime(query = query, isInitialLoad = true)
                }
        }
    }

    fun loadNextPage() {
        if (_searchQuery.value.isBlank()) return
        searchAnime(query = _searchQuery.value, isInitialLoad = false)
    }

    private fun searchAnime(query: String, isInitialLoad: Boolean) {
        if (query.isBlank()) {
            _uiState.value = UiState.Success(PaginationState(emptyList()))
            return
        }

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
                val paginationState = currentState.data
                _uiState.value = UiState.Success(paginationState.copy(isLoadingNextPage = true))
            }

            searchAnimeUseCase(query = query, page = currentPage)
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
                        _uiState.value = UiState.Error(exception.message)
                    } else if (currentState is UiState.Success) {
                        val paginationState = currentState.data
                        _uiState.value = UiState.Success(paginationState.copy(isLoadingNextPage = false))
                    }
                }
            isLoading = false
        }
    }

    fun onQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }
    class Factory(private val useCase: SearchAnimeUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchViewModel(useCase) as T
        }
    }
}