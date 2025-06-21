package co.feip.fefu2025.domain.usecase

import co.feip.fefu2025.domain.repository.AnimeRepository

class RemoveFromFavoritesUseCase(private val repository: AnimeRepository) {
    suspend operator fun invoke(animeId: Int) {
        repository.removeFromFavorites(animeId)
    }
}