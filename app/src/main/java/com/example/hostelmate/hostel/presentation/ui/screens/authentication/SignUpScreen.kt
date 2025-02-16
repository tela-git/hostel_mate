package com.example.hostelmate.hostel.presentation.ui.screens.authentication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hostelmate.R
import com.example.hostelmate.hostel.presentation.ui.components.EmailInputField
import com.example.hostelmate.hostel.presentation.ui.components.NameInputField
import com.example.hostelmate.hostel.presentation.ui.components.PassWordInputField
import com.example.hostelmate.hostel.presentation.ui.components.buttons.LongButtonPrimary
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    onLoginInsteadClick: () -> Unit
) {
    val authViewModel : AuthViewModel = koinViewModel()
    val uiState by authViewModel.signUpUiState.collectAsState()
    var isSignUpButtonEnabled by remember { mutableStateOf(false) }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 20.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        Spacer(Modifier.height(36.dp))
        Text(
            text = stringResource(R.string.hostel_mate_headline),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(Modifier.height(56.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(36.dp),
            modifier = Modifier
                .padding(horizontal = 12.dp)
        ){
            NameInputField(
                name = uiState.name,
                onNameChange = { authViewModel.changeEnteredSignUpName(it) },
                modifier = Modifier
            )
            EmailInputField(
                emailAddress = uiState.email,
                onEmailChange = {
                    authViewModel.changeEnteredSignUpEmail(it)
                    isSignUpButtonEnabled = authViewModel.signUpUiState.value.password.isNotEmpty() &&
                            authViewModel.signUpUiState.value.email.isNotEmpty()
                },
                modifier = Modifier
            )
            PassWordInputField(
                password = uiState.password,
                isPasswordVisible = uiState.isPasswordVisible,
                onPasswordChange = {
                    authViewModel.changeEnteredSignUpPassword(it)
                    isSignUpButtonEnabled = authViewModel.signUpUiState.value.password.isNotEmpty() &&
                            authViewModel.signUpUiState.value.email.isNotEmpty()
                },
                onPasswordVisibilityChange = { authViewModel.changePasswordVisibility(it) }
            )
            Spacer(Modifier.height(20.dp))
            LongButtonPrimary(
                text = "Signup",
                onClick = {  },
                isEnabled = isSignUpButtonEnabled
            )
            Spacer(Modifier.height(32.dp))
            TextButton(
                onClick = {
                    onLoginInsteadClick()
                }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Already have an account ?",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            textDecoration = TextDecoration.Underline
                        )
                    )
                    Text(
                        text = "Login",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            textDecoration = TextDecoration.Underline
                        )
                    )
                }
            }
        }
    }
}
