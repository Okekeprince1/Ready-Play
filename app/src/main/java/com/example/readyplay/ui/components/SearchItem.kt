package com.example.readyplay.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.readyplay.R
import com.example.readyplay.ui.theme.lightBlack
import com.example.readyplay.ui.theme.textFieldColor

@Composable
fun SearchItem(
    onScanClick: () -> Unit
) {
    val textInputFieldColors =
        TextFieldDefaults.colors(
            focusedTextColor = lightBlack.copy(alpha = 0.5f),
            unfocusedTextColor = lightBlack.copy(alpha = 0.5f),
            focusedContainerColor = textFieldColor,
            unfocusedContainerColor = textFieldColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        )

    TextField(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        value = "",
        placeholder = {
            Text(
                text = stringResource(R.string.search),
                style =
                    MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Normal,
                    ),
            )
        },
        onValueChange = { chg -> println(chg) },
        singleLine = true,
        leadingIcon = {
            IconButton(onClick = { }) {
                Icon(
                    modifier = Modifier.size(19.dp),
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                )
            }
        },
        trailingIcon = {
            IconButton(onClick = { onScanClick() }) {
                Image(
                    painter =
                        painterResource(
                            id = R.drawable.ic_scan_blue,
                        ),
                    contentDescription = null,
                )
            }
        },
        shape = MaterialTheme.shapes.small,
        colors = textInputFieldColors,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
    )
}
