package com.example.readyplay.domain.repository.movie

import com.example.readyplay.domain.model.Movie

interface MovieRepository {
    suspend fun getMovieList(): Result<List<Movie>>
}
