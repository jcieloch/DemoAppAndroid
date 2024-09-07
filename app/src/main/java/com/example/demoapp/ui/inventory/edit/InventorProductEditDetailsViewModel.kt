package com.example.demoapp.ui.inventory.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.demoapp.data.model.InventoryItem
import com.example.demoapp.data.repository.InventoryRepository
import com.example.demoapp.navigation.RouteArgs.productId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class InventoryProductEditDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    val inventoryRepository: InventoryRepository
) : ViewModel() {

    val id: Long = savedStateHandle[productId] ?: 0

    private val _item: LiveData<InventoryItem> = inventoryRepository.observeItem(id).asLiveData()
    val item: LiveData<InventoryItem> = _item

    fun saveItem(item: InventoryItem, onSuccess: () -> Unit) {
        viewModelScope.launch {
            inventoryRepository.saveInventoryItem(item)
            onSuccess()
        }
    }

    fun editItem(item: InventoryItem, onSuccess: () -> Unit) {
        viewModelScope.launch {
            inventoryRepository.saveInventoryItem(item)
            onSuccess()
        }
    }
}
