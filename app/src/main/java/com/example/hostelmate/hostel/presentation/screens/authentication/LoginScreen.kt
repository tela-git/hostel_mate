package com.example.hostelmate.hostel.presentation.screens.authentication

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.navigation.NavController
import com.example.hostelmate.R
import com.example.hostelmate.core.presentation.components.LongButton
import com.example.hostelmate.hostel.presentation.components.EmailInputField
import com.example.hostelmate.hostel.presentation.components.PassWordInputField
import com.example.hostelmate.hostel.presentation.screens.Screens

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    navController: NavController,
) {
    val uiState by authViewModel.loginUiState.collectAsState()
    var isLoginEnabled by remember { mutableStateOf(false) }

    BackHandler {
        // Do nothing
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.hostel_mate_headline),
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(vertical = 20.dp)
        )
        LoginBody(
            uiState = uiState,
            onPasswordChange = {
                authViewModel.changeEnteredLoginPassword(it)
                isLoginEnabled = authViewModel.loginUiState.value.password.isNotEmpty() &&
                        authViewModel.loginUiState.value.email.isNotEmpty()
            },
            onEmailChange = {
                authViewModel.changeEnteredLoginEmail(it)
                isLoginEnabled = authViewModel.loginUiState.value.email.isNotEmpty() &&
                        authViewModel.loginUiState.value.password.isNotEmpty()
            },
            onPasswordVisibilityChange = { authViewModel.changePasswordVisibility(it) },
            isLoginEnabled = isLoginEnabled,
            onLoginButtonClick = { },
            onSignUpInsteadClick = {
                navController.navigate(Screens.Signup.route) {
                    popUpTo(Screens.Signup.route) { inclusive = false }
                }
                authViewModel.clearLoginCred()
            },
            onSkipLoginClick = {
                navController.navigate(Screens.Home.route)
            }
        )
    }
}

@Composable
fun LoginBody(
    modifier: Modifier = Modifier,
    uiState: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordVisibilityChange: (Boolean) -> Unit,
    isLoginEnabled: Boolean,
    onSignUpInsteadClick: () -> Unit,
    onLoginButtonClick: () ->  Unit,
    onSkipLoginClick: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        Column(
            modifier = modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EmailInputField(
                emailAddress = uiState.email,
                onEmailChange = onEmailChange
            )
        }
        Column(
            modifier = modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PassWordInputField(
                password = uiState.password,
                onPasswordChange =  onPasswordChange,
                isPasswordVisible = uiState.isPasswordVisible,
                onPasswordVisibilityChange = onPasswordVisibilityChange
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = modifier
                    .fillMaxWidth()
            ){
                TextButton(
                    onClick = { }
                ) {
                    Text(
                        text = "Forgot password?",
                        style = MaterialTheme.typography.labelLarge,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ){
        LongButton(
            title = "LOGIN",
            onClick = {
                onLoginButtonClick()
            },
            modifier = modifier
                .fillMaxWidth(0.9f),
            isEnabled = isLoginEnabled
        )
//        TextButton(
//            onClick = {
//                onSignUpInsteadClick()
//            }
//        ) {
//            Row()
//            {
//                Text(
//                    text = "Don't have an account ?",
//                    color = MaterialTheme.colorScheme.onPrimaryContainer,
//                    textDecoration = TextDecoration.Underline
//                )
//                Text(
//                    text = " Sign Up ",
//                    style = MaterialTheme.typography.labelLarge,
//                    color = MaterialTheme.colorScheme.onPrimaryContainer,
//                    textDecoration = TextDecoration.Underline
//                )
//            }
//        }
        TextButton(
            onClick = {
                onSkipLoginClick()
            }
        ) {
            Text(
                text = "Skip for now",
                fontSize = 16.sp
            )
        }
        Spacer(Modifier.height(40.dp))
    }
}
