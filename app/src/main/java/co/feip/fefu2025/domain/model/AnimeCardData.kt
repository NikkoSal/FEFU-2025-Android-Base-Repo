package co.feip.fefu2025.domain.model

data class AnimeCardData(
    val id: Int? = null,
    val imageRes: Int,
    val title: String,
    val genreNames: List<String>,
    val rating: Float,
    val episodes: Int? = null
)