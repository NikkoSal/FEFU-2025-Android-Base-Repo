package co.feip.fefu2025.presentation.Screen.RecomendationList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.feip.fefu2025.presentation.details.RecommendationViewModel
import co.feip.fefu2025.presentation.components.AnimeCard
import co.feip.fefu2025.domain.model.AnimeCardData
import co.feip.fefu2025.presentation.details.AnimeUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendationListScreen(
    viewModel: RecommendationViewModel = viewModel(),
    onBackClick: () -> Unit,
    onAnimeClick: (Int) -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Может понравиться", fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        when (val currentState = state) {
            is AnimeUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFF4CAF50))
                }
            }

            is AnimeUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Ошибка: не удалось загрузить рекомендации",
                            color = Color.Red
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = { viewModel.loadRecommendations() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                        ) {
                            Text("Повторить")
                        }
                    }
                }
            }

            is AnimeUiState.Success -> {
                val recommendations = currentState.animeList

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
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    items(recommendations) { anime ->
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
