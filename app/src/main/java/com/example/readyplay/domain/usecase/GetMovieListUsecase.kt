package com.example.readyplay.domain.usecase

import com.example.readyplay.domain.repository.movie.MovieRepository
import com.example.readyplay.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetMovieListUsecase(
    private val movieRepo: MovieRepository,
) {
    operator fun invoke() =
        flow {
            emit(NetworkResult.Loading())
            val response = movieRepo.getMovieList()
            if (response.isSuccess) {
                emit(NetworkResult.Success(data = response.getOrThrow()))
            } else {
                emit(NetworkResult.Error(message = response.exceptionOrNull()?.localizedMessage))
            }
        }.catch {
            emit(NetworkResult.Error(it.message.toString()))
        }.flowOn(Dispatchers.IO)
}
