package com.example.demoapp.ui.inventory.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.demoapp.data.model.InventoryItem
import com.example.demoapp.data.model.User
import com.example.demoapp.data.repository.InventoryRepository
import com.example.demoapp.data.repository.UserRepository
import com.example.demoapp.navigation.RouteArgs.NoQrCode
import com.example.demoapp.navigation.RouteArgs.productId
import com.example.demoapp.navigation.RouteArgs.productQrCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class InventoryProductDetailsViewModel @Inject constructor(
    val inventoryRepository: InventoryRepository
) : ViewModel() {

    suspend fun fetchDetails(qr: String?): InventoryItem? {
        if(!qr.isNullOrBlank() && qr != NoQrCode) {
            return inventoryRepository.fetchInventoryItemDetails(qr)
        }
        return null
    }

    fun saveItem(item: InventoryItem, onSuccess: () -> Unit) {
        viewModelScope.launch {
            inventoryRepository.saveInventoryItem(item)
            onSuccess()
        }
    }
}
