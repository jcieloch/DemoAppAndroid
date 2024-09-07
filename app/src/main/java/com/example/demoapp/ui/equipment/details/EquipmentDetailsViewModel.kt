package com.example.demoapp.ui.equipment.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.demoapp.data.local.dao.EquipmentDetails
import com.example.demoapp.data.model.Equipment
import com.example.demoapp.data.repository.EquipmentRepository
import com.example.demoapp.navigation.RouteArgs
import com.example.demoapp.navigation.RouteArgs.productId
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class EquipmentDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val equipmentRepository: EquipmentRepository,
) : ViewModel() {

    val id: Long = savedStateHandle[productId] ?: 0

    private val _equipmentDetails: LiveData<EquipmentDetails> = equipmentRepository.observeEquipmentDetails(id).asLiveData()
    val equipmentDetails: LiveData<EquipmentDetails> = _equipmentDetails

    suspend fun fetchData() {
        equipmentRepository.saveEquipmentDetails(
            equipmentRepository.fetchEquipmentDetails(id)
        )
    }

}
