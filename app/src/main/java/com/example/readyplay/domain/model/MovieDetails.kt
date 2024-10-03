package com.example.readyplay.domain.model

import kotlin.random.Random

data class MovieDetails(
    val id: Int,
    val name: String,
    val image: String,
    val overview: String,
    val isAdult: Boolean,
    val revenue: String,
    val price: String = Random.nextInt(5000, 7000).toString(),
) {
    val isPG = if (isAdult) "PG(18+)" else "PG(Kids)"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MovieDetails) return false

        return id == other.id
    }
}
