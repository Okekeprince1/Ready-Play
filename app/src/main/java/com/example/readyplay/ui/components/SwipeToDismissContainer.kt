package com.example.readyplay.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun <T> SwipeToDeleteContainer(
    item: T,
    onDelete: (T) -> Unit,
    content: @Composable (T) -> Unit,
) {
    var showDeleteBottomSheet by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var dragOffset by remember { mutableStateOf(0f) }
    val threshold = 100f

    if (showDeleteBottomSheet) {
        RemoveCartModal(
            item = item,
            onDismiss = {
                showDeleteBottomSheet = false
            },
        ) {
            showDeleteBottomSheet = false
            onDelete(item)
        }
    }

    Row(
        modifier =
            Modifier
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragEnd = {
                            expanded = dragOffset < -threshold
                            dragOffset = 0f
                        },
                        onDrag = { change, dragAmount ->
                            change.consume()
                            dragOffset += dragAmount.x

                            expanded = dragOffset < -threshold
                        },
                    )
                },
    ) {
        Box(modifier = Modifier.weight(1f).offset(x = if (expanded) -(50).dp else 0.dp)) {
            content(item)
        }
        IconButton(
            enabled = expanded,
            onClick = { showDeleteBottomSheet = true },
            modifier =
                Modifier
                    .height(70.dp)
                    .background(Color.Red)
                    .width(
                        animateDpAsState(
                            if (expanded) 60.dp else 5.dp,
                            tween(200),
                        ).value,
                    ),
        ) {
            if (expanded) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = Color.White,
                )
            }
        }
    }
}
