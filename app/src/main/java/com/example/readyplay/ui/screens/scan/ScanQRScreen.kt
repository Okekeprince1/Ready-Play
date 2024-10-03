package com.example.readyplay.ui.screens.scan

import android.content.ActivityNotFoundException
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import com.example.readyplay.ui.theme.ReadyPlayTheme
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

@Composable
fun ScanQRScreen(
    onScanFail: () -> Unit,
    onScan: (String) -> Unit,
) {
    val launcher =
        rememberLauncherForActivityResult(ScanContract()) { scanIntentResult ->
            scanIntentResult?.let {
                if (scanIntentResult.contents.isNotEmpty()) {
                    onScan(scanIntentResult.contents)
                } else {
                    onScanFail()
                }
            } ?: run { onScanFail() }
        }

    LaunchedEffect(true) {
        try {
            launcher.launch(
                ScanOptions().apply {
                    setBeepEnabled(true)
                    setDesiredBarcodeFormats(ScanOptions.ALL_CODE_TYPES)
                    setPrompt("Scan QR code")
                    setCameraId(0)
                    setBeepEnabled(true)
                    setOrientationLocked(true)
                    setBarcodeImageEnabled(true)
                },
            )
        } catch (e: ActivityNotFoundException) {
            onScanFail()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScanQRScreenPreview() {
    ReadyPlayTheme {
        ScanQRScreen(onScanFail = {}) {}
    }
}
