package com.example.readyplay.viewmodel

import com.example.readyplay.MainDispatcherRule
import com.example.readyplay.domain.usecase.GetMovieListUsecase
import com.example.readyplay.shared.ShopCart
import com.example.readyplay.ui.screens.store.StoreScreenViewModel
import com.example.readyplay.ui.screens.store.StoreUiState
import com.example.readyplay.utils.NetworkResult
import com.example.readyplay.utils.getMovies
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class StoreViewModelTest {
    @JvmField
    @Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getMovieListUsecase = mockk<GetMovieListUsecase>(relaxed = true)
    private val shopCart = mockk<ShopCart>(relaxed = true)

    @Test
    fun test_get_movie_success() =
        runTest {
            coEvery { getMovieListUsecase.invoke() } returns
                flowOf(
                    NetworkResult.Success(data = getMovies()),
                )
            val viewModel = StoreScreenViewModel(getMovieListUsecase, shopCart)

            assertEquals(getMovies(), (viewModel.uiState.value as StoreUiState.Content).data)
        }

    @Test
    fun test_get_movie_failure() =
        runTest {
            coEvery { getMovieListUsecase.invoke() } returns
                flowOf(
                    NetworkResult.Error(message = "error"),
                )
            val viewModel = StoreScreenViewModel(getMovieListUsecase, shopCart)

            assertEquals("error", (viewModel.uiState.value as StoreUiState.Error).message)
        }
}
