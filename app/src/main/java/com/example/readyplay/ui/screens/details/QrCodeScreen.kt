package com.example.readyplay.ui.screens.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.readyplay.R
import com.example.readyplay.infra.qr.QrCode
import com.example.readyplay.infra.qr.QrCodeGenerator
import com.example.readyplay.ui.components.ErrorMessage
import com.example.readyplay.ui.components.Loading
import com.example.readyplay.ui.components.Spacing
import com.example.readyplay.ui.theme.ReadyPlayTheme
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QRModal(
    modifier: Modifier = Modifier,
    id: Int,
    viewModel: QRModalViewModel = koinViewModel(),
    onDismiss: () -> Unit,
) {
    val uiState = viewModel.uiState.collectAsState()

    LaunchedEffect(id) {
        viewModel.getExternalID(id)
    }

    val sheetState = rememberModalBottomSheetState()
    val paddingValues = PaddingValues(20.dp)
    ModalBottomSheet(
        modifier =
            modifier.padding(
                horizontal = 16.dp,
            ),
        onDismissRequest = {
            onDismiss()
        },
        sheetState = sheetState,
    ) {
        when (uiState.value) {
            QRModalUiState.Loading -> Loading(padding = PaddingValues(50.dp), color = Color.Black)

            is QRModalUiState.Content -> {
                val content = (uiState.value as QRModalUiState.Content).data
                content?.let {
                    val qrCodeImage =
                        remember {
                            QrCodeGenerator.generateQr(
                                content =
                                    QrCode(
                                        external_id = it.id,
                                        external_source = it.imdb_id,
                                    ),
                            )
                        }
                    Column(
                        modifier =
                            modifier
                                .fillMaxSize()
                                .padding(
                                    horizontal = 16.dp,
                                ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Spacing(height = 20.dp)
                        Image(
                            bitmap =
                                qrCodeImage.asImageBitmap(),
                            contentDescription = "confirm",
                        )
                        Spacing(height = 20.dp)
                        Text(
                            text = stringResource(R.string.scan_qr_code_text),
                            style =
                                MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Normal,
                                ),
                        )
                    }
                } ?: run {
                    ErrorMessage(
                        message = stringResource(R.string.no_data_to_show),
                        padding = paddingValues,
                        color = Color.Black,
                    )
                }
            }

            is QRModalUiState.Error -> {
                ErrorMessage(
                    message = (uiState.value as QRModalUiState.Error).message,
                    padding = paddingValues,
                    color = Color.Black,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QRModalPreview() {
    ReadyPlayTheme {
        QRModal(id = 43) {}
    }
}
