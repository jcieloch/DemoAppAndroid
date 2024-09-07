package com.example.demoapp.ui.inventory.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.demoapp.data.model.IInventoryItem
import com.example.demoapp.data.model.InventoryItem
import com.example.demoapp.data.model.Report
import com.example.demoapp.data.repository.InventoryRepository
import com.example.demoapp.navigation.RouteArgs.NoQrCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InventoryReportViewModel @Inject constructor(
    val inventoryRepository: InventoryRepository
) : ViewModel() {

    private val _report: LiveData<Report> = inventoryRepository.observeReport().asLiveData()
    val report: LiveData<Report> = _report
}