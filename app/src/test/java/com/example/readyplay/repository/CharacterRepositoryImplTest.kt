package com.example.readyplay.repository

import com.example.readyplay.data.MovieApi
import com.example.readyplay.data.repository.movie.MovieRepositoryImpl
import com.example.readyplay.domain.mapper.movie.MovieMapper
import com.example.readyplay.utils.testMovieResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response

class MovieRepositoryImplTest {
    private val hogwartApi = mockk<MovieApi>()

    private val mapper = MovieMapper()

    @Test
    fun test_success() =
        runTest {
            coEvery { hogwartApi.getMovieList() } returns Response.success(200, testMovieResponse())

            val movieRepo = MovieRepositoryImpl(hogwartApi, mapper)
            val response = movieRepo.getMovieList()

            assertEquals(testMovieResponse().results.map { mapper.mapTo(it) }, response.getOrThrow())
        }

    @Test
    fun test_success_with_null_data() =
        runTest {
            coEvery { hogwartApi.getMovieList() } returns Response.success(null)

            val movieRepo = MovieRepositoryImpl(hogwartApi, mapper)
            val response = movieRepo.getMovieList()

            val errorMsg = "error occurred"
            assertEquals(errorMsg, response.exceptionOrNull()?.message)
        }

    @Test
    fun test_fail() =
        runTest {
            coEvery { hogwartApi.getMovieList() } returns
                Response.error(
                    404,
                    "".toResponseBody(null),
                )

            val movieRepo = MovieRepositoryImpl(hogwartApi, mapper)
            val response = movieRepo.getMovieList()

            val errorMsg = "error occurred"
            assertEquals(errorMsg, response.exceptionOrNull()?.message)
        }

    @Test
    fun test_throw_exception() =
        runTest {
            coEvery { hogwartApi.getMovieList() } throws RuntimeException("error")

            val movieRepo = MovieRepositoryImpl(hogwartApi, mapper)
            val response = movieRepo.getMovieList()

            val errorMsg = "error"
            assertEquals(errorMsg, response.exceptionOrNull()?.message)
        }
}
