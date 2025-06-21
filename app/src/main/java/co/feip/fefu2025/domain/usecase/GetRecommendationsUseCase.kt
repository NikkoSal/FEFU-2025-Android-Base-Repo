package co.feip.fefu2025.domain.usecase

import co.feip.fefu2025.domain.model.AnimeCardData
import co.feip.fefu2025.domain.repository.AnimeRepository

class GetRecommendationsUseCase(private val repository: AnimeRepository) {
    suspend operator fun invoke(animeId: Int): Result<List<AnimeCardData>> {
        return repository.getRecommendations(animeId)
    }
}