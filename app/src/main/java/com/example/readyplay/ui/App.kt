package com.example.readyplay.ui

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.readyplay.datastore.UserDataStore
import com.example.readyplay.infra.qr.QrCodeGenerator
import com.example.readyplay.ui.navigation.BottomNavigationMenu
import com.example.readyplay.ui.navigation.NavRoute
import com.example.readyplay.ui.navigation.bottomNavRoutes
import com.example.readyplay.ui.screens.cart.CartScreen
import com.example.readyplay.ui.screens.cart.CartScreenViewModel
import com.example.readyplay.ui.screens.details.StoreDetailsScreen
import com.example.readyplay.ui.screens.details.StoreDetailsScreenViewModel
import com.example.readyplay.ui.screens.login.LoginScreen
import com.example.readyplay.ui.screens.scan.ScanQRScreen
import com.example.readyplay.ui.screens.splash.SplashScreen
import com.example.readyplay.ui.screens.store.StoreScreen
import com.example.readyplay.ui.screens.store.StoreScreenViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun App(userDataStore: UserDataStore = koinInject()) {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentDestination = navBackStackEntry?.destination
    val showBottomBar = bottomNavRoutes.any { currentDestination?.route?.contains(it.name, ignoreCase = true) ?: false }

    val context = LocalContext.current

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationMenu(currentDestination) { dest ->
                    if (dest is NavRoute.Home) {
                        navController.navigate(dest) {
                            launchSingleTop = true
                        }
                    } else {
                        Toast.makeText(context, "Coming Soon!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        },
    ) {
        NavHost(navController = navController, startDestination = NavRoute.Splash, modifier = Modifier.padding(it)) {
            composable<NavRoute.Splash> {
                val isLoggedIn by userDataStore.isLoggedIn.collectAsStateWithLifecycle(false)
                SplashScreen(
                    onFinish = {
                        if (isLoggedIn.not()) {
                            navController.navigate(NavRoute.Login) {
                                popUpTo(route = NavRoute.Splash) { inclusive = true }
                            }
                        } else {
                            navController.navigate(NavRoute.Store) {
                                popUpTo(route = NavRoute.Splash) { inclusive = true }
                            }
                        }
                    },
                )
            }
            composable<NavRoute.Login> {
                LoginScreen(
                    onLogin = {
                        navController.navigate(NavRoute.Store) {
                            popUpTo(route = NavRoute.Login) { inclusive = true }
                        }
                    },
                )
            }
            navigation<NavRoute.Home>(startDestination = NavRoute.Store) {
                composable<NavRoute.Store> {
                    val storeViewModel: StoreScreenViewModel = koinViewModel()
                    StoreScreen(viewModel = storeViewModel, onScan = {
                        navController.navigate(NavRoute.Scan)
                    }, onGoToCart = {
                        navController.navigate(NavRoute.Cart)
                    }) { id ->
                        navController.navigate(NavRoute.MovieDetails(id = id, src = null))
                    }
                }
                composable<NavRoute.MovieDetails> { backStackEntry ->
                    val movieDetails: NavRoute.MovieDetails = backStackEntry.toRoute()
                    val detailsViewModel: StoreDetailsScreenViewModel = koinViewModel()
                    LaunchedEffect(key1 = movieDetails.id, key2 = movieDetails.src) {
                        detailsViewModel.handleGetDetails(movieDetails.id, movieDetails.src)
                    }
                    StoreDetailsScreen(
                        viewModel = detailsViewModel,
                        onBackClick = { navController.navigateUp() },
                    ) {
                        navController.navigate(NavRoute.Cart)
                    }
                }
                composable<NavRoute.Scan> {
                    ScanQRScreen(
                        onScanFail = {
                            Toast.makeText(context, "Failed to get a scan result", Toast.LENGTH_SHORT).show()
                            navController.navigateUp()
                        },
                    ) { result ->
                        QrCodeGenerator.qrCodeAdapter.fromJson(result)?.let {
                            navController.navigate(NavRoute.MovieDetails(it.external_id, it.external_source)) {
                                popUpTo(NavRoute.Store) {
                                    inclusive = false
                                }
                            }
                        } ?: kotlin.run { navController.navigateUp() }
                    }
                }
                composable<NavRoute.Cart> {
                    val cartViewModel: CartScreenViewModel = koinViewModel()
                    CartScreen(
                        viewModel = cartViewModel,
                        onBackClick = { navController.navigateUp() },
                    ) {
                        navController.navigate(NavRoute.Store) {
                            popUpTo(NavRoute.Store) {
                                inclusive = false
                            }
                        }
                    }
                }
            }
            composable<NavRoute.Saved> {
                Text(text = "Saved")
            }
            composable<NavRoute.Payment> {
                Text(text = "Payment")
            }
            composable<NavRoute.History> {
                Text(text = "History")
            }
            composable<NavRoute.Settings> {
                Text(text = "Settings")
            }
        }
    }
}
