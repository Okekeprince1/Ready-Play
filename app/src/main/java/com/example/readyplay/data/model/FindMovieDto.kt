package com.example.readyplay.data.model

import androidx.annotation.Keep
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class FindMovieDto(
    val movie_results: List<MovieDto>,
)
