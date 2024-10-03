package com.example.readyplay.domain.repository.find

import com.example.readyplay.domain.model.ExternalID
import com.example.readyplay.domain.model.MovieDetails

interface FindMovieRepository {
    suspend fun findMovie(
        externalId: String,
        src: String,
    ): Result<MovieDetails>

    suspend fun getExternalId(id: String): Result<ExternalID>
}
