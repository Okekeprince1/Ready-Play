package com.example.readyplay.ui.screens.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.readyplay.R
import com.example.readyplay.ui.components.CartItem
import com.example.readyplay.ui.components.CheckoutModal
import com.example.readyplay.ui.components.Spacing
import com.example.readyplay.ui.components.SwipeToDeleteContainer
import com.example.readyplay.ui.theme.ReadyPlayTheme
import com.example.readyplay.utils.formatAmount
import org.koin.androidx.compose.koinViewModel

@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    viewModel: CartScreenViewModel = koinViewModel(),
    onBackClick: () -> Unit,
    onPurchase: () -> Unit,
) {
    val uiState =
        viewModel
            .uiState.collectAsState()
    val checkoutActive = derivedStateOf { (uiState.value as CartUiState.Content).data.isNotEmpty() }
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        containerColor = Color.Black,
        topBar = {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
            ) {
                IconButton(
                    modifier =
                        Modifier
                            .align(Alignment.TopStart),
                    onClick = { onBackClick() },
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_back_icon),
                        contentDescription = null,
                    )
                }
                Text(
                    modifier =
                        Modifier
                            .align(Alignment.Center),
                    text = stringResource(R.string.my_cart),
                    textAlign = TextAlign.Center,
                    style =
                        MaterialTheme.typography.titleLarge.copy(
                            color = Color.White,
                        ),
                )
                Spacing(20.dp)
            }
        },
        bottomBar = {
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
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = (uiState.value as CartUiState.Content).totalSum.toString().formatAmount(),
                        style =
                            MaterialTheme.typography.titleLarge.copy(
                                fontSize = 20.sp,
                                color = Color.Black,
                            ),
                    )
                }
                Button(
                    enabled = checkoutActive.value,
                    onClick = { showBottomSheet = true },
                    modifier =
                        Modifier
                            .padding(horizontal = 20.dp),
                    shape = MaterialTheme.shapes.large,
                ) {
                    Image(painter = painterResource(id = R.drawable.ic_checkout_white), contentDescription = null)
                    Spacing(width = 10.dp)
                    Text(
                        text = stringResource(R.string.check_out),
                    )
                }
            }
        },
    ) { paddingValues ->
        Spacing(height = 50.dp)
        LazyColumn(
            modifier =
                Modifier
                    .padding(paddingValues)
                    .fillMaxWidth(),
        ) {
            items(items = (uiState.value as CartUiState.Content).data.toList(), key = { it }) { (movie, count) ->
                SwipeToDeleteContainer(
                    item = movie,
                    onDelete = {
                        viewModel.removeFromCart(movie)
                    },
                ) { item ->
                    CartItem(item = item, itemCount = count) { newCount ->
                        viewModel.onChangeItem(movie, newCount)
                    }
                }
            }
        }

        if (showBottomSheet) {
            CheckoutModal(
                onDismiss = {
                    showBottomSheet = false
                },
            ) {
                showBottomSheet = false
                viewModel.clearCart()
                onPurchase()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CartScreenPreview() {
    ReadyPlayTheme {
//        CartScreen {}
    }
}
