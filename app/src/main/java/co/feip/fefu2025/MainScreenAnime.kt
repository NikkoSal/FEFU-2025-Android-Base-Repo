package co.feip.fefu2025

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.WindowInsets

@Composable
fun MainAnimeScreen(
    animeList: List<AnimeCardData>,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        topBar = {
            Surface() {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(38.dp)
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                    }

                    TextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Поиск...") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        singleLine = true,
                        textStyle = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        },
        modifier = modifier
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(
                start = 8.dp,
                end = 8.dp,
                top = innerPadding.calculateTopPadding() + 8.dp,
                bottom = innerPadding.calculateBottomPadding() + 8.dp
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(animeList) { anime ->
                AnimeCard(
                    data = anime,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    val sampleList = listOf(
        AnimeCardData(
            imageRes = R.drawable.pic,
            title = "Бойцовский клуб любви",
            genreNames = listOf("Романтика", "Боевик"),
            rating = 8.7f,
            episodes = 12
        ),
        AnimeCardData(
            imageRes = R.drawable.pic,
            title = "Тайны алой ночи",
            genreNames = listOf("Мистика", "Экшн"),
            rating = 8.9f,
            episodes = 13
        ),
        AnimeCardData(
            imageRes = R.drawable.pic,
            title = "Комедийный взрыв",
            genreNames = listOf("Комедия", "Экшн"),
            rating = 8.1f,
            episodes = 10
        ),
        AnimeCardData(
            imageRes = R.drawable.pic,
            title = "Любовь и кулаки",
            genreNames = listOf("Романтика", "Экшн"),
            rating = 8.5f,
            episodes = 24
        ),
        AnimeCardData(
            imageRes = R.drawable.pic,
            title = "Магия розовой луны",
            genreNames = listOf("Мистика", "Романтика"),
            rating = 9.0f,
            episodes = 11
        ),
        AnimeCardData(
            imageRes = R.drawable.pic,
            title = "Смех в бою",
            genreNames = listOf("Комедия", "Боевик"),
            rating = 8.2f,
            episodes = 13
        ),
        AnimeCardData(
            imageRes = R.drawable.pic,
            title = "Танец клинка",
            genreNames = listOf("Экшн", "Боевик"),
            rating = 8.8f,
            episodes = 26
        ),
        AnimeCardData(
            imageRes = R.drawable.pic,
            title = "Сердце комедии",
            genreNames = listOf("Комедия", "Романтика"),
            rating = 7.9f,
            episodes = 12
        ),
        AnimeCardData(
            imageRes = R.drawable.pic,
            title = "Битва чувств",
            genreNames = listOf("Боевик", "Романтика"),
            rating = 8.4f,
            episodes = 14
        ),
        AnimeCardData(
            imageRes = R.drawable.pic,
            title = "Темная сцена",
            genreNames = listOf("Мистика", "Экшн"),
            rating = 8.6f,
            episodes = 16
        )
    )

    MaterialTheme {
        MainAnimeScreen(animeList = sampleList)
    }
}
