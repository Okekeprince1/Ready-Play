package com.example.readyplay.infra.qr

import androidx.annotation.Keep
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class QrCode(
    val external_id: String,
    val external_source: String,
)
