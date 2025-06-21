package co.feip.fefu2025.presentation.Screen.Favorites

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import co.feip.fefu2025.presentation.details.UiState
import co.feip.fefu2025.presentation.components.AnimeCard
import co.feip.fefu2025.presentation.favorites.FavoritesViewModel
import co.feip.fefu2025.domain.model.AnimeCardData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel,
    onAnimeClick: (animeCard: AnimeCardData) -> Unit,
    onBackClick: () -> Unit
) {
    val uiState by viewModel.favoritesState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Избранное") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Назад")
                    }
                }
            )
        }
    ) { innerPadding ->
        when (val currentState = uiState) {
            is UiState.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is UiState.Error -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Произошла ошибка", color = MaterialTheme.colorScheme.error)
                }
            }
            is UiState.Success -> {
                val favoritesList = currentState.data
                if (favoritesList.isEmpty()) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Список избранного пуст", textAlign = TextAlign.Center)
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(
                            start = 8.dp,
                            end = 8.dp,
                            top = innerPadding.calculateTopPadding() + 8.dp,
                            bottom = innerPadding.calculateBottomPadding() + 8.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(
                            items = favoritesList,
                            key = { it.id }
                        ) { animeCard ->
                            AnimeCard(
                                data = animeCard,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onAnimeClick(animeCard) }
                            )
                        }
                    }
                }
            }
        }
    }
}