package com.example.readyplay.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductionCompany(
    val id: Int,
    val logo_path: Any?,
    val name: String,
    val origin_country: String,
)
