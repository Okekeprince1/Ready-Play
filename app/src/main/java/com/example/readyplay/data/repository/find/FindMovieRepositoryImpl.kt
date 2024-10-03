package com.example.readyplay.data.repository.find

import com.example.readyplay.data.MovieApi
import com.example.readyplay.domain.mapper.movie.ExternalMovieIdMapper
import com.example.readyplay.domain.mapper.movie.FindMovieMapper
import com.example.readyplay.domain.model.ExternalID
import com.example.readyplay.domain.model.MovieDetails
import com.example.readyplay.domain.repository.find.FindMovieRepository

class FindMovieRepositoryImpl(
    private val api: MovieApi,
    private val mapper: FindMovieMapper,
    private val externalIDMapper: ExternalMovieIdMapper,
) : FindMovieRepository {
    override suspend fun findMovie(
        externalId: String,
        src: String,
    ): Result<MovieDetails> {
        return try {
            val response = api.findMovie(externalId, src)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(mapper.mapTo(it.movie_results[0]))
                } ?: run { Result.failure(Exception("error occurred")) }
            } else {
                Result.failure(Exception("error occurred"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getExternalId(id: String): Result<ExternalID> {
        return try {
            val response = api.getMovieExternalIds(id)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(externalIDMapper.mapTo(it))
                } ?: run { Result.failure(Exception("error occurred")) }
            } else {
                Result.failure(Exception("error occurred"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
