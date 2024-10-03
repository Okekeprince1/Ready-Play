package com.example.readyplay

import android.app.Application
import com.example.readyplay.di.ApiModule
import com.example.readyplay.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(
                AppModule.module,
                ApiModule.module,
            )
        }
    }
}
