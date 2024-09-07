package com.example.demoapp.ui.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.demoapp.ui.auth.AuthViewModel
import com.example.demoapp.ui.components.TopAppBar

@Composable
fun DashboardScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    dashboardViewModel: DashboardViewModel = hiltViewModel(),
    navigateToProductsList: () -> Unit,
    navigateToInventory: () -> Unit,
    navigateToInventorySummary: () -> Unit,
    openDrawer: () -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    val user by viewModel.user.observeAsState()
    val inventoryItems = dashboardViewModel.inventoryItems.observeAsState()

    val unfinishedInventory = inventoryItems.value != null && inventoryItems.value!!.isNotEmpty()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                openDrawer = openDrawer,
                text = "Inventory app"
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
            Text("Hello, you are logged in!")
            Spacer(modifier = Modifier.height(16.dp))
            Box(contentAlignment = Alignment.Center) {
                Text(text = "Hello, ${user?.username}!")
            }
            Button(onClick = navigateToProductsList) {
                Text(text = "Show products")
            }
            Button(onClick = navigateToInventory) {
                Text(text = if (unfinishedInventory) "Continue inventory" else "Start inventory")
            }
            if (unfinishedInventory) {
                Button(onClick = navigateToInventorySummary) {
                    Text(text = "Show unfinished inventory summary")
                }
            }
        }
    }
}