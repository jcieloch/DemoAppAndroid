package com.example.demoapp.ui.inventory.edit

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
import com.example.demoapp.data.model.InventoryItem
import com.example.demoapp.ui.components.BackTopAppBar

@Composable
fun InventoryProductEditDetailsScreen(
    onBack: () -> Unit,
    onSave: () -> Unit,
    viewModel: InventoryProductEditDetailsViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    val item by viewModel.item.observeAsState()

    var username by remember { mutableStateOf(item?.username) }
    var qrCode by remember { mutableStateOf(item?.qr) }
    var localization by remember { mutableStateOf(item?.localization) }
    var status by remember { mutableStateOf(item?.equipmentStatus) }
    var description by remember { mutableStateOf(item?.description) }

    LaunchedEffect(key1 = item) {
        item?.let {
            username = it.username
            qrCode = it.qr
            localization = it.localization
            status = it.equipmentStatus
            description = it.description
        }
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
            TextField(value = qrCode ?: "", onValueChange = { qrCode = it }, label = { Text("Qr Code") })
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = username ?: "", onValueChange = { username = it }, label = { Text("username") })
            Spacer(modifier = Modifier.height(16.dp))
            TextField(value = localization ?: "", onValueChange = { localization = it }, label = { Text("localization") })
            Spacer(modifier = Modifier.height(16.dp))
            TextField(value = description ?: "", onValueChange = { description = it }, label = { Text("description") })
            Spacer(modifier = Modifier.height(16.dp))
            TextField(value = status ?: "", onValueChange = { status = it }, label = { Text("status") })
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                val itemToSave = item?.copy(
                    qr = qrCode ?: "",
                    localization = localization,
                    username = username,
                    equipmentStatus = status,
                    description = description
                )
                if(itemToSave != null) {
                    viewModel.saveItem(itemToSave) {
                        onSave()
                    }
                }
            }) {
                Text(text = "Save")
            }
        }
    }

}