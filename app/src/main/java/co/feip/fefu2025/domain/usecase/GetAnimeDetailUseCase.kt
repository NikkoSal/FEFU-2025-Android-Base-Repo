package co.feip.fefu2025.domain.usecase

import co.feip.fefu2025.domain.model.AnimeCardData
import co.feip.fefu2025.domain.model.PaginatedData
import co.feip.fefu2025.domain.repository.AnimeRepository

class GetAnimeListUseCase(private val repository: AnimeRepository) {
    suspend operator fun invoke(page: Int): Result<PaginatedData<AnimeCardData>> {
        return repository.getTopAnime(page)
    }
}