package co.feip.fefu2025.presentation.Screen.SearchScreens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.feip.fefu2025.domain.model.AnimeCardData
import co.feip.fefu2025.presentation.components.AnimeCard
import co.feip.fefu2025.presentation.details.AnimeUiState
import co.feip.fefu2025.presentation.details.SearchViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModelFactory: SearchViewModel.Factory,
    onBackClick: () -> Unit,
    onAnimeClick: (Int) -> Unit
) {
    val viewModel: SearchViewModel = viewModel(factory = viewModelFactory)
    val state by viewModel.uiState.collectAsState()
    var query by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    var searchJob by remember { mutableStateOf<Job?>(null) }

    LaunchedEffect(query) {
        searchJob?.cancel()
        searchJob = coroutineScope.launch {
            delay(600)
            viewModel.updateQuery(query)
        }
    }

    Scaffold(
        topBar = {
            Surface {
                Column {
                    TextField(
                        value = query,
                        onValueChange = { query = it },  // Упростили обработчик
                        placeholder = { Text("Поиск...") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null,
                                modifier = Modifier.clickable { onBackClick() },
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                        trailingIcon = {
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
        }
    ) { innerPadding ->
        when (state) {
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
                            text = "Ошибка при поиске",
                            color = Color.Red
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                searchJob?.cancel()
                                viewModel.updateQuery(query)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                        ) {
                            Text("Повторить")
                        }
                    }
                }
            }

            is AnimeUiState.Success -> {
                val results = (state as AnimeUiState.Success).animeList
                if (results.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Ничего не найдено", color = Color.Gray)
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
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(results) { anime ->
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
}