package com.example.readyplay.ui.screens.cart

import androidx.lifecycle.ViewModel
import com.example.readyplay.domain.model.MovieDetails
import com.example.readyplay.shared.ShopCart
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CartScreenViewModel(
    private val cart: ShopCart,
) : ViewModel() {
    private val _uiState: MutableStateFlow<CartUiState> =
        MutableStateFlow(aggregateCart())
    val uiState: StateFlow<CartUiState> get() = _uiState.asStateFlow()

    fun clearCart() {
        cart.clear()
        aggregateCart()
        updateUI()
    }

    fun removeFromCart(movie: MovieDetails) {
        cart.removeFromCart(movie)
        updateUI()
    }

    fun onChangeItem(
        item: MovieDetails,
        newCount: Int,
    ) {
        if (newCount > 1) {
            cart.addToCart(item, newCount)
            updateUI()
        }
    }

    private fun updateUI() {
        _uiState.update { aggregateCart() }
    }

    private fun aggregateCart() =
        CartUiState.Content(
            data = cart.cart,
            totalSum = cart.cart.map { (item, count) -> item.price.toInt() * count }.sumOf { it }.toFloat(),
        )
}

sealed class CartUiState {
    data class Content(
        val data: Map<MovieDetails, Int> = mapOf(),
        val totalSum: Float = 0f,
    ) : CartUiState()
}
