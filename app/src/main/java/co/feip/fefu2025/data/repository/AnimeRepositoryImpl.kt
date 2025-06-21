package co.feip.fefu2025.data.repository

import co.feip.fefu2025.data.mapper.toDomainCardData
import co.feip.fefu2025.data.mapper.toDomainModel
import co.feip.fefu2025.data.remote.ApiService
import co.feip.fefu2025.domain.model.Anime
import co.feip.fefu2025.domain.model.AnimeCardData
import co.feip.fefu2025.domain.model.PaginatedData
import co.feip.fefu2025.domain.repository.AnimeRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class AnimeRepositoryImpl(
    private val apiService: ApiService
) : AnimeRepository {

    override suspend fun getTopAnime(page: Int): Result<PaginatedData<AnimeCardData>> {
        return try {
            val response = apiService.getTopAnime(page = page)
            val paginatedData = PaginatedData(
                data = response.data.map { it.toDomainCardData() },
                hasNextPage = response.pagination.hasNextPage
            )
            Result.success(paginatedData)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun searchAnime(query: String, page: Int): Result<PaginatedData<AnimeCardData>> {
        return try {
            val response = apiService.searchAnime(query = query, page = page)
            val paginatedData = PaginatedData(
                data = response.data.map { it.toDomainCardData() },
                hasNextPage = response.pagination.hasNextPage
            )
            Result.success(paginatedData)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAnimeById(id: Int): Result<Anime> {
        return try {
            coroutineScope {
                val detailsDeferred = async { apiService.getAnimeDetails(id) }
                val recommendationsDeferred = async { apiService.getAnimeRecommendations(id) }

                val animeDto = detailsDeferred.await().data
                val recommendationsDto = recommendationsDeferred.await().data

                val anime = animeDto.toDomainModel()
                val recommendations = recommendationsDto
                    .map { it.toDomainCardData() }
                    .take(12)
                Result.success(anime.copy(recommendations = recommendations))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getRecommendations(animeId: Int): Result<List<AnimeCardData>> {
        return try {
            val response = apiService.getAnimeRecommendations(animeId)
            Result.success(response.data.map { it.toDomainCardData() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}