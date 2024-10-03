package com.example.readyplay.infra.qr

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

object QrCodeGenerator {
    val qrCodeAdapter: JsonAdapter<QrCode> by lazy {
        Moshi.Builder()
            .build()
            .adapter(QrCode::class.java)
    }

    fun generateQr(content: QrCode): Bitmap =
        BarcodeEncoder().encodeBitmap(
            qrCodeAdapter.toJson(content),
            BarcodeFormat.QR_CODE,
            500,
            500,
        )
}
