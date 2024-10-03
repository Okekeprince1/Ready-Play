package com.example.readyplay.domain.repository.details

import com.example.readyplay.domain.model.MovieDetails

interface MovieDetailsRepository {
    suspend fun getMovieById(id: String): Result<MovieDetails>
}
