package com.example.demoapp.ui.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.demoapp.ui.auth.AuthViewModel

@Composable
fun WelcomeScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onLoggedIn: () -> Unit,
    onLoginPress: () -> Unit,
    onRegisterPress: () -> Unit
) {
    LaunchedEffect(key1 = true) {
        viewModel.isLoggedIn.collect {
            if (it) {
                onLoggedIn()
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = onLoginPress) {
            Text("Login")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRegisterPress) {
            Text("Register")
        }
    }
}