package co.feip.fefu2025.domain.usecase

import co.feip.fefu2025.domain.model.Anime
import co.feip.fefu2025.domain.repository.AnimeRepository

class GetAnimeDetailUseCase(private val repository: AnimeRepository) {
    suspend operator fun invoke(id: Int): Result<Anime> {
        return repository.getAnimeById(id)
    }
}