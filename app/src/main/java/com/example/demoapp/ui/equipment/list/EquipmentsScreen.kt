package com.example.demoapp.ui.equipment.list


import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.demoapp.data.model.Equipment
import com.example.demoapp.ui.auth.AuthViewModel
import com.example.demoapp.ui.components.TopAppBar
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.demoapp.data.model.EquipmentStatus
import com.example.demoapp.data.model.IEquipment
import com.example.demoapp.ui.components.BackFilterTopAppBar
import com.example.demoapp.ui.components.BackTopAppBar
import com.example.demoapp.ui.components.FilterAction
import com.example.demoapp.utils.mapEquipmentStatusToDisplayName

@Composable
fun EquipmentsScreen(
    viewModel: EquipmentsViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onEquipmentClick: (id: Long) -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
) {
    val equipments by viewModel.equipments.observeAsState()
    var filter: EquipmentStatus? by remember { mutableStateOf(null) }

    Log.i("EQUIPMENTS: ", equipments.toString())
    Log.i("LISTA ROZMIAR", equipments?.size.toString())

    LaunchedEffect(key1 = true) {
        viewModel.fetchData()
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            BackFilterTopAppBar(
                onBack = onBack,
                text = "Equipment list",
                filterActions =
                    EquipmentStatus.values().map {
                        val action = {
                            filter = it
                        }
                        return@map FilterAction(mapEquipmentStatusToDisplayName(it), action)
                    }.plus(FilterAction(text = "ALL", action = { filter = null }))
            )
        }
    ) { paddingValues ->
        paddingValues
        EquipmentListView(equipments?.filter { if(filter != null) it.status == filter else true } ?: emptyList(), onEquipmentClick)
    }
}

@Composable
fun EquipmentListView(equipmentList: List<Equipment>, onEquipmentClick: (id: Long) -> Unit) {
    LazyColumn {
        items(equipmentList) { equipment ->
            EquipmentItemView(equipment, onEquipmentClick)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EquipmentItemView(equipment: IEquipment, onEquipmentClick: (id: Long) -> Unit) {
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = { onEquipmentClick(equipment.id) }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = equipment.name, style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "QR Code: ${equipment.qrCode}", style = MaterialTheme.typography.body2)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Description: ${equipment.description}", style = MaterialTheme.typography.body2)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Status: ${mapEquipmentStatusToDisplayName(equipment.status)}", style = MaterialTheme.typography.body2)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Producer: ${equipment.producer}", style = MaterialTheme.typography.body2)
        }
    }
}