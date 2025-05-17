package co.feip.fefu2025.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.flowlayout.FlowRow
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import co.feip.fefu2025.domain.model.AnimeCardData
import co.feip.fefu2025.utils.Genres
import co.feip.fefu2025.R


@Composable
fun AnimeCard(
    data: AnimeCardData,
    modifier: Modifier = Modifier
        .width(160.dp)
        .height(250.dp)
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = data.title,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                minLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )

            data.episodes?.let { episodes ->
                Text(
                    text = "$episodes эп.",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            Image(
                painter = painterResource(id = data.imageRes),
                contentDescription = data.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(4f / 3f)
                    .clip(RoundedCornerShape(8.dp))
                    .padding(top = 6.dp)
            )

            FlowRow(
                modifier = Modifier
                    .padding(top = 6.dp)
                    .heightIn(max = 80.dp),
                mainAxisSpacing = 4.dp,
                crossAxisSpacing = 4.dp
            ) {
                data.genreNames.forEach { genreName ->
                    val genreColor = Genres.LIST.find { it.first == genreName }?.second?.let {
                        Color(it)
                    } ?: Color.Gray

                    GenreChip(
                        genreName = genreName,
                        genreColor = genreColor
                    )
                }
            }

            RatingBar(
                rating = data.rating,
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 4.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}



@Composable
private fun GenreChip(
    genreName: String,
    genreColor: Color,
    modifier: Modifier = Modifier
) {
    Text(
        text = genreName,
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(
                genreColor.copy(alpha = 0.12f),
                RoundedCornerShape(16.dp)
            )
            .border(
                width = 1.dp,
                color = genreColor.copy(alpha = 0.24f),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp),
        color = genreColor,
        fontSize = 10.sp,
        style = MaterialTheme.typography.labelSmall
    )
}

@Composable
private fun RatingBar(
    rating: Float,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = "Рейтинг",
            tint = Color(0xFFFFC107),
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = "%.1f".format(rating),
            modifier = Modifier.padding(start = 4.dp),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Medium
        )
    }
}


@Preview
@Composable
fun PreviewAnimeCard() {
    MaterialTheme {
        AnimeCard(
            data = AnimeCardData(
                imageRes = R.drawable.pic,
                title = "Вечная воля",
                genreNames = listOf("Романтика", "Боевик", "Экшн"),
                rating = 5f,
                episodes = 1
            ),
            modifier = Modifier
                .width(200.dp)
                .padding(10.dp)
        )
    }
}