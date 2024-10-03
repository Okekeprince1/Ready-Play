package com.example.readyplay.repository

import com.example.readyplay.data.MovieApi
import com.example.readyplay.data.repository.details.MovieDetailsRepositoryImpl
import com.example.readyplay.domain.mapper.movie.MovieDetailsMapper
import com.example.readyplay.utils.testMovieDetailsResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response

class MovieDetailsRepositoryImplTest {
    private val hogwartApi = mockk<MovieApi>()

    private val mapper = MovieDetailsMapper()

    @Test
    fun test_success() =
        runTest {
            coEvery { hogwartApi.getMovieById(ID) } returns Response.success(200, testMovieDetailsResponse())

            val movieDetailsRepo = MovieDetailsRepositoryImpl(hogwartApi, mapper)
            val response = movieDetailsRepo.getMovieById(ID)

            assertEquals(mapper.mapTo(testMovieDetailsResponse()).id, response.getOrThrow().id)
        }

    @Test
    fun test_success_with_null_data() =
        runTest {
            coEvery { hogwartApi.getMovieById(ID) } returns Response.success(null)

            val movieDetailsRepo = MovieDetailsRepositoryImpl(hogwartApi, mapper)
            val response = movieDetailsRepo.getMovieById(ID)

            val errorMsg = "error occurred"
            assertEquals(errorMsg, response.exceptionOrNull()?.message)
        }

    @Test
    fun test_fail() =
        runTest {
            coEvery { hogwartApi.getMovieById(ID) } returns
                Response.error(
                    404,
                    "".toResponseBody(null),
                )

            val movieDetailsRepo = MovieDetailsRepositoryImpl(hogwartApi, mapper)
            val response = movieDetailsRepo.getMovieById(ID)

            val errorMsg = "error occurred"
            assertEquals(errorMsg, response.exceptionOrNull()?.message)
        }

    @Test
    fun test_throw_exception() =
        runTest {
            coEvery { hogwartApi.getMovieById(ID) } throws RuntimeException("error")

            val movieDetailsRepo = MovieDetailsRepositoryImpl(hogwartApi, mapper)
            val response = movieDetailsRepo.getMovieById(ID)

            val errorMsg = "error"
            assertEquals(errorMsg, response.exceptionOrNull()?.message)
        }

    companion object {
        const val ID = "123"
    }
}
