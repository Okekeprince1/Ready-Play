package com.example.readyplay.ui.screens.store

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.readyplay.R
import com.example.readyplay.ui.components.CartButton
import com.example.readyplay.ui.components.DealCard
import com.example.readyplay.ui.components.ErrorMessage
import com.example.readyplay.ui.components.Loading
import com.example.readyplay.ui.components.MovieCard
import com.example.readyplay.ui.components.RecommendHeader
import com.example.readyplay.ui.components.ScanModal
import com.example.readyplay.ui.components.SearchItem
import com.example.readyplay.ui.components.Spacing
import kotlin.random.Random

@Composable
fun StoreScreen(
    modifier: Modifier = Modifier,
    viewModel: StoreScreenViewModel,
    onScan: () -> Unit,
    onGoToCart: () -> Unit,
    onClickItem: (String) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsState()
    val cartCount = viewModel.cartSize.collectAsState()

    var showScanModal by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        containerColor = Color.Black,
        topBar = {
            Column {
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(R.string.hello_daniel),
                        style =
                            MaterialTheme.typography.headlineLarge.copy(
                                letterSpacing = (-1).sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White,
                            ),
                    )
                    CartButton(itemCount = cartCount.value) {
                        onGoToCart()
                    }
                }
                SearchItem(onScanClick = { showScanModal = true })
                Spacing(height = 20.dp)
            }
        },
    ) { paddingValues ->
        when (uiState.value) {
            StoreUiState.Loading -> Loading(padding = paddingValues)
            is StoreUiState.Content -> {
                val movieList = (uiState.value as StoreUiState.Content).data

                movieList?.let {
                    val position = remember { Random.nextInt(0, it.size) }

                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                    ) {
                        LazyVerticalGrid(
                            modifier =
                                Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 16.dp),
                            columns = GridCells.Fixed(2),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            it.forEachIndexed { index, card ->
                                if (index == 0) {
                                    item(span = { GridItemSpan(maxLineSpan) }) {
                                        Column {
                                            DealCard(movie = it[position]) { movie ->
                                                onClickItem(movie.id)
                                            }
                                            Spacing(height = 20.dp)
                                            RecommendHeader()
                                        }
                                    }
                                } else {
                                    item {
                                        MovieCard(
                                            movie = card,
                                        ) {
                                            onClickItem(it.id)
                                        }
                                    }
                                }
                            }
                        }
                    }
                } ?: run {
                    ErrorMessage(message = stringResource(R.string.no_data_to_show), padding = paddingValues)
                }
            }
            is StoreUiState.Error -> {
                ErrorMessage(message = (uiState.value as StoreUiState.Error).message, padding = paddingValues)
            }
        }
        if (showScanModal) {
            ScanModal(onDismiss = { showScanModal = false }) {
                onScan()
                showScanModal = false
            }
        }
    }
}

// @Preview(showBackground = true)
// @Composable
// fun StorePreview() {
//    ReadyPlayTheme {
//        StoreScreen() {}
//    }
// }
