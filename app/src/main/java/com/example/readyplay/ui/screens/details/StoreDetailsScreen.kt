package com.example.readyplay.ui.screens.details

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.readyplay.R
import com.example.readyplay.ui.components.AddToCartModal
import com.example.readyplay.ui.components.CartButton
import com.example.readyplay.ui.components.ErrorMessage
import com.example.readyplay.ui.components.Loading
import com.example.readyplay.ui.components.Spacing
import com.example.readyplay.ui.theme.ReadyPlayTheme
import com.example.readyplay.utils.formatAmount
import org.koin.androidx.compose.koinViewModel

@Composable
fun StoreDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: StoreDetailsScreenViewModel = koinViewModel(),
    onBackClick: () -> Unit,
    onPayClick: () -> Unit,
) {
    val uiState = viewModel.uiState.collectAsState()
    val cartCount = viewModel.cartSize.collectAsState()
    val orderCount = remember { mutableIntStateOf(1) }

    var showQRModal by remember { mutableStateOf(false) }
    var showAddCartModal by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        containerColor = Color.Black,
        topBar = {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    onClick = { onBackClick() },
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_back_icon),
                        contentDescription = null,
                    )
                }
                CartButton(itemCount = cartCount.value) { onPayClick() }
            }
        },
        floatingActionButton = {
            if (uiState.value is MovieDetailsUiState.Content) {
                IconButton(
                    modifier =
                        Modifier.background(
                            shape = MaterialTheme.shapes.large,
                            color = MaterialTheme.colorScheme.primary,
                        ),
                    onClick = { showQRModal = true },
                    colors =
                        IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                        ),
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "qr",
                    )
                }
            }
        },
        bottomBar = {
            if (uiState.value is MovieDetailsUiState.Content) {
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(90.dp)
                            .background(
                                color = MaterialTheme.colorScheme.surface,
                            ),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column {
                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            text = stringResource(R.string.price),
                            style =
                                MaterialTheme.typography.labelSmall.copy(
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Normal,
                                ),
                        )
                        Spacing(height = 3.dp)
                        (uiState.value as MovieDetailsUiState.Content).data?.let {
                            Text(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                text = it.price.formatAmount(),
                                style =
                                    MaterialTheme.typography.titleLarge.copy(
                                        fontSize = 20.sp,
                                        color = Color.Black,
                                    ),
                            )
                        }
                    }
                    Button(
                        onClick = {
                            showAddCartModal = true
                            viewModel.addToCart(orderCount.value)
                        },
                        modifier =
                            Modifier
                                .padding(horizontal = 20.dp),
                        shape = MaterialTheme.shapes.large,
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_cart_white),
                            contentDescription = null,
                        )
                        Spacing(width = 10.dp)
                        Text(
                            text = stringResource(R.string.add_to_cart),
                        )
                    }
                }
            }
        },
    ) { paddingValues ->
        when (uiState.value) {
            MovieDetailsUiState.Loading -> Loading(padding = paddingValues)
            is MovieDetailsUiState.Content -> {
                val movieDetails = (uiState.value as MovieDetailsUiState.Content).data

                movieDetails?.let {
                    Column(
                        modifier =
                            Modifier
                                .padding(paddingValues)
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState()),
                    ) {
                        Spacing(height = 10.dp)
                        Box(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                                    .shadow(elevation = 1.dp, shape = MaterialTheme.shapes.large)
                                    .background(color = MaterialTheme.colorScheme.surface)
                                    .height(250.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            AsyncImage(
                                model = it.image,
                                modifier =
                                    Modifier
                                        .padding(vertical = 15.dp)
                                        .fillMaxHeight()
                                        .width(150.dp),
                                contentScale = ContentScale.Crop,
                                contentDescription = null,
                            )
                            IconButton(
                                modifier =
                                    Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(10.dp),
                                onClick = { /*TODO*/ },
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_like_active),
                                    contentDescription = null,
                                )
                            }
                        }
                        Spacing(height = 20.dp)
                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            text = "Animation",
                            style =
                                MaterialTheme.typography.titleMedium.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold,
                                ),
                        )
                        Spacing(height = 10.dp)
                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            text = movieDetails.name,
                            style =
                                MaterialTheme.typography.titleLarge.copy(
                                    color = Color.Yellow,
                                ),
                        )
                        Spacing(height = 10.dp)
                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            text = movieDetails.isPG,
                            style =
                                MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Normal,
                                    color = Color.White,
                                ),
                        )
                        Spacing(height = 20.dp)
                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            text = movieDetails.overview,
                            style =
                                MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Normal,
                                    color = Color.White,
                                ),
                        )
                        Spacing(height = 20.dp)
                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            text = stringResource(R.string.format),
                            style =
                                MaterialTheme.typography.labelSmall.copy(
                                    color = MaterialTheme.colorScheme.primary,
                                ),
                        )
                        Spacing(height = 10.dp)
                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            text = stringResource(R.string.quantity),
                            style =
                                MaterialTheme.typography.labelSmall.copy(
                                    color = MaterialTheme.colorScheme.primary,
                                ),
                        )
                        Spacing(height = 10.dp)
                        Row(
                            modifier =
                                Modifier
                                    .padding(horizontal = 16.dp)
                                    .animateContentSize(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            IconButton(enabled = orderCount.value > 1, onClick = { orderCount.value-- }) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_remove_shop),
                                    contentDescription = null,
                                )
                            }
                            Spacing(height = 8.dp)
                            Text(
                                text = orderCount.value.toString(),
                                style =
                                    MaterialTheme.typography.bodyMedium.copy(
                                        color = Color.White,
                                    ),
                            )
                            Spacing(height = 8.dp)
                            IconButton(
                                onClick = { orderCount.value++ },
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_add_shop),
                                    contentDescription = null,
                                )
                            }
                        }
                    }
                } ?: run {
                    ErrorMessage(message = stringResource(R.string.no_data_to_show), padding = paddingValues)
                }
            }
            is MovieDetailsUiState.Error -> {
                ErrorMessage(message = (uiState.value as MovieDetailsUiState.Error).message, padding = paddingValues)
            }
        }
    }
    if (showQRModal) {
        QRModal(id = viewModel.getMovieId) { showQRModal = false }
    }
    if (showAddCartModal) {
        AddToCartModal(
            itemCount = viewModel.cartSize.value,
            onContinue = {
                showAddCartModal = false
                onBackClick()
            },
        ) {
            showAddCartModal = false
            onPayClick()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StoreDetailsScreenPreview() {
    ReadyPlayTheme {
//        StoreDetailsScreen {}
    }
}
