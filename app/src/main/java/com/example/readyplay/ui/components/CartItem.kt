package com.example.readyplay.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.readyplay.R
import com.example.readyplay.domain.model.MovieDetails
import com.example.readyplay.utils.formatAmount

@Composable
fun CartItem(
    modifier: Modifier = Modifier,
    item: MovieDetails,
    itemCount: Int,
    onChange: (Int) -> Unit = {},
) {
    Row(modifier = modifier.padding(start = 16.dp)) {
        Box(
            modifier =
                Modifier
                    .size(80.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = MaterialTheme.shapes.small
                    ),
            contentAlignment = Alignment.Center,
        ) {
            AsyncImage(
                model = item.image,
                modifier =
                    Modifier
                        .size(70.dp),
                contentScale = ContentScale.Fit,
                alignment = Alignment.Center,
                contentDescription = null,
            )
        }
        Spacing(width = 16.dp)
        Column(
            modifier = Modifier.height(IntrinsicSize.Min),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Yellow,
                ),
            )
            Spacing(height = 8.dp)
            Text(
                text = item.isPG,
                style =
                    MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Normal,
                        color = Color.White,
                    ),
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = item.price.formatAmount(),
                    style =
                        MaterialTheme.typography.titleMedium.copy(
                            color = Color.White,
                            fontSize = 15.sp,
                            lineHeight = 25.sp,
                            letterSpacing = 0.sp,
                            fontWeight = FontWeight.ExtraBold,
                        ),
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(enabled = itemCount > 0, onClick = { onChange(itemCount - 1) }) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_remove_shop),
                            contentDescription = null,
                        )
                    }
                    Spacing(height = 8.dp)
                    Text(
                        text = itemCount.toString(),
                        style =
                            MaterialTheme.typography.bodyMedium.copy(
                                color = Color.White,
                            ),
                    )
                    Spacing(height = 8.dp)
                    IconButton(
                        onClick = { onChange(itemCount + 1) },
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_add_shop),
                            contentDescription = null,
                        )
                    }
                }
            }
        }
    }
    Spacing(height = 16.dp)
}
