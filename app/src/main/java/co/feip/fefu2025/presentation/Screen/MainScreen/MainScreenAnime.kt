package co.feip.fefu2025.presentation.Screen.MainScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.feip.fefu2025.domain.model.AnimeCardData
import co.feip.fefu2025.presentation.components.AnimeCard
import co.feip.fefu2025.presentation.details.MainViewModel
import androidx.compose.ui.Alignment
import co.feip.fefu2025.presentation.details.AnimeUiState
import androidx.compose.foundation.background


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAnimeScreen(
    viewModel: MainViewModel = viewModel(),
    onAnimeClick: (Int) -> Unit,
    onSearchClick: () -> Unit
) {
    val state = viewModel.uiState

    Scaffold(
        topBar = {
            Surface {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .clickable { onSearchClick() },
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Поиск...",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        when (val currentState = state) {
            is AnimeUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFF4CAF50))
                }
            }

            is AnimeUiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Ошибка: не удалось загрузить аниме",
                            color = Color.Red
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = { viewModel.loadAnime() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                        ) {
                            Text("Повторить")
                        }
                    }
                }
            }

            is AnimeUiState.Success -> {
                val animeList = currentState.animeList
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
                            data = AnimeCardData(
                                id = anime.id,
                                imageRes = anime.imageRes,
                                title = anime.title,
                                genreNames = anime.genreNames,
                                rating = anime.formattedRating.toFloatOrNull() ?: 0f,
                                episodes = anime.formattedEpisodes?.toIntOrNull()
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onAnimeClick(anime.id) }
                        )
                    }
                }
            }
        }
    }
}
