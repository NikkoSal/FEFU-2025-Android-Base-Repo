package co.feip.fefu2025.data.mapper

import co.feip.fefu2025.data.remote.dto.AnimeDataDto
import co.feip.fefu2025.data.remote.dto.RecommendationEntryDto
import co.feip.fefu2025.domain.model.Anime
import co.feip.fefu2025.domain.model.AnimeCardData

fun AnimeDataDto.toDomainModel(): Anime {
    return Anime(
        id = this.malId,
        title = this.titleEnglish ?: this.title,
        imageUrl = this.images?.jpg?.imageUrl,
        genreNames = this.genres?.mapNotNull { it.name } ?: emptyList(),
        description = this.synopsis,
        rating = this.score ?: 0.0,
        year = this.year?.toString(),
        episodesCount = this.episodes,
        ratingDistribution = null,
        recommendations = null
    )
}

fun AnimeDataDto.toDomainCardData(): AnimeCardData {
    return AnimeCardData(
        id = this.malId,
        title = this.titleEnglish ?: this.title,
        imageUrl = this.images?.jpg?.imageUrl,
        genreNames = this.genres?.mapNotNull { it.name } ?: emptyList(),
        rating = this.score ?: 0.0,
        episodes = this.episodes
    )
}

fun RecommendationEntryDto.toDomainCardData(): AnimeCardData {
    val animeDto = this.entry
    return AnimeCardData(
        id = animeDto.malId,
        title = animeDto.titleEnglish ?: animeDto.title,
        imageUrl = animeDto.images?.jpg?.imageUrl,
        genreNames = animeDto.genres?.mapNotNull { it.name } ?: emptyList(),
        rating = animeDto.score ?: 0.0,
        episodes = animeDto.episodes
    )
}