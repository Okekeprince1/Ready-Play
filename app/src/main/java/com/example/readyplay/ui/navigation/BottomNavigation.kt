package com.example.readyplay.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination

@Composable
fun BottomNavigationMenu(
    currentDestination: NavDestination?,
    onClick: (NavRoute) -> Unit,
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        bottomNavRoutes.forEach { btmRoute ->
            val isSelected = currentDestination?.route?.contains(btmRoute.name, ignoreCase = true) ?: false
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = btmRoute.icon),
                        contentDescription = btmRoute.name,
                        tint =
                            if (isSelected) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.tertiary
                            },
                    )
                },
                label = { Text(btmRoute.name) },
                selected = currentDestination?.route.let { it.equals(btmRoute.name) },
                onClick = {
                    onClick(btmRoute.route)
                },
            )
        }
    }
}
