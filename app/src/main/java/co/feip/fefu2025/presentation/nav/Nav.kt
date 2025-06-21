package co.feip.fefu2025.presentation.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import co.feip.fefu2025.data.remote.ApiService
import co.feip.fefu2025.data.repository.AnimeRepositoryImpl
import co.feip.fefu2025.domain.repository.AnimeRepository
import co.feip.fefu2025.domain.usecase.GetAnimeDetailUseCase
import co.feip.fefu2025.domain.usecase.GetAnimeListUseCase
import co.feip.fefu2025.domain.usecase.GetRecommendationsUseCase
import co.feip.fefu2025.domain.usecase.SearchAnimeUseCase
import co.feip.fefu2025.presentation.Screen.AnimeDetails.AnimeScreen
import co.feip.fefu2025.presentation.Screen.MainScreen.MainAnimeScreen
import co.feip.fefu2025.presentation.Screen.RecomendationList.RecommendationListScreen
import co.feip.fefu2025.presentation.Screen.SearchScreens.SearchScreen
import co.feip.fefu2025.presentation.details.AnimeDetailViewModel
import co.feip.fefu2025.presentation.details.MainViewModel
import co.feip.fefu2025.presentation.details.RecommendationViewModel
import co.feip.fefu2025.presentation.details.SearchViewModel

@Composable
fun Nav(modifier: Modifier = Modifier) {
    val navController: NavHostController = rememberNavController()


    val apiService = remember { ApiService.create() }
    val animeRepository: AnimeRepository = remember { AnimeRepositoryImpl(apiService) }

    val getAnimeListUseCase = remember { GetAnimeListUseCase(animeRepository) }
    val getAnimeDetailUseCase = remember { GetAnimeDetailUseCase(animeRepository) }
    val searchAnimeUseCase = remember { SearchAnimeUseCase(animeRepository) }
    val getRecommendationsUseCase = remember { GetRecommendationsUseCase(animeRepository) }


    NavHost(
        navController = navController,
        startDestination = "main",
        modifier = modifier
    ) {
        composable("main") {

            val mainViewModel: MainViewModel = viewModel(
                factory = MainViewModel.Factory(getAnimeListUseCase)
            )
            MainAnimeScreen(
                viewModel = mainViewModel,
                onAnimeClick = { animeId -> navController.navigate("anime/$animeId") },
                onSearchClick = { navController.navigate("search") }
            )
        }

        composable(
            route = "anime/{animeId}",
            arguments = listOf(navArgument("animeId") { type = NavType.IntType }),
            deepLinks = listOf(navDeepLink { uriPattern = "mysuperapp://anime/{animeId}" })
        ) { backStackEntry ->
            val animeId = backStackEntry.arguments?.getInt("animeId") ?: return@composable


            val detailViewModel: AnimeDetailViewModel = viewModel(
                factory = AnimeDetailViewModel.Factory(getAnimeDetailUseCase, animeId)
            )
            AnimeScreen(
                viewModel = detailViewModel,
                onAnimeClick = { id -> if (id != animeId) navController.navigate("anime/$id") },

                onRecommendationsClick = { navController.navigate("recommendations/$animeId") },
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(
            route = "recommendations/{animeId}",
            arguments = listOf(navArgument("animeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val animeId = backStackEntry.arguments?.getInt("animeId") ?: return@composable

            val recommendationViewModel: RecommendationViewModel = viewModel(
                factory = RecommendationViewModel.Factory(getRecommendationsUseCase, animeId)
            )
            RecommendationListScreen(
                viewModel = recommendationViewModel,
                onBackClick = { navController.popBackStack() },
                onAnimeClick = { id -> navController.navigate("anime/$id") }
            )
        }

        composable("search") {
            val searchViewModel: SearchViewModel = viewModel(
                factory = SearchViewModel.Factory(searchAnimeUseCase)
            )
            SearchScreen(
                viewModel = searchViewModel,
                onBackClick = { navController.popBackStack() },
                onAnimeClick = { id -> navController.navigate("anime/$id") }
            )
        }
    }
}