package com.example.readyplay.viewmodel

import com.example.readyplay.MainDispatcherRule
import com.example.readyplay.domain.usecase.FindMovieUsecase
import com.example.readyplay.domain.usecase.GetMovieDetailsUsecase
import com.example.readyplay.shared.ShopCart
import com.example.readyplay.ui.screens.details.MovieDetailsUiState
import com.example.readyplay.ui.screens.details.StoreDetailsScreenViewModel
import com.example.readyplay.utils.NetworkResult
import com.example.readyplay.utils.getMovieDetails
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class GetMovieDetailsViewModelTest {
    @JvmField
    @Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getMovieDetailsUsecase = mockk<GetMovieDetailsUsecase>(relaxed = true)
    private val findMovieUsecase = mockk<FindMovieUsecase>(relaxed = true)
    private val shopCart = mockk<ShopCart>(relaxed = true)

    @Test
    fun test_get_character_details_success() =
        runTest {
            coEvery { getMovieDetailsUsecase.invoke(ID) } returns
                flowOf(
                    NetworkResult.Success(data = getMovieDetails()),
                )
            val viewModel =
                StoreDetailsScreenViewModel(
                    shopCart,
                    getMovieDetailsUsecase,
                    findMovieUsecase,
                )
            viewModel.handleGetDetails(ID, null)

            assertEquals(getMovieDetails(), (viewModel.uiState.value as MovieDetailsUiState.Content).data)
        }

    @Test
    fun test_get_character_details_failure() =
        runTest {
            coEvery { getMovieDetailsUsecase.invoke(ID) } returns
                flowOf(
                    NetworkResult.Error(message = "error"),
                )
            val viewModel =
                StoreDetailsScreenViewModel(
                    shopCart,
                    getMovieDetailsUsecase,
                    findMovieUsecase,
                )
            viewModel.handleGetDetails(ID, null)

            assertEquals("error", (viewModel.uiState.value as MovieDetailsUiState.Error).message)
        }

    companion object {
        const val ID = "123"
    }
}
