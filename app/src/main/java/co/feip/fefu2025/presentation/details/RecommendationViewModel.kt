package co.feip.fefu2025.presentation.details

import androidx.lifecycle.ViewModel
import co.feip.fefu2025.data.repository.MockAnimeRepository
import co.feip.fefu2025.domain.model.Anime

class RecommendationViewModel : ViewModel() {
    private val repository = MockAnimeRepository()

    val recommendations: List<Anime> = repository.getRecommendations()
}