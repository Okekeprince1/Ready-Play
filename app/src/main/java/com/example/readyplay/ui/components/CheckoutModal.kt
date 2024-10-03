package com.example.readyplay.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.readyplay.R
import com.example.readyplay.ui.theme.ReadyPlayTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutModal(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        modifier =
            modifier.padding(
                horizontal = 16.dp,
            ),
        dragHandle = null,
        onDismissRequest = {
            onDismiss()
        },
        sheetState = sheetState,
        containerColor = Color.White,
    ) {
        Column(
            modifier =
                modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = 16.dp,
                    ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacing(height = 80.dp)
            Image(
                painter = painterResource(id = R.drawable.ic_confirm_checkout),
                contentDescription = "confirm",
            )
            Spacing(height = 20.dp)
            Text(
                text = stringResource(R.string.checkout_successful),
                style =
                    MaterialTheme.typography.titleMedium.copy(
                        fontSize = 20.sp,
                        lineHeight = 25.sp,
                        fontWeight = FontWeight.ExtraBold,
                    ),
            )
            Spacing(height = 15.dp)
            Text(
                text = stringResource(R.string.your_cart_has_been_cleared),
                style =
                    MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Normal,
                    ),
            )
            Spacing(height = 15.dp)
            Button(
                onClick = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            onConfirm()
                        }
                    }
                },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                shape = MaterialTheme.shapes.small,
            ) {
                Text(
                    text = stringResource(R.string.continue_shopping),
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddToCartModal(
    modifier: Modifier = Modifier,
    itemCount: Int,
    onContinue: () -> Unit,
    onGoToCart: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        modifier =
            modifier.padding(
                horizontal = 16.dp,
            ),
        dragHandle = null,
        onDismissRequest = {
            onContinue()
        },
        sheetState = sheetState,
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = 16.dp,
                    ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacing(height = 80.dp)
            Image(
                painter = painterResource(id = R.drawable.ic_confirm_checkout),
                contentDescription = "confirm",
            )
            Spacing(height = 15.dp)
            Text(
                text = stringResource(R.string.added_to_cart),
                style =
                    MaterialTheme.typography.titleMedium.copy(
                        fontSize = 20.sp,
                        lineHeight = 25.sp,
                        fontWeight = FontWeight.ExtraBold,
                    ),
            )
            Spacing(height = 15.dp)
            Text(
                text = "$itemCount item${if (itemCount > 1) "s" else ""}",
                style =
                    MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Normal,
                    ),
            )
            Spacing(height = 15.dp)
            Button(
                onClick = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            onGoToCart()
                        }
                    }
                },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .shadow(15.dp, MaterialTheme.shapes.large, true, MaterialTheme.colorScheme.error),
                shape = MaterialTheme.shapes.small,
            ) {
                Text(
                    text = stringResource(R.string.go_to_cart),
                )
            }
            Spacing(height = 15.dp)
            Text(
                modifier =
                    Modifier.clickable {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                onContinue()
                            }
                        }
                    },
                text = stringResource(id = R.string.continue_shopping),
                style =
                    MaterialTheme.typography.titleMedium.copy(
                        lineHeight = 25.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.primary,
                    ),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CheckoutModalPreview() {
    ReadyPlayTheme {
        CheckoutModal(
            onDismiss = {},
            onConfirm = {},
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> RemoveCartModal(
    modifier: Modifier = Modifier,
    item: T,
    onDismiss: () -> Unit,
    onDeleteItem: (T) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        modifier =
            modifier.padding(
                horizontal = 16.dp,
            ),
        onDismissRequest = {
            onDismiss()
        },
        containerColor = Color.White,
        sheetState = sheetState,
    ) {
        Column(
            modifier =
                modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = 16.dp,
                    ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacing(height = 80.dp)
            Image(
                painter = painterResource(id = R.drawable.ic_trash_red),
                contentDescription = "trash",
            )
            Spacing(height = 15.dp)
            Text(
                text = stringResource(R.string.delete_item),
                style =
                    MaterialTheme.typography.titleMedium.copy(
                        fontSize = 20.sp,
                        lineHeight = 25.sp,
                        fontWeight = FontWeight.ExtraBold,
                    ),
            )
            Spacing(height = 15.dp)
            Text(
                text = stringResource(R.string.delete_this_item_warning),
                style =
                    MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Normal,
                    ),
            )
            Spacing(height = 15.dp)
            Button(
                onClick = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            onDeleteItem(item)
                        }
                    }
                },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .shadow(15.dp, MaterialTheme.shapes.large, true, MaterialTheme.colorScheme.error),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                    ),
                shape = MaterialTheme.shapes.small,
            ) {
                Text(
                    text = stringResource(R.string.confirm),
                )
            }
            Spacing(height = 15.dp)
            Text(
                modifier =
                    Modifier.clickable {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                onDismiss()
                            }
                        }
                    },
                text = stringResource(R.string.cancel),
                style =
                    MaterialTheme.typography.titleMedium.copy(
                        lineHeight = 25.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.error,
                    ),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanModal(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        modifier =
            modifier.padding(
                horizontal = 16.dp,
            ),
        dragHandle = null,
        onDismissRequest = {
            onDismiss()
        },
        sheetState = sheetState,
        containerColor = Color.White,
    ) {
        Column(
            modifier =
                modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = 16.dp,
                    ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacing(height = 40.dp)
            Text(
                text = stringResource(R.string.what_would_you_like_to_scan),
                style =
                    MaterialTheme.typography.titleMedium.copy(
                        fontSize = 20.sp,
                        lineHeight = 25.sp,
                        fontWeight = FontWeight.ExtraBold,
                    ),
            )
            Spacing(height = 20.dp)
            Image(
                modifier =
                    Modifier.clickable {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                onConfirm()
                            }
                        }
                    },
                painter = painterResource(id = R.drawable.qr_code_bg),
                contentDescription = "confirm",
            )
            Spacing(height = 30.dp)
            Text(
                modifier =
                    Modifier.clickable {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                onDismiss()
                            }
                        }
                    },
                text = stringResource(R.string.not_now),
                style =
                    MaterialTheme.typography.titleMedium.copy(
                        fontSize = 15.sp,
                        lineHeight = 25.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.primary,
                    ),
            )
        }
    }
}

@Composable
fun ScanModalPreview() {
    ReadyPlayTheme {
        ScanModal(
            onDismiss = {},
            onConfirm = {},
        )
    }
}
