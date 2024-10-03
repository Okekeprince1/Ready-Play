package com.example.readyplay

import android.content.Context
import com.example.readyplay.di.AppModule
import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.KoinTest
import org.koin.test.verify.verify

@OptIn(KoinExperimentalAPI::class)
class CheckModulesTest : KoinTest {
    @Test
    fun checkAllModules() {
        AppModule.module.verify(
            extraTypes = listOf(Context::class),
        )
    }
}
