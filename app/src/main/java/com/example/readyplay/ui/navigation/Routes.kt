package com.example.readyplay.ui.navigation

import androidx.annotation.DrawableRes
import com.example.readyplay.R
import kotlinx.serialization.Serializable

sealed class NavRoute {
    @Serializable
    object Splash : NavRoute()

    @Serializable
    object Login : NavRoute()

    @Serializable
    object Home : NavRoute()

    @Serializable
    object Store : NavRoute()

    @Serializable
    object Cart : NavRoute()

    @Serializable
    data class MovieDetails(val id: String, val src: String?) : NavRoute()

    @Serializable
    object Scan : NavRoute()

    @Serializable
    object Saved : NavRoute()

    @Serializable
    object Payment : NavRoute()

    @Serializable
    object History : NavRoute()

    @Serializable
    object Settings : NavRoute()
}

data class BottomNavRoute<T : Any>(
    val name: String,
    val route: T,
    @DrawableRes val icon: Int,
)

val bottomNavRoutes =
    listOf(
        BottomNavRoute("Store", NavRoute.Store, R.drawable.ic_home_icon_active),
        BottomNavRoute("Saved", NavRoute.Saved, R.drawable.ic_store_icon_inactive),
        BottomNavRoute("Payment", NavRoute.Payment, R.drawable.ic_payment_inactive),
        BottomNavRoute("History", NavRoute.History, R.drawable.ic_history_inactive),
        BottomNavRoute("Settings", NavRoute.Settings, R.drawable.ic_setting_inactive),
    )
