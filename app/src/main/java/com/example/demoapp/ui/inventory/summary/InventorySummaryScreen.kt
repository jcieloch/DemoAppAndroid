package com.example.demoapp.ui.inventory.summary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import com.example.demoapp.data.model.EquipmentStatus
import com.example.demoapp.data.model.IInventoryItem
import com.example.demoapp.ui.components.BackFilterTopAppBar
import com.example.demoapp.ui.components.BackTopAppBar
import com.example.demoapp.ui.components.FilterAction
import com.example.demoapp.utils.mapEquipmentStatusToDisplayName

@Composable
fun InventorySummaryScreen(
    viewModel: InventorySummaryViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onItemClick: (id: Long) -> Unit,
    onReport: () -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
) {
    val items by viewModel.items.observeAsState()
    var filter: EquipmentStatus? by remember { mutableStateOf(null) }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            BackTopAppBar(
                onBack = onBack,
                text = "Summary"
            )
        },
        bottomBar = {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    Button(onClick = {
                        viewModel.sendInventory {
                            onReport()
                            viewModel.clearList()
                        }
                    }) {
                        Text(text = "Send inventory and show report")
                    }
                    Button(onClick = {
                        viewModel.clearList()
                    }) {
                        Text(text = "Clear list")
                    }
                }
            }

        }
    ) { paddingValues ->
        paddingValues
        ItemListView(items ?: emptyList(), onItemClick)
    }
}

@Composable
fun ItemListView(items: List<IInventoryItem>, onItemClick: (id: Long) -> Unit) {
    LazyColumn {
        items(items) { item ->
            EquipmentItemView(item, onItemClick)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EquipmentItemView(item: IInventoryItem, onItemClick: (id: Long) -> Unit) {
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = { onItemClick(item.id) }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "QR Code: ${item.qr}", style = MaterialTheme.typography.body2)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Description: ${item.description}", style = MaterialTheme.typography.body2)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Status: ${item.equipmentStatus}", style = MaterialTheme.typography.body2)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Localization: ${item.localization}", style = MaterialTheme.typography.body2)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "username: ${item.username}", style = MaterialTheme.typography.body2)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "email: ${item.email}", style = MaterialTheme.typography.body2)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "phone number: ${item.phoneNumber}", style = MaterialTheme.typography.body2)
        }
    }
}