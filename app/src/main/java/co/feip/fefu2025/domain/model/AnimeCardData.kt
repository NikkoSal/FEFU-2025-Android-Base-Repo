package co.feip.fefu2025.domain.model

data class AnimeCardData(
    val id: Int,
    val imageUrl: String?,
    val title: String,
    val genreNames: List<String>,
    val rating: Double,
    val episodes: Int? = null
)