package co.feip.fefu2025.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.math.max
import androidx.compose.ui.draw.clip
import co.feip.fefu2025.utils.RatingColors

@Composable
fun RatingChart(
    ratings: Map<Int, Int>,
    modifier: Modifier = Modifier
) {
    val maxCount = max(ratings.values.maxOrNull() ?: 1, 1)

    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Оценки пользователей",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            for (rating in 1..10) {
                val count = ratings[rating] ?: 0
                val heightRatio = count.toFloat() / maxCount
                val color = RatingColors.list[rating - 1]

                Box(
                    modifier = Modifier
                        .weight(1f),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Box(
                            modifier = Modifier
                                .height((heightRatio * 120).dp)
                                .width(16.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(color)
                        )
                        Text(
                            text = rating.toString(),
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }
    }
}