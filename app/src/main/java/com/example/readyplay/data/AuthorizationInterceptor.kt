package com.example.readyplay.data

import com.example.readyplay.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request =
            chain.request().newBuilder()
                .header(
                    "Authorization",
                    "Bearer ${BuildConfig.API_KEY}",
                )
                .build()

        return chain.proceed(request)
    }
}
