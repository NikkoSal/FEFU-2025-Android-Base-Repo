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
import co.feip.fefu2025.data.local.AppDatabase
import co.feip.fefu2025.domain.usecase.*
import co.feip.fefu2025.presentation.Screen.AnimeDetails.AnimeScreen
import co.feip.fefu2025.presentation.Screen.Favorites.FavoriteDetailScreen
import co.feip.fefu2025.presentation.Screen.Favorites.FavoritesScreen
import co.feip.fefu2025.presentation.Screen.MainScreen.MainAnimeScreen
import co.feip.fefu2025.presentation.Screen.RecomendationList.RecommendationListScreen
import co.feip.fefu2025.presentation.Screen.SearchScreens.SearchScreen
import co.feip.fefu2025.presentation.favorites.FavoritesViewModel
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import androidx.compose.ui.platform.LocalContext
import co.feip.fefu2025.presentation.favorites.FavoriteDetailViewModel


@Composable
fun Nav(modifier: Modifier = Modifier) {
    val navController: NavHostController = rememberNavController()
    val context = LocalContext.current

    val dependencies = remember {
        val db = AppDatabase.getDatabase(context)
        val animeDao = db.animeDao()
        val apiService = ApiService.create()
        val animeRepository: AnimeRepository = AnimeRepositoryImpl(apiService, animeDao)

        object {

            val getAnimeList = GetAnimeListUseCase(animeRepository)
            val getAnimeDetail = GetAnimeDetailUseCase(animeRepository)
            val searchAnime = SearchAnimeUseCase(animeRepository)
            val getRecommendations = GetRecommendationsUseCase(animeRepository)
            val getFavorites = GetFavoritesUseCase(animeRepository)
            val isFavorite = IsFavoriteUseCase(animeRepository)
            val addToFavorites = AddToFavoritesUseCase(animeRepository)
            val removeFromFavorites = RemoveFromFavoritesUseCase(animeRepository)
            val getFavoriteDetail = GetFavoriteDetailUseCase(animeRepository)
        }
    }

    NavHost(
        navController = navController,
        startDestination = "main",
        modifier = modifier
    ) {
        composable("main") {
            val mainViewModel: MainViewModel = viewModel(factory = MainViewModel.Factory(dependencies.getAnimeList))
            MainAnimeScreen(
                viewModel = mainViewModel,
                onAnimeClick = { animeId -> navController.navigate("anime/$animeId") },
                onSearchClick = { navController.navigate("search") },
                onFavoritesClick = { navController.navigate("favorites") }
            )
        }

        composable(
            route = "anime/{animeId}",
            arguments = listOf(navArgument("animeId") { type = NavType.IntType }),
            deepLinks = listOf(navDeepLink { uriPattern = "mysuperapp://anime/{animeId}" })
        ) { backStackEntry ->
            val animeId = backStackEntry.arguments?.getInt("animeId") ?: return@composable

            val detailViewModel: AnimeDetailViewModel = viewModel(
                factory = AnimeDetailViewModel.Factory(
                    getAnimeDetailUseCase = dependencies.getAnimeDetail,
                    isFavoriteUseCase = dependencies.isFavorite,
                    addToFavoritesUseCase = dependencies.addToFavorites,
                    removeFromFavoritesUseCase = dependencies.removeFromFavorites,
                    animeId = animeId
                )
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
                factory = RecommendationViewModel.Factory(dependencies.getRecommendations, animeId)
            )
            RecommendationListScreen(
                viewModel = recommendationViewModel,
                onBackClick = { navController.popBackStack() },
                onAnimeClick = { id -> navController.navigate("anime/$id") }
            )
        }

        composable("search") {
            val searchViewModel: SearchViewModel = viewModel(
                factory = SearchViewModel.Factory(dependencies.searchAnime)
            )
            SearchScreen(
                viewModel = searchViewModel,
                onBackClick = { navController.popBackStack() },
                onAnimeClick = { id -> navController.navigate("anime/$id") }
            )
        }
        composable("favorites") {
            val favoritesViewModel: FavoritesViewModel = viewModel(
                factory = FavoritesViewModel.Factory(dependencies.getFavorites)
            )
            FavoritesScreen(
                viewModel = favoritesViewModel,
                onBackClick = { navController.popBackStack() },
                onAnimeClick = { animeCard ->
                    navController.navigate("favorite_detail/${animeCard.id}")
                }
            )
        }

        composable(
            route = "favorite_detail/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0

            val favoriteDetailViewModel: FavoriteDetailViewModel = viewModel(
                factory = FavoriteDetailViewModel.Factory(dependencies.getFavoriteDetail, id)
            )

            FavoriteDetailScreen(
                viewModel = favoriteDetailViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
