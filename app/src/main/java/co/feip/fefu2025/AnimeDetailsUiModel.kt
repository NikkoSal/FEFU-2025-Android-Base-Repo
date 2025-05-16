package co.feip.fefu2025

data class AnimeDetailsUiModel(
    val imageRes: Int,
    val title: String,
    val genreNames: List<String>,
    val description: String,
    val formattedRating: String,
    val year: String,
    val formattedEpisodes: String,
    val ratingDistribution: Map<Int, Int>,
    val recommendations: List<AnimeCardData>
)