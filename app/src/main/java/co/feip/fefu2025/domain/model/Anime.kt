package co.feip.fefu2025.domain.model

data class Anime(
    val id: Int,
    val imageRes: Int, //
    val title: String, //
    val genreNames: List<String>, //
    val description: String? = null,
    val formattedRating: String, //
    val year: String? = null,
    val formattedEpisodes: String? = null,
    val ratingDistribution: Map<Int, Int>? = null,
    val recommendationIds: List<Int>? = null,
    val recommendations: List<AnimeCardData>? = null
)