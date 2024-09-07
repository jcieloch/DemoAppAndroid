package com.example.demoapp.ui.inventory.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoapp.data.model.EquipmentStatus
import com.example.demoapp.data.model.InventoryItem
import com.example.demoapp.navigation.RouteArgs.NoQrCode
import com.example.demoapp.ui.components.BackFilterTopAppBar
import com.example.demoapp.ui.components.BackTopAppBar
import com.example.demoapp.ui.components.FilterAction
import com.example.demoapp.utils.mapEquipmentStatusToDisplayName

@Composable
fun InventoryProductDetailsScreen(
    onBack: () -> Unit,
    onFinish: () -> Unit,
    onAddNext: () -> Unit,
    qr: String?,
    viewModel: InventoryProductDetailsViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    var username by remember { mutableStateOf("") }
    var qrCode by remember { mutableStateOf(if(qr == NoQrCode) "" else qr ?: "") }
    var localization by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(key1 = qrCode) {
        val item = viewModel.fetchDetails(qrCode)
        username = item?.username ?: ""
        localization = item?.localization ?: ""
        status = item?.equipmentStatus ?: ""
        email = item?.email ?: ""
        description = item?.description ?: ""
        phoneNumber = item?.phoneNumber ?: ""
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            BackTopAppBar(
                onBack = onBack,
                text = "Inventory item",
            )
        }
    ) { paddingValues ->
        paddingValues
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            TextField(value = qrCode, onValueChange = { qrCode = it }, label = { Text("Qr Code") })
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = username, onValueChange = { username = it }, label = { Text("username") })
            Spacer(modifier = Modifier.height(16.dp))
            TextField(value = localization, onValueChange = { localization = it }, label = { Text("localization") })
            Spacer(modifier = Modifier.height(16.dp))
            TextField(value = description, onValueChange = { description = it }, label = { Text("description") })
            Spacer(modifier = Modifier.height(16.dp))
            TextField(value = status, onValueChange = { status = it }, label = { Text("status") })
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Contact to user:")
            Text(text = "Email:")
            Text(text = email)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Phone number:")
            Text(text = phoneNumber)
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                viewModel.saveItem(
                    InventoryItem(
                        qr = qrCode,
                        email = email,
                        localization = localization,
                        username = username,
                        phoneNumber = phoneNumber,
                        equipmentStatus = status,
                        description = description,
                        id = 0
                    )
                ) {
                    onFinish()
                }
            }) {
                Text(text = "Save and show summary")
            }
            Button(onClick = {
                viewModel.saveItem(
                    InventoryItem(
                        qr = qrCode,
                        email = email,
                        localization = localization,
                        username = username,
                        phoneNumber = phoneNumber,
                        equipmentStatus = status,
                        description = description,
                        id = 0
                    )
                ) {
                    onAddNext()
                }
            }) {
                Text(text = "Save and add next item")
            }
        }
    }

}