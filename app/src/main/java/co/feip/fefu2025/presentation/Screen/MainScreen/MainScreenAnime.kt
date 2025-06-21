package co.feip.fefu2025.presentation.Screen.MainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import co.feip.fefu2025.presentation.details.UiState
import co.feip.fefu2025.presentation.components.AnimeCard
import co.feip.fefu2025.presentation.details.MainViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAnimeScreen(
    viewModel: MainViewModel,
    onAnimeClick: (Int) -> Unit,
    onSearchClick: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    val listState = rememberLazyGridState()

    Scaffold(
        topBar = {
            // ВАШ КОД TOPBAR - НЕ ТРОГАЕМ
            Surface(shadowElevation = 4.dp) {
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
            is UiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFF4CAF50))
                }
            }

            is UiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = currentState.message ?: "Произошла неизвестная ошибка",
                            color = Color.Red
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = { viewModel.refresh() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                        ) {
                            Text("Повторить")
                        }
                    }
                }
            }

            is UiState.Success -> {
                // ВАШ КОД SUCCESS - ДОБАВЛЯЕМ ЛОГИКУ ПАГИНАЦИИ
                val paginationState = currentState.data // 7. Получаем PaginationState
                val animeList = paginationState.items   // 8. Получаем список из него

                LazyVerticalGrid(
                    state = listState, // 9. ДОБАВЛЕНО: Передаем состояние грида
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
                    items(
                        items = animeList,
                        key = { it.id }
                    ) { anime ->
                        AnimeCard(
                            data = anime,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onAnimeClick(anime.id) }
                        )
                    }

                    if (paginationState.isLoadingNextPage) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(color = Color(0xFF4CAF50))
                            }
                        }
                    }
                }
            }
        }
    }

    val shouldLoadMore = remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val totalItems = layoutInfo.totalItemsCount
            if (totalItems == 0) return@derivedStateOf false
            val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1
            lastVisibleItemIndex >= totalItems - 2
        }
    }

    LaunchedEffect(shouldLoadMore.value) {
        val currentState = viewModel.uiState.value
        if (shouldLoadMore.value && currentState is UiState.Success && !currentState.data.isLoadingNextPage && currentState.data.canLoadMore) {
            viewModel.loadNextPage()
        }
    }
}