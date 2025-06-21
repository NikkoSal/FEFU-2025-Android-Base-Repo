package co.feip.fefu2025.domain.repository

import co.feip.fefu2025.domain.model.Anime
import co.feip.fefu2025.domain.model.AnimeCardData
import co.feip.fefu2025.domain.model.PaginatedData

interface AnimeRepository {

    suspend fun getTopAnime(page: Int): Result<PaginatedData<AnimeCardData>>
    suspend fun searchAnime(query: String, page: Int): Result<PaginatedData<AnimeCardData>>
    suspend fun getAnimeById(id: Int): Result<Anime>
    suspend fun getRecommendations(animeId: Int): Result<List<AnimeCardData>>
}