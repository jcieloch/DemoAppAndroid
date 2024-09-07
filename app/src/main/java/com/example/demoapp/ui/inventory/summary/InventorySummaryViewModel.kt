package com.example.demoapp.ui.inventory.summary

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.demoapp.data.model.Equipment
import com.example.demoapp.data.model.IInventoryItem
import com.example.demoapp.data.model.InventoryItem
import com.example.demoapp.data.model.Report
import com.example.demoapp.data.repository.EquipmentRepository
import com.example.demoapp.data.repository.InventoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class InventorySummaryViewModel @Inject constructor(
    private val inventoryRepository: InventoryRepository
) : ViewModel() {

    private val _items: LiveData<List<IInventoryItem>> = inventoryRepository.observeItems().asLiveData()
    val items: LiveData<List<IInventoryItem>> = _items

    fun clearList() {
        viewModelScope.launch {
            inventoryRepository.clearItems()
        }
    }

    fun sendInventory(onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val items = inventoryRepository.getItems()
            val report = inventoryRepository.sendInventory(items)
            inventoryRepository.saveReport(Report(id = 0, report = report))
            withContext(Dispatchers.Main) {
                onSuccess()
            }
        }
    }

}
