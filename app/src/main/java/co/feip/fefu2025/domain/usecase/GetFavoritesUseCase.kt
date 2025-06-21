package co.feip.fefu2025.domain.usecase

import co.feip.fefu2025.domain.model.AnimeCardData
import co.feip.fefu2025.domain.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow

class GetFavoritesUseCase(private val repository: AnimeRepository) {
    operator fun invoke(): Flow<List<AnimeCardData>> {
        return repository.getFavoritesStream()
    }
}