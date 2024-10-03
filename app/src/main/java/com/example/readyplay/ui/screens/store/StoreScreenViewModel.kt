package com.example.readyplay.ui.screens.store

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readyplay.domain.model.Movie
import com.example.readyplay.domain.usecase.GetMovieListUsecase
import com.example.readyplay.shared.ShopCart
import com.example.readyplay.utils.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class StoreScreenViewModel(
    private val usecase: GetMovieListUsecase,
    cart: ShopCart,
) : ViewModel() {
    private val _uiState: MutableStateFlow<StoreUiState> =
        MutableStateFlow(StoreUiState.Loading)
    val uiState: StateFlow<StoreUiState> get() = _uiState.asStateFlow()

    val cartSize = cart.cartSize

    init {
        getMovieListForStore()
    }

    private fun getMovieListForStore() =
        usecase.invoke()
            .onEach { result ->
                when (result) {
                    is NetworkResult.Loading -> {
                        _uiState.update { StoreUiState.Loading }
                    }

                    is NetworkResult.Error -> {
                        _uiState.update { StoreUiState.Error(message = result.message.toString()) }
                    }

                    is NetworkResult.Success -> {
                        _uiState.update { StoreUiState.Content(data = result.data) }
                    }
                }
            }.launchIn(viewModelScope)
}

sealed class StoreUiState {
    object Loading : StoreUiState()

    data class Content(val data: List<Movie>? = null) : StoreUiState()

    data class Error(val message: String) : StoreUiState()
}
