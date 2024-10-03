package com.example.readyplay.shared

import androidx.compose.runtime.getValue
import com.example.readyplay.domain.model.MovieDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ShopCart {
    val cart: MutableMap<MovieDetails, Int> = mutableMapOf()

    private val _cartSize = MutableStateFlow(cart.size)
    val cartSize = _cartSize.asStateFlow()

    private fun update() {
        _cartSize.update {
            cart.size
        }
    }

    fun addToCart(
        item: MovieDetails,
        count: Int = 1,
    ) {
        cart[item] = count
        update()
    }

    fun removeFromCart(item: MovieDetails) {
        cart.remove(item)
        update()
    }

    fun clear() {
        cart.clear()
        update()
    }
}
