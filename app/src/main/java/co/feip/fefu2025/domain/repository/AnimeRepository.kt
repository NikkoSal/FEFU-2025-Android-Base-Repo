package co.feip.fefu2025.domain.repository

import co.feip.fefu2025.domain.model.Anime
import co.feip.fefu2025.domain.model.AnimeCardData
import co.feip.fefu2025.domain.model.PaginatedData
import kotlinx.coroutines.flow.Flow
import co.feip.fefu2025.data.local.AnimeFavoriteEntity

interface AnimeRepository {

    suspend fun getTopAnime(page: Int): Result<PaginatedData<AnimeCardData>>
    suspend fun searchAnime(query: String, page: Int): Result<PaginatedData<AnimeCardData>>
    suspend fun getAnimeById(id: Int): Result<Anime>
    suspend fun getRecommendations(animeId: Int): Result<List<AnimeCardData>>
    fun getFavoritesStream(): Flow<List<AnimeCardData>>
    fun isFavoriteStream(animeId: Int): Flow<Boolean>
    suspend fun addToFavorites(anime: Anime)
    suspend fun removeFromFavorites(animeId: Int)
    suspend fun getFavoriteAnimeDetail(animeId: Int): Result<AnimeFavoriteEntity>
}