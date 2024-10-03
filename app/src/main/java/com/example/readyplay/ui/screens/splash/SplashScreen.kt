package com.example.readyplay.ui.screens.splash

import android.window.SplashScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.readyplay.R
import com.example.readyplay.ui.theme.ReadyPlayTheme
import com.example.readyplay.ui.theme.black
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    onFinish: () -> Unit,
) {
    val currentOnFinish by rememberUpdatedState(onFinish)

    LaunchedEffect(true) {
        delay(2500)
        currentOnFinish()
    }
    Box(
        modifier =
            modifier
                .fillMaxSize()
                .background(color = black),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            modifier =
                Modifier.height(50.dp)
                    .width(202.dp),
            painter = painterResource(id = R.drawable.ready_play_logo),
            contentDescription = "splash",
        )
    }
}

@Preview
@Preview(showBackground = true)
@Composable
fun SplashPreview() {
    ReadyPlayTheme {
        SplashScreen(
            onFinish = {}
        )
    }
}
