package com.example.demoapp.ui.inventory.report

import com.example.demoapp.ui.inventory.summary.InventorySummaryViewModel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
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
fun InventoryReportScreen(
    viewModel: InventoryReportViewModel = hiltViewModel(),
    onBack: () -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
) {
    val report by viewModel.report.observeAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            BackTopAppBar(
                onBack = onBack,
                text = "Report"
            )
        }
    ) { paddingValues ->
        paddingValues
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(scrollState),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = report?.report ?: "No data")
            Button(onClick = onBack) {
                Text(text = "Go to dashboard")
            }
        }
    }
}