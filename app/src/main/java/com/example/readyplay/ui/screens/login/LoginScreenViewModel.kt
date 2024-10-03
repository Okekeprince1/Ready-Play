package com.example.readyplay.ui.screens.login

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readyplay.datastore.UserDataStore
import kotlinx.coroutines.launch

class LoginScreenViewModel(
    private val userDataStore: UserDataStore,
) : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")

    var emailError by mutableStateOf<String?>(null)
    var passwordError by mutableStateOf<String?>(null)

    private fun validateLogin(): Boolean {
        var isValid = true

        if (email.isBlank()) {
            emailError = "Email cannot be empty"
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailError = "Invalid email format"
            isValid = false
        } else {
            emailError = null
        }

        if (password.isBlank()) {
            passwordError = "Password cannot be empty"
            isValid = false
        } else {
            passwordError = null
        }

        return isValid
    }

    fun onLoginAttempt(onLogin: () -> Unit) {
        if (validateLogin()) {
            viewModelScope.launch {
                userDataStore.saveLoginState(true)
                onLogin()
            }
        }
    }
}
