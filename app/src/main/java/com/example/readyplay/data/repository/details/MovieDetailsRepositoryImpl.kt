package com.example.readyplay.data.repository.details

import com.example.readyplay.data.MovieApi
import com.example.readyplay.domain.mapper.movie.MovieDetailsMapper
import com.example.readyplay.domain.model.MovieDetails
import com.example.readyplay.domain.repository.details.MovieDetailsRepository

class MovieDetailsRepositoryImpl(
    private val api: MovieApi,
    private val mapper: MovieDetailsMapper,
) : MovieDetailsRepository {
    override suspend fun getMovieById(id: String): Result<MovieDetails> {
        return try {
            val response = api.getMovieById(id)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(mapper.mapTo(it))
                } ?: run { Result.failure(Exception("error occurred")) }
            } else {
                Result.failure(Exception("error occurred"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
