package com.example.readyplay.domain.model

import kotlin.random.Random

data class Movie(
    val id: String,
    val title: String,
    val image: String,
    val isAdult: Boolean,
    val price: String = Random.nextInt(5000, 7000).toString(),
) {
    val isPG = if (isAdult) "PG(18+)" else "PG(Kids)"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Movie) return false

        return id == other.id
    }
}
