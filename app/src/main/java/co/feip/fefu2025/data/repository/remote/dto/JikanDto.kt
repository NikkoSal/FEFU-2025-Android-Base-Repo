package co.feip.fefu2025.data.remote.dto

import com.google.gson.annotations.SerializedName


data class JikanResponse(
    val pagination: PaginationDto,
    val data: List<AnimeDataDto>
)

data class PaginationDto(

    @SerializedName("has_next_page") val hasNextPage: Boolean,
    @SerializedName("current_page") val currentPage: Int
)

data class AnimeDataDto(
    @SerializedName("mal_id") val malId: Int,
    val images: ImagesDto?,
    val title: String,
    @SerializedName("title_english") val titleEnglish: String?,
    val episodes: Int?,

    val score: Double?,
    val synopsis: String?,
    val year: Int?,
    val genres: List<GenreDto>?
)

data class ImagesDto(
    val jpg: ImageTypeDto?
)

data class ImageTypeDto(
    @SerializedName("image_url") val imageUrl: String?
)


data class GenreDto(
    val name: String,
    val url: String,
    @SerializedName("mal_id") val malId: Int,
    val type: String
)


data class AnimeDetailsResponse(
    val data: AnimeDataDto
)

data class AnimeRecommendationsResponse(
    val data: List<RecommendationEntryDto>
)

data class RecommendationEntryDto(
    val entry: AnimeDataDto
)