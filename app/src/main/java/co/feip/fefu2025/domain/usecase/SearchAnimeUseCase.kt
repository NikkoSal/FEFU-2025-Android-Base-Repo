package co.feip.fefu2025.domain.usecase

import co.feip.fefu2025.domain.model.AnimeCardData
import co.feip.fefu2025.domain.model.PaginatedData
import co.feip.fefu2025.domain.repository.AnimeRepository

class SearchAnimeUseCase(private val repository: AnimeRepository) {
    suspend operator fun invoke(query: String, page: Int): Result<PaginatedData<AnimeCardData>> {
        return repository.searchAnime(query, page)
    }
}