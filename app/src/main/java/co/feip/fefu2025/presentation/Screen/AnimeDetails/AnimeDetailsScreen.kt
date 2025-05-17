package co.feip.fefu2025.presentation.Screen.AnimeDetails

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import co.feip.fefu2025.ui.components.RatingChart
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import co.feip.fefu2025.FlexBoxLayout
import co.feip.fefu2025.utils.Genres
import co.feip.fefu2025.presentation.detail.AnimeDetailViewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import co.feip.fefu2025.R
import co.feip.fefu2025.domain.model.Anime
import co.feip.fefu2025.presentation.components.AnimeCard

@Composable
fun AnimeScreen(
    animeId: Int,
    viewModelFactory: AnimeDetailViewModel.Factory,
    onAnimeClick: (Int) -> Unit
) {
    val viewModel: AnimeDetailViewModel = viewModel(factory = viewModelFactory)
    val anime by viewModel.anime

    if (anime != null) {
        AnimeDetailScreen(anime!!, onAnimeClick)
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Загрузка...")
        }
    }
}


@Composable
fun AnimeDetailScreen(
    anime: Anime,
    onAnimeClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = anime.title,
            modifier = Modifier.padding(top = 16.dp),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )

        anime.formattedEpisodes?.let { episodes ->
            EpisodesInfo(
                episodes = episodes,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Image(
            painter = painterResource(id = anime.imageRes),
            contentDescription = anime.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .aspectRatio(3f / 4f)
                .clip(RoundedCornerShape(12.dp))
                .padding(top = 8.dp)
        )

        anime.description?.let { description ->
            Text(
                text = stringResource(R.string.description_label),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .wrapContentWidth(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.titleMedium.copy(
                    textAlign = TextAlign.Center
                ),
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = description,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .wrapContentWidth(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.bodyMedium.copy(
                    textAlign = TextAlign.Center
                )
            )
        }

        Text(
            text = stringResource(R.string.genres_label),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .wrapContentWidth(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.titleMedium.copy(
                textAlign = TextAlign.Center
            ),
            fontWeight = FontWeight.SemiBold
        )

        FlexBoxGenreLayout(
            genreNames = anime.genreNames,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            RatingInfo(rating = anime.formattedRating)
            anime.year?.let { year ->
                YearInfo(year = year)
            }
        }

        anime.ratingDistribution?.let { ratings ->
            RatingChart(
                ratings = ratings,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            )
        }

        anime.recommendations?.takeIf { it.isNotEmpty() }?.let { recommendations ->
            Text(
                text = "Может понравиться",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 8.dp)
                    .wrapContentWidth(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.titleMedium.copy(
                    textAlign = TextAlign.Center
                ),
                fontWeight = FontWeight.SemiBold
            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(recommendations) { rec ->
                    AnimeCard(
                        data = rec,
                        modifier = Modifier
                            .clickable { rec.id?.let { onAnimeClick(it) } }
                            .width(160.dp)
                    )
                }
            }
        }
    }
}



@Composable
private fun RatingInfo(
    rating: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = stringResource(R.string.rating_description),
            tint = Color(0xFFFFC107),
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = rating,
            modifier = Modifier.padding(start = 4.dp),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun YearInfo(
    year: String,
    modifier: Modifier = Modifier
) {
    InfoRow(
        value = year,
        label = stringResource(R.string.year_label),
        modifier = modifier
    )
}

@Composable
private fun EpisodesInfo(
    episodes: String,
    modifier: Modifier = Modifier
) {
    InfoRow(
        value = episodes,
        label = stringResource(R.string.episodes_label),
        modifier = modifier
    )
}

@Composable
private fun InfoRow(
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = label,
            modifier = Modifier.padding(start = 4.dp),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun FlexBoxGenreLayout(
    genreNames: List<String>,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current
) {
    AndroidView(
        factory = { ctx ->
            FlexBoxLayout(ctx).apply {
                genreNames.forEach { genreName ->
                    val genreColor = Genres.LIST.find { it.first == genreName }?.second
                        ?.let { Color(it) }
                        ?: Color.Gray

                    addView(createGenreTextView(genreName, genreColor, ctx))
                }
            }
        },
        modifier = modifier,
        update = { view ->
            view.removeAllViews()
            genreNames.forEach { genreName ->
                val genreColor = Genres.LIST.find { it.first == genreName }?.second
                    ?.let { Color(it) }
                    ?: Color.Gray

                view.addView(createGenreTextView(genreName, genreColor, context))
            }
        }
    )
}

private fun createGenreTextView(
    genreName: String,
    genreColor: Color,
    context: Context
): TextView {
    return TextView(context).apply {
        text = genreName
        setTextColor(genreColor.toArgb())
        textSize = 14f
        setPadding(32, 16, 32, 16)

        background = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 24f
            setColor(adjustAlpha(genreColor, 0.1f).toArgb())
            setStroke(2, genreColor.toArgb())
        }

        gravity = Gravity.CENTER
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
}

private fun adjustAlpha(color: Color, factor: Float): Color {
    return color.copy(alpha = color.alpha * factor)
}

