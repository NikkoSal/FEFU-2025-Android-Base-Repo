package co.feip.fefu2025.domain.model


data class Anime(
    val id: Int,
    val imageUrl: String?,
    val title: String,
    val genreNames: List<String>,
    val description: String?,
    val rating: Double,
    val year: String?,
    val episodesCount: Int?,
    val ratingDistribution: Map<Int, Int>? = null,
    val recommendations: List<AnimeCardData>? = null
)