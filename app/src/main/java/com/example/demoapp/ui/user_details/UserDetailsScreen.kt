package com.example.demoapp.ui.user_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.demoapp.ui.components.BackTopAppBar

@Composable
fun UserDetailsScreen(
    onBack: () -> Unit,
    viewModel: UserDetailsViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    val user by viewModel.user.observeAsState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            BackTopAppBar(
                onBack = onBack,
                text = "User details"
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(16.dp))
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "User ID: ${user?.id}")
                Text(text = "Username: ${user?.username}")
                Text(text = "Email: ${user?.email}")
                if(user?.phoneNumber?.isNotEmpty() == true) Text(text = "Phone number: ${user?.phoneNumber}")
                Text(text = "Role: ${user?.role}")
            }
        }
    }

}