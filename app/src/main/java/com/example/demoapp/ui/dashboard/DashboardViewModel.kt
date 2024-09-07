package com.example.demoapp.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.demoapp.data.model.IInventoryItem
import com.example.demoapp.data.repository.InventoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val inventoryRepository: InventoryRepository
) : ViewModel() {

    private val _items: LiveData<List<IInventoryItem>> = inventoryRepository.observeItems().asLiveData()
    val inventoryItems: LiveData<List<IInventoryItem>> = _items

}
