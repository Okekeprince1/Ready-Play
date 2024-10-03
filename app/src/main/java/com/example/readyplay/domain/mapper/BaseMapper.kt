package com.example.readyplay.domain.mapper

interface BaseMapper<K, V> {
    fun mapFrom(to: V): K

    fun mapTo(from: K): V
}
