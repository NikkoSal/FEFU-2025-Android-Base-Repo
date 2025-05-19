package co.feip.fefu2025.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.compose.runtime.*
import co.feip.fefu2025.domain.model.Anime
import co.feip.fefu2025.domain.usecase.GetAnimeListUseCase

class MainViewModel(private val getAnimeListUseCase: GetAnimeListUseCase) : ViewModel() {

    private val fullList: List<Anime> = getAnimeListUseCase()

    var searchQuery by mutableStateOf("")
        private set

    val animeList: List<Anime>
        get() = if (searchQuery.isBlank()) {
            fullList
        } else {
            fullList.filter { anime ->
                anime.title.contains(searchQuery, ignoreCase = true)
            }
        }

    fun updateSearchQuery(query: String) {
        searchQuery = query
    }

    class Factory(private val useCase: GetAnimeListUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(useCase) as T
        }
    }
}
