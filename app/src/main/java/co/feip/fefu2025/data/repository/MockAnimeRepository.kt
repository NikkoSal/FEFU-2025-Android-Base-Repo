package co.feip.fefu2025.data.repository

import co.feip.fefu2025.R
import co.feip.fefu2025.domain.model.Anime
import co.feip.fefu2025.domain.repository.AnimeRepository
import co.feip.fefu2025.domain.model.AnimeCardData


class MockAnimeRepository : AnimeRepository {
    private val animeList = listOf(
        Anime(
            id = 1, //
            imageRes = R.drawable.pic, //
            title = "Вечная воля", //
            genreNames = listOf("Романтика", "Боевик", "Экшн"), //
            description = "Бай Сяоцюань рос без родителей в маленькой деревне и всегда стремился к чему-то большему...", //
            formattedRating = "5.0", //
            year = "2025",
            formattedEpisodes = "1", //
            ratingDistribution = mapOf( //
                1 to 100, 2 to 50, 3 to 200, 4 to 150, 5 to 1000,
                6 to 250, 7 to 400, 8 to 350, 9 to 450, 10 to 244
            ),
            recommendationIds = listOf(2, 3, 4) //
        ),
        Anime(
            id = 2, //
            imageRes = R.drawable.pic, //
            title = "Вечная воля", //
            genreNames = listOf("Романтика", "Боевик", "Экшн"), //
            description = "Бай Сяоцюань рос без родителей в маленькой деревне и всегда стремился к чему-то большему...", //
            formattedRating = "5.0", //
            year = "2025",
            formattedEpisodes = "1", //
            ratingDistribution = mapOf( //
                1 to 100, 2 to 50, 3 to 200, 4 to 150, 5 to 1000,
                6 to 250, 7 to 400, 8 to 350, 9 to 450, 10 to 244
            ),
            recommendationIds = listOf(1, 3, 4) //
        ),
        Anime(
            id = 3, //
            imageRes = R.drawable.pic, //
            title = "Вечная воля", //
            genreNames = listOf("Романтика", "Боевик", "Экшн"), //
            description = "Бай Сяоцюань рос без родителей в маленькой деревне и всегда стремился к чему-то большему...", //
            formattedRating = "5.0", //
            year = "2025",
            formattedEpisodes = "1", //
            ratingDistribution = mapOf( //
                1 to 100, 2 to 50, 3 to 200, 4 to 150, 5 to 1000,
                6 to 250, 7 to 400, 8 to 350, 9 to 450, 10 to 244
            ),
            recommendationIds = listOf(2, 7, 4) //
        ),
        Anime(
            id = 4, //
            imageRes = R.drawable.pic, //
            title = "Вечная воля", //
            genreNames = listOf("Романтика", "Боевик", "Экшн"), //
            description = "Бай Сяоцюань рос без родителей в маленькой деревне и всегда стремился к чему-то большему...", //
            formattedRating = "5.0", //
            year = "2025",
            formattedEpisodes = "1", //
            ratingDistribution = mapOf( //
                1 to 100, 2 to 50, 3 to 200, 4 to 150, 5 to 1000,
                6 to 250, 7 to 400, 8 to 350, 9 to 450, 10 to 244
            ),
            recommendationIds = listOf(2, 7, 4) //
        ),Anime(
            id = 5, //
            imageRes = R.drawable.pic, //
            title = "Вечная воля", //
            genreNames = listOf("Романтика", "Боевик", "Экшн"), //
            description = "Бай Сяоцюань рос без родителей в маленькой деревне и всегда стремился к чему-то большему...", //
            formattedRating = "5.0", //
            year = "2025",
            formattedEpisodes = "1", //
            ratingDistribution = mapOf( //
                1 to 100, 2 to 50, 3 to 200, 4 to 150, 5 to 1000,
                6 to 250, 7 to 400, 8 to 350, 9 to 450, 10 to 244
            ),
            recommendationIds = listOf(2, 3, 4) //
        )
        ,Anime(
            id = 6, //
            imageRes = R.drawable.pic, //
            title = "Вечная воля", //
            genreNames = listOf("Романтика", "Боевик", "Экшн"), //
            description = "Бай Сяоцюань рос без родителей в маленькой деревне и всегда стремился к чему-то большему...", //
            formattedRating = "5.0", //
            year = "2025",
            formattedEpisodes = "1", //
            ratingDistribution = mapOf( //
                1 to 100, 2 to 50, 3 to 200, 4 to 150, 5 to 1000,
                6 to 250, 7 to 400, 8 to 350, 9 to 450, 10 to 244
            ),
            recommendationIds = listOf(2, 10, 4) //
        ),
        Anime(
            id = 7, //
            imageRes = R.drawable.pic, //
            title = "Вечная воля", //
            genreNames = listOf("Романтика", "Боевик", "Экшн"), //
            description = "Бай Сяоцюань рос без родителей в маленькой деревне и всегда стремился к чему-то большему...", //
            formattedRating = "5.0", //
            year = "2025",
            formattedEpisodes = "1", //
            ratingDistribution = mapOf( //
                1 to 100, 2 to 50, 3 to 200, 4 to 150, 5 to 1000,
                6 to 250, 7 to 400, 8 to 350, 9 to 450, 10 to 244
            ),
            recommendationIds = listOf(2, 3, 5) //
        ),
        Anime(
            id = 8, //
            imageRes = R.drawable.pic, //
            title = "Вечная воля", //
            genreNames = listOf("Романтика", "Боевик", "Экшн"), //
            description = "Бай Сяоцюань рос без родителей в маленькой деревне и всегда стремился к чему-то большему...", //
            formattedRating = "5.0", //
            year = "2025",
            formattedEpisodes = "1", //
            ratingDistribution = mapOf( //
                1 to 100, 2 to 50, 3 to 200, 4 to 150, 5 to 1000,
                6 to 250, 7 to 400, 8 to 350, 9 to 450, 10 to 244
            ),
            recommendationIds = listOf(2, 3, 4) //
        ),
        Anime(
            id = 9, //
            imageRes = R.drawable.pic, //
            title = "Вечная воля", //
            genreNames = listOf("Романтика", "Боевик", "Экшн"), //
            description = "Бай Сяоцюань рос без родителей в маленькой деревне и всегда стремился к чему-то большему...", //
            formattedRating = "5.0", //
            year = "2025",
            formattedEpisodes = "1", //
            ratingDistribution = mapOf( //
                1 to 100, 2 to 50, 3 to 200, 4 to 150, 5 to 1000,
                6 to 250, 7 to 400, 8 to 350, 9 to 450, 10 to 244
            ),
            recommendationIds = listOf(2, 3, 4) //
        ),
        Anime(
            id = 10, //
            imageRes = R.drawable.pic, //
            title = "Вечная воля", //
            genreNames = listOf("Романтика", "Боевик", "Экшн"), //
            description = "Бай Сяоцюань рос без родителей в маленькой деревне и всегда стремился к чему-то большему...", //
            formattedRating = "5.0", //
            year = "2025",
            formattedEpisodes = "1", //
            ratingDistribution = mapOf( //
                1 to 100, 2 to 50, 3 to 200, 4 to 150, 5 to 1000,
                6 to 250, 7 to 400, 8 to 350, 9 to 450, 10 to 244
            ),
            recommendationIds = listOf(2, 3, 4) //
        ),
        Anime(
            id = 11, //
            imageRes = R.drawable.pic, //
            title = "Вечная воля", //
            genreNames = listOf("Романтика", "Боевик", "Экшн"), //
            description = "Бай Сяоцюань рос без родителей в маленькой деревне и всегда стремился к чему-то большему...", //
            formattedRating = "5.0", //
            year = "2025",
            formattedEpisodes = "1", //
            ratingDistribution = mapOf( //
                1 to 100, 2 to 50, 3 to 200, 4 to 150, 5 to 1000,
                6 to 250, 7 to 400, 8 to 350, 9 to 450, 10 to 244
            ),
            recommendationIds = listOf(2, 3, 4) //
        ),
        Anime(
            id = 12, //
            imageRes = R.drawable.pic, //
            title = "Вечная воля", //
            genreNames = listOf("Романтика", "Боевик", "Экшн"), //
            description = "Бай Сяоцюань рос без родителей в маленькой деревне и всегда стремился к чему-то большему...", //
            formattedRating = "5.0", //
            year = "2025",
            formattedEpisodes = "1", //
            ratingDistribution = mapOf( //
                1 to 100, 2 to 50, 3 to 200, 4 to 150, 5 to 1000,
                6 to 250, 7 to 400, 8 to 350, 9 to 450, 10 to 244
            ),
            recommendationIds = listOf(2, 3, 4) //
        ),
        Anime(
            id = 13, //
            imageRes = R.drawable.pic, //
            title = "Вечная воля", //
            genreNames = listOf("Романтика", "Боевик", "Экшн"), //
            description = "Бай Сяоцюань рос без родителей в маленькой деревне и всегда стремился к чему-то большему...", //
            formattedRating = "5.0", //
            year = "2025",
            formattedEpisodes = "1", //
            ratingDistribution = mapOf( //
                1 to 100, 2 to 50, 3 to 200, 4 to 150, 5 to 1000,
                6 to 250, 7 to 400, 8 to 350, 9 to 450, 10 to 244
            ),
            recommendationIds = listOf(2, 3, 4) //
        )
    )
    override fun getAnimeList(): List<Anime> = animeList

    override fun getAnimeById(id: Int): Anime? {
        val anime = animeList.find { it.id == id }
        return anime?.copy(
            recommendations = anime.recommendationIds?.mapNotNull { rid ->
                animeList.find { it.id == rid }?.toAnimeCardData()
            }
        )
    }
}

private fun Anime.toAnimeCardData(): AnimeCardData {
    return AnimeCardData(
        id = this.id,
        imageRes = this.imageRes,
        title = this.title,
        genreNames = this.genreNames,
        rating = this.formattedRating.toFloatOrNull() ?: 0f,
        episodes = this.formattedEpisodes?.toIntOrNull()
    )
}