package com.example.hostelmate.hostel.presentation.ui.screens.authentication

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AuthViewModel: ViewModel() {

    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState = _loginUiState.asStateFlow()

    private val _signUpUiState = MutableStateFlow(SignUpUiState())
    val signUpUiState = _signUpUiState.asStateFlow()

    private val _authState = MutableStateFlow(AuthState(AppAuthState.UnAuthenticated))
    val authState = _authState.asStateFlow()

    fun changeEnteredLoginEmail(email: String) {
        _loginUiState.update { it.copy(email = email) }
    }
    fun changeEnteredLoginPassword(password: String) {
        _loginUiState.update { it.copy(password = password) }
    }
    fun changeEnteredSignUpEmail(email: String) {
        _signUpUiState.update { it.copy(email = email) }
    }
    fun changeEnteredSignUpPassword(password: String) {
        _signUpUiState.update { it.copy(password = password) }
    }
    fun changeEnteredSignUpName(name: String) {
        _signUpUiState.update { it.copy(name = name) }
    }
    fun changePasswordVisibility(isVisible: Boolean) {
        _loginUiState.update { it.copy(isPasswordVisible = isVisible) }
        _signUpUiState.update { it.copy(isPasswordVisible = isVisible) }
    }
    fun clearLoginCred() {
        _loginUiState.update { LoginUiState() }
    }
    fun clearSignUpCred() {
        _signUpUiState.update { SignUpUiState() }
    }
}


data class AuthState(
    val authState: AppAuthState,
)
sealed class AppAuthState {
    data object Authenticated : AppAuthState()
    data object UnAuthenticated : AppAuthState()
}

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false
)

data class SignUpUiState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val name: String = ""
)