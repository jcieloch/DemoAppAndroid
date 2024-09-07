package com.example.demoapp.ui.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.demoapp.data.model.RegisterUser
import com.example.demoapp.data.model.UserRole

@Composable
fun RegisterScreen(
    onRegister: (registerUser: RegisterUser) -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber: String? by remember { mutableStateOf(null) }
    var statusMessage by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.fillMaxSize(),  verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(value = username, onValueChange = { username = it }, label = { Text("Username") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = password, onValueChange = { password = it }, label = { Text("Password") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = phoneNumber ?: "", onValueChange = { phoneNumber = it }, label = { Text("Phone number") })
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if(listOf(username, password, email).all { it.isNotEmpty() }) {
                onRegister(RegisterUser(username, password, email, phoneNumber, UserRole.CATALOGUER))
            } else {
                statusMessage = "Error - all fields are mandatory."
            }

        }) {
            Text("Register")
        }
        statusMessage?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(it)
        }
    }
}