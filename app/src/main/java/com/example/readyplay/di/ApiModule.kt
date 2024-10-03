package com.example.readyplay.di

import com.example.readyplay.data.AuthorizationInterceptor
import com.example.readyplay.data.MovieApi
import com.example.readyplay.utils.Constant
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiModule {
    val module
        get() =
            module {
                single {
                    Retrofit.Builder()
                        .baseUrl(Constant.BASE_API_URL)
                        .client(
                            OkHttpClient.Builder().apply {
                                addNetworkInterceptor(
                                    HttpLoggingInterceptor().apply {
                                        level = HttpLoggingInterceptor.Level.BODY
                                    },
                                )
                                addInterceptor(AuthorizationInterceptor())
                            }.build(),
                        )
                        .addConverterFactory(
                            MoshiConverterFactory.create(
                                Moshi.Builder()
                                    .addLast(KotlinJsonAdapterFactory())
                                    .build(),
                            ),
                        )
                        .build()
                        .create(MovieApi::class.java)
                }
            }
}
