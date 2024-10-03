package com.example.readyplay.ui.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readyplay.domain.model.MovieDetails
import com.example.readyplay.domain.usecase.FindMovieUsecase
import com.example.readyplay.domain.usecase.GetMovieDetailsUsecase
import com.example.readyplay.shared.ShopCart
import com.example.readyplay.utils.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class StoreDetailsScreenViewModel(
    private val cart: ShopCart,
    private val usecase: GetMovieDetailsUsecase,
    private val findMovieUsecase: FindMovieUsecase,
) : ViewModel() {
    private val _uiState: MutableStateFlow<MovieDetailsUiState> =
        MutableStateFlow(MovieDetailsUiState.Loading)
    val uiState: StateFlow<MovieDetailsUiState> get() = _uiState.asStateFlow()

    val cartSize = cart.cartSize
    private var movieDetails: MovieDetails? = null
    val getMovieId: Int
        get() = movieDetails?.id ?: 0

    fun handleGetDetails(
        id: String,
        src: String?,
    ) {
        src?.let {
            movieDetailsFromSrc(id, src)
        } ?: kotlin.run {
            movieDetails(id)
        }
    }

    private fun movieDetails(id: String) =
        usecase.invoke(id)
            .onEach { mapUiState(it) }.launchIn(viewModelScope)

    private fun movieDetailsFromSrc(
        id: String,
        src: String,
    ) = findMovieUsecase.invoke(id, src)
        .onEach { mapUiState(it) }.launchIn(viewModelScope)

    private fun mapUiState(result: NetworkResult<MovieDetails>) {
        when (result) {
            is NetworkResult.Error -> {
                _uiState.update {
                    MovieDetailsUiState.Error(
                        message = result.message.toString(),
                    )
                }
            }

            is NetworkResult.Loading ->
                _uiState.update { MovieDetailsUiState.Loading }

            is NetworkResult.Success ->
                _uiState.update {
                    movieDetails = result.data
                    MovieDetailsUiState.Content(
                        data = result.data,
                    )
                }
        }
    }

    fun addToCart(orderCount: Int) {
        movieDetails?.let {
            cart.addToCart(it, orderCount)
        }
    }
}

sealed class MovieDetailsUiState {
    object Loading : MovieDetailsUiState()

    data class Content(val data: MovieDetails? = null) : MovieDetailsUiState()

    data class Error(val message: String) : MovieDetailsUiState()
}
