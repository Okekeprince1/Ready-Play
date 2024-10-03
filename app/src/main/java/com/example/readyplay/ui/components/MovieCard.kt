package com.example.readyplay.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.readyplay.R
import com.example.readyplay.domain.model.Movie
import com.example.readyplay.utils.formatAmount

@Composable
fun MovieCard(
    movie: Movie,
    onClickItem: (Movie) -> Unit,
) {
    val isLiked = remember { mutableStateOf(false) }
    Column(
        modifier =
            Modifier
                .width(176.dp),
    ) {
        Box(
            modifier =
                Modifier
                    .size(165.dp)
                    .clickable { onClickItem(movie) }
                    .shadow(elevation = 1.dp, shape = MaterialTheme.shapes.large)
                    .background(color = MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center,
        ) {
            AsyncImage(
                modifier =
                    Modifier
                        .size(120.dp),
                model = movie.image,
                contentScale = ContentScale.Fit,
                alignment = Alignment.Center,
                contentDescription = null,
            )
            IconButton(
                modifier =
                    Modifier
                        .align(Alignment.TopEnd),
                onClick = { isLiked.value = !isLiked.value },
            ) {
                Image(
                    painter =
                        painterResource(
                            id =
                                if (isLiked.value) {
                                    R.drawable.ic_like_active
                                } else {
                                    R.drawable.ic_like_inactive
                                },
                        ),
                    contentDescription = null,
                )
            }
        }
        Spacing(height = 8.dp)
        Text(
            text = movie.price.formatAmount(),
            style =
                MaterialTheme.typography.labelSmall.copy(
                    fontSize = 14.sp,
                    color = Color.White,
                ),
        )
        Spacing(height = 5.dp)
        Text(
            text = movie.title,
            style =
                MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Yellow,
                ),
        )
    }
}

@Composable
fun RecommendHeader() {
    Text(
        modifier = Modifier,
        text = stringResource(R.string.recommended_for_you),
        style =
            MaterialTheme.typography.headlineMedium.copy(
                letterSpacing = (-1).sp,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = Color.White,
            ),
    )
    Spacing(height = 10.dp)
}
