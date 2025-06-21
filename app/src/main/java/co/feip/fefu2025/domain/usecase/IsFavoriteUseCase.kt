package co.feip.fefu2025.domain.usecase

import co.feip.fefu2025.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow

class IsFavoriteUseCase(private val repository: AnimeRepository) {
    operator fun invoke(animeId: Int): Flow<Boolean> {
        return repository.isFavoriteStream(animeId)
    }
}