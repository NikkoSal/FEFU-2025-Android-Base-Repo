package co.feip.fefu2025

data class AnimeCardData(
    val imageRes: Int,
    val title: String,
    val genreNames: List<String>,
    val rating: Float,
    val episodes: Int? = null
)