package com.example.readyplay.data.repository.movie

import com.example.readyplay.data.MovieApi
import com.example.readyplay.domain.mapper.movie.MovieMapper
import com.example.readyplay.domain.model.Movie
import com.example.readyplay.domain.repository.movie.MovieRepository

class MovieRepositoryImpl(
    private val api: MovieApi,
    private val mapper: MovieMapper,
) : MovieRepository {
    override suspend fun getMovieList(): Result<List<Movie>> {
        return try {
            val response = api.getMovieList()
            if (response.isSuccessful) {
                response.body()?.let { movieList ->
                    Result.success(movieList.results.map { mapper.mapTo(it) })
                } ?: run { Result.failure(Exception("error occurred")) }
            } else {
                Result.failure(Exception("error occurred"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    companion object {
        const val RESPONSE_LIMIT = 20
    }
}
