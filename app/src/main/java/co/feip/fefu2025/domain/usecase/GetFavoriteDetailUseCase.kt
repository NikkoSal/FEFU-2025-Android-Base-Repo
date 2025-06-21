package co.feip.fefu2025.domain.usecase

import co.feip.fefu2025.data.local.AnimeFavoriteEntity
import co.feip.fefu2025.domain.repository.AnimeRepository

class GetFavoriteDetailUseCase(private val repository: AnimeRepository) {
    suspend operator fun invoke(animeId: Int): Result<AnimeFavoriteEntity> {
        return repository.getFavoriteAnimeDetail(animeId)
    }
}