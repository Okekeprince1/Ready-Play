package com.example.readyplay.usecase

import com.example.readyplay.domain.repository.details.MovieDetailsRepository
import com.example.readyplay.domain.usecase.GetMovieDetailsUsecase
import com.example.readyplay.utils.getMovieDetails
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetMovieDetailsUsecaseTest {
    private val movieDetailsRepo = mockk<MovieDetailsRepository>(relaxed = true)

    @Test
    fun test_success() =
        runTest {
            coEvery { movieDetailsRepo.getMovieById(ID) } returns
                Result.success(
                    getMovieDetails(),
                )

            val usecase = GetMovieDetailsUsecase(movieDetailsRepo)
            val response = usecase.invoke(ID)

            assertEquals(getMovieDetails(), response.last().data)
        }

    @Test
    fun test_failure() =
        runTest {
            coEvery { movieDetailsRepo.getMovieById(ID) } returns Result.failure(RuntimeException("error"))

            val usecase = GetMovieDetailsUsecase(movieDetailsRepo)
            val response = usecase.invoke(ID)

            assertEquals("error", response.last().message)
        }

    @Test
    fun test_exception() =
        runTest {
            coEvery { movieDetailsRepo.getMovieById(ID) } throws RuntimeException("error")

            val usecase = GetMovieDetailsUsecase(movieDetailsRepo)
            val response = usecase.invoke(ID)

            assertEquals("error", response.last().message)
        }

    companion object {
        const val ID = "123"
    }
}
