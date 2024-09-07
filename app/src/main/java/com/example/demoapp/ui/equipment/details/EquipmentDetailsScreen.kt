package com.example.demoapp.ui.equipment.details


import android.util.Log
import android.widget.Button
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.demoapp.data.model.Equipment
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EquipmentDetailsScreen(
    viewModel: EquipmentDetailsViewModel = hiltViewModel(),
    onBack: () -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
) {
    val details by viewModel.equipmentDetails.observeAsState()

    LaunchedEffect(key1 = true) {
        viewModel.fetchData()
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            BackTopAppBar(
                onBack = onBack,
                text = "Details"
            )
        }
    ) { paddingValues ->
        paddingValues

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Informacje o sprzęcie
                Text(text = details?.equipment?.name ?: "", style = MaterialTheme.typography.h5)
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "QR Code: ${details?.equipment?.qrCode}", style = MaterialTheme.typography.body2)
                Spacer(modifier = Modifier.height(4.dp))

                Text(text = "Status: ${details?.equipment?.status}", style = MaterialTheme.typography.body2)
                Spacer(modifier = Modifier.height(4.dp))

                Text(text = "Producer: ${details?.equipment?.description ?: "Unknown"}", style = MaterialTheme.typography.body2)
                Spacer(modifier = Modifier.height(8.dp))

                // Informacje o użytkowniku
                Text(text = "User: ${details?.assignment?.username ?: "No user"}", style = MaterialTheme.typography.body2)
                Spacer(modifier = Modifier.height(4.dp))

                Text(text = "Email: ${details?.assignment?.email ?: "No user"}", style = MaterialTheme.typography.body2)
                Spacer(modifier = Modifier.height(4.dp))

                Text(text = "Phone: ${details?.assignment?.phoneNumber ?: "No user"}", style = MaterialTheme.typography.body2)
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "User role: ${details?.assignment?.role ?: "No user"}", style = MaterialTheme.typography.body2)
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Assignment date: ${details?.assignment?.assignmentDate ?: "No user"}", style = MaterialTheme.typography.body2)
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Localization: ${details?.assignment?.localization ?: "No user"}", style = MaterialTheme.typography.body2)
                Spacer(modifier = Modifier.height(8.dp))

                // Dodatkowe przyciski lub interakcje, jeśli potrzebne
//                Button(onClick = onBackClick) {
//                    Text(text = "Back")
//                }
            }

//        EquipmentListView(equipments?.filter { if(filter != null) it.status == filter else true } ?: emptyList())
    }
}
