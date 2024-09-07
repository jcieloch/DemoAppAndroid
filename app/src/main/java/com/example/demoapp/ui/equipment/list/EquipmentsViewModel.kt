package com.example.demoapp.ui.equipment.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.demoapp.data.model.Equipment
import com.example.demoapp.data.model.EquipmentStatus
import com.example.demoapp.data.model.User
import com.example.demoapp.data.repository.EquipmentRepository
import com.example.demoapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EquipmentsViewModel @Inject constructor(
    private val equipmentRepository: EquipmentRepository
) : ViewModel() {

    private val _equipments: LiveData<List<Equipment>> = equipmentRepository.observeEquipments().asLiveData()
    val equipments: LiveData<List<Equipment>> = _equipments

//    private val _currentFilter: LiveData<EquipmentStatus> =
//    val equipments: LiveData<List<Equipment>> = _equipments

    suspend fun fetchData() {
        equipmentRepository.saveEquipments(equipmentRepository.fetchEquipments())
    }

}
