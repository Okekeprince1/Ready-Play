package com.example.readyplay.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.example.readyplay.ui.theme.darkBlue
import com.example.readyplay.utils.formatAmount

@Composable
fun DealCard(
    movie: Movie,
    onClickItem: (Movie) -> Unit,
) {
    val isLiked = remember { mutableStateOf(false) }
    DealHeader()
    Spacing(height = 10.dp)
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable { onClickItem(movie) }
                .shadow(elevation = 1.dp, shape = MaterialTheme.shapes.large)
                .height(177.dp),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.surface),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = movie.image,
                modifier =
                    Modifier
                        .size(120.dp)
                        .weight(1f),
                contentScale = ContentScale.Fit,
                contentDescription = null,
            )
            Column(
                modifier =
                    Modifier
                        .weight(1f),
            ) {
                Text(
                    text = "Animation",
                    style =
                        MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                        ),
                )
                Spacing(height = 10.dp)
                Text(
                    text = movie.price.formatAmount(),
                    style =
                        MaterialTheme.typography.labelSmall.copy(
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.primary,
                        ),
                )
                Spacing(height = 5.dp)
                Text(
                    text = movie.title,
                    style =
                        MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Medium,
                            color = darkBlue,
                        ),
                )
                Spacing(height = 5.dp)
                Text(
                    text = movie.isPG,
                    style =
                        MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onSurface,
                        ),
                )
            }
        }
        IconButton(
            modifier =
                Modifier
                    .align(Alignment.TopEnd)
                    .padding(10.dp),
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
}

@Composable
fun DealHeader() {
    Row(
        modifier =
            Modifier
                .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.deals_of_the_day),
            style =
                MaterialTheme.typography.headlineMedium.copy(
                    letterSpacing = (-1).sp,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    color = Color.White,
                ),
        )
        Text(
            text = stringResource(R.string.see_all),
            style =
                MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                ),
        )
    }
}
