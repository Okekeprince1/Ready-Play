package com.example.readyplay.di

import com.example.readyplay.data.repository.details.MovieDetailsRepositoryImpl
import com.example.readyplay.data.repository.find.FindMovieRepositoryImpl
import com.example.readyplay.data.repository.movie.MovieRepositoryImpl
import com.example.readyplay.datastore.UserDataStore
import com.example.readyplay.domain.mapper.movie.ExternalMovieIdMapper
import com.example.readyplay.domain.mapper.movie.FindMovieMapper
import com.example.readyplay.domain.mapper.movie.MovieDetailsMapper
import com.example.readyplay.domain.mapper.movie.MovieMapper
import com.example.readyplay.domain.repository.details.MovieDetailsRepository
import com.example.readyplay.domain.repository.find.FindMovieRepository
import com.example.readyplay.domain.repository.movie.MovieRepository
import com.example.readyplay.domain.usecase.FindMovieUsecase
import com.example.readyplay.domain.usecase.GetExternalIdUsecase
import com.example.readyplay.domain.usecase.GetMovieDetailsUsecase
import com.example.readyplay.domain.usecase.GetMovieListUsecase
import com.example.readyplay.shared.ShopCart
import com.example.readyplay.ui.screens.cart.CartScreenViewModel
import com.example.readyplay.ui.screens.details.QRModalViewModel
import com.example.readyplay.ui.screens.details.StoreDetailsScreenViewModel
import com.example.readyplay.ui.screens.login.LoginScreenViewModel
import com.example.readyplay.ui.screens.store.StoreScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModule {
    val module
        get() =
            module {
                single { UserDataStore(get()) }

                single<MovieRepository> { MovieRepositoryImpl(get(), get()) }
                single<MovieDetailsRepository> {
                    MovieDetailsRepositoryImpl(get(), get())
                }
                single<FindMovieRepository> {
                    FindMovieRepositoryImpl(get(), get(), get())
                }

                single { ShopCart() }
                single { MovieMapper() }
                single { FindMovieMapper() }
                single { MovieDetailsMapper() }
                single { MovieDetailsMapper() }
                single { ExternalMovieIdMapper() }

                single { FindMovieUsecase(get()) }
                single { GetMovieListUsecase(get()) }
                single { GetMovieDetailsUsecase(get()) }
                single { GetExternalIdUsecase(get()) }

                viewModel { QRModalViewModel(get()) }
                viewModel { CartScreenViewModel(get()) }
                viewModel { LoginScreenViewModel(get()) }
                viewModel { StoreScreenViewModel(get(), get()) }
                viewModel { StoreDetailsScreenViewModel(get(), get(), get()) }
            }
}
