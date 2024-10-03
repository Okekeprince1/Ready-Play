package com.example.readyplay.domain.model

import com.example.readyplay.utils.Constant

data class ExternalID(
    val id: String,
    val imdb_id: String = Constant.IMDB_ID,
)
