package com.example.readyplay.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.readyplay.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartButton(
    modifier: Modifier = Modifier,
    itemCount: Int,
    onClick: () -> Unit,
) {
    BadgedBox(
        modifier =
            modifier
                .clickable { onClick() },
        badge = {
            if (itemCount > 0) {
                Badge(
                    containerColor = Color.Red,
                    contentColor = Color.White,
                ) {
                    Text("$itemCount")
                }
            }
        },
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_scan_yellow),
            contentDescription = null,
        )
    }
}
