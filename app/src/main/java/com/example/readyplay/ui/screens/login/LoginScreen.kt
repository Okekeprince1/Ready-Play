package com.example.readyplay.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.readyplay.R
import com.example.readyplay.ui.components.ErrorText
import com.example.readyplay.ui.components.Spacing
import com.example.readyplay.ui.theme.ReadyPlayTheme
import com.example.readyplay.ui.theme.lightBlack
import com.example.readyplay.ui.theme.lightGrey
import com.example.readyplay.ui.theme.textFieldColor
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    viewModel: LoginScreenViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
    onLogin: () -> Unit,
) {
    var passwordVisible by remember { mutableStateOf(false) }

    val textInputFieldColors = TextFieldDefaults.colors(
        focusedTextColor = lightBlack.copy(alpha = 0.5f),
        unfocusedTextColor = lightBlack.copy(alpha = 0.5f),
        focusedContainerColor = textFieldColor,
        unfocusedContainerColor = textFieldColor,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
    )

    val shadowErrorModifier = Modifier.shadow(
        elevation = 5.dp,
        shape = MaterialTheme.shapes.small,
        ambientColor = Color.Red,
        spotColor = Color.Red,
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp),
    ) {
        Spacing(height = 25.dp)
        Text(
            text = stringResource(R.string.log_in),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
        )
        Spacing(height = 10.dp)
        Text(
            text = stringResource(R.string.login_now_subtitle),
            style = MaterialTheme.typography.bodyLarge.copy(color = lightGrey),
        )
        Spacing(height = 30.dp)

        Text(
            text = stringResource(R.string.login_email_address),
            style = MaterialTheme.typography.labelSmall.copy(color = lightBlack),
        )
        Spacing(height = 10.dp)
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .then(
                    if (!viewModel.emailError.isNullOrBlank()) {
                        shadowErrorModifier
                    } else {
                        Modifier
                    }
                ),
            value = viewModel.email,
            onValueChange = { viewModel.email = it },
            singleLine = true,
            placeholder = {
                Text(stringResource(R.string.name_example_com))
            },
            trailingIcon = {
                if (!viewModel.emailError.isNullOrBlank()) {
                    Image(
                        painter = painterResource(id = R.drawable.error_warning),
                        contentDescription = null,
                    )
                }
            },
            shape = MaterialTheme.shapes.small,
            colors = textInputFieldColors,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        )
        if (!viewModel.emailError.isNullOrBlank()) {
            Spacing(height = 5.dp)
            ErrorText(text = viewModel.emailError ?: "")

        }
        Spacing(height = 25.dp)
        Text(
            text = stringResource(R.string.login_password),
            style = MaterialTheme.typography.labelSmall.copy(color = lightBlack),
        )
        Spacing(height = 10.dp)
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .then(
                    if (!viewModel.passwordError.isNullOrBlank()) {
                        shadowErrorModifier
                    } else {
                        Modifier
                    }
                ),
            value = viewModel.password,
            placeholder = {
                Text(stringResource(R.string.enter_your_password))
            },
            onValueChange = { viewModel.password = it },
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Image(
                        painter = painterResource(
                            id = if (passwordVisible) {
                                R.drawable.ic_eye_icon_open
                            } else {
                                R.drawable.ic_eye_close
                            },
                        ),
                        contentDescription = null,
                    )
                }
            },
            shape = MaterialTheme.shapes.small,
            colors = textInputFieldColors,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        )
        if (!viewModel.passwordError.isNullOrBlank()) {
            Spacing(height = 5.dp)
            ErrorText(text = viewModel.passwordError ?: "")
        }
        Spacing(height = 10.dp)

        Text(
            modifier = Modifier.fillMaxWidth().clickable {},
            text = stringResource(R.string.forgot_password),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.02.sp,
            ),
            textAlign = TextAlign.End,
        )
        Spacing(height = 32.dp)

        Button(
            onClick = { viewModel.onLoginAttempt(onLogin) },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = MaterialTheme.shapes.small,
        ) {
            Text(text = stringResource(id = R.string.log_in))
        }

        Spacing(height = 20.dp)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(R.string.dont_have_a_readyplay_account),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Medium,
                    color = lightGrey,
                    letterSpacing = .02.sp,
                ),
            )
            Spacing(width = 2.dp)
            Text(
                text = stringResource(R.string.sign_up),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary,
                ),
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    ReadyPlayTheme {
        LoginScreen(
            onLogin = {},
        )
    }
}
