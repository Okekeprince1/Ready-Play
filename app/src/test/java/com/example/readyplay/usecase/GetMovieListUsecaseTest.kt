package com.example.readyplay.usecase

import com.example.readyplay.domain.repository.movie.MovieRepository
import com.example.readyplay.domain.usecase.GetMovieListUsecase
import com.example.readyplay.utils.getMovies
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

class GetMovieListUsecaseTest {
    private val getMovieRepo = mockk<MovieRepository>(relaxed = true)

    @Test
    fun test_success() =
        runTest {
            coEvery { getMovieRepo.getMovieList() } returns Result.success(getMovies())

            val usecase = GetMovieListUsecase(getMovieRepo)
            val response = usecase.invoke()

            assertEquals(getMovies(), response.last().data)
        }

    @Test
    fun test_failure() =
        runTest {
            coEvery { getMovieRepo.getMovieList() } returns Result.failure(RuntimeException("error"))

            val usecase = GetMovieListUsecase(getMovieRepo)
            val response = usecase.invoke()

            assertEquals("error", response.last().message)
        }

    @Test
    fun test_exception() =
        runTest {
            coEvery { getMovieRepo.getMovieList() } throws RuntimeException("error")

            val usecase = GetMovieListUsecase(getMovieRepo)
            val response = usecase.invoke()

            assertEquals("error", response.last().message)
        }
}
