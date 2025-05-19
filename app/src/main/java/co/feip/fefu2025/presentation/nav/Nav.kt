package co.feip.fefu2025.presentation.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import co.feip.fefu2025.presentation.Screen.MainScreen.MainAnimeScreen
import co.feip.fefu2025.presentation.Screen.AnimeDetails.AnimeScreen
import co.feip.fefu2025.data.repository.MockAnimeRepository
import co.feip.fefu2025.domain.usecase.GetAnimeDetailUseCase
import co.feip.fefu2025.domain.usecase.GetAnimeListUseCase
import co.feip.fefu2025.presentation.details.MainViewModel
import co.feip.fefu2025.presentation.details.AnimeDetailViewModel
import co.feip.fefu2025.presentation.Screen.RecomendationList.RecommendationListScreen
import androidx.navigation.navDeepLink


@Composable
fun Nav(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    val repository = MockAnimeRepository()
    val listUseCase = GetAnimeListUseCase(repository)
    val detailUseCase = GetAnimeDetailUseCase(repository)

    val mainViewModelFactory = MainViewModel.Factory(listUseCase)

    NavHost(
        navController = navController,
        startDestination = "main",
        modifier = modifier
    ) {
        composable("main") {
            val mainViewModel: MainViewModel = viewModel(factory = mainViewModelFactory)
            MainAnimeScreen(
                viewModel = mainViewModel,
                onAnimeClick = { animeId ->
                    navController.navigate("anime/$animeId")
                }
            )
        }

        composable(
            route = "anime/{animeId}",
            arguments = listOf(navArgument("animeId") { type = NavType.IntType }),
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "mysuperapp://anime/{animeId}"
                }
            )
        ) { backStackEntry ->
            val animeId = backStackEntry.arguments?.getInt("animeId") ?: return@composable
            val detailViewModelFactory = AnimeDetailViewModel.Factory(detailUseCase, animeId)
            AnimeScreen(
                animeId = animeId,
                viewModelFactory = detailViewModelFactory,
                onAnimeClick = { id -> navController.navigate("anime/$id") },
                onRecommendationsClick = { navController.navigate("recommendations") },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("recommendations") {
            RecommendationListScreen(
                onBackClick = { navController.popBackStack() },
                onAnimeClick = { id -> navController.navigate("anime/$id") }
            )
        }
    }
}