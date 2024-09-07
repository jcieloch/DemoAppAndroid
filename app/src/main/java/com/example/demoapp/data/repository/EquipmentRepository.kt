package com.example.demoapp.data.repository

import com.example.demoapp.data.local.dao.EquipmentDao
import com.example.demoapp.data.local.dao.EquipmentDetails
import com.example.demoapp.data.local.dao.UserDao
import com.example.demoapp.data.model.Equipment
import com.example.demoapp.data.model.EquipmentAssignment
import com.example.demoapp.data.model.IEquipmentAssignment
import com.example.demoapp.data.model.LoginUser
import com.example.demoapp.data.model.RegisterUser
import com.example.demoapp.data.model.User
import com.example.demoapp.data.service.RemoteAuthService
import com.example.demoapp.data.service.RemoteEquipmentService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EquipmentRepository @Inject constructor(
    private val equipmentDao: EquipmentDao,
    private val apiService: RemoteEquipmentService
) {

    fun observeEquipments(): Flow<List<Equipment>> {
        return equipmentDao.observeEquipments()
    }

    suspend fun fetchEquipments(): List<Equipment> {
        try { // TODO: obsługa błędów
            val list = apiService.list().body() ?: emptyList()
            return list
        } catch (e: Exception) {
            return emptyList()
        }
    }

    suspend fun saveEquipments(equipments: List<Equipment>) {
        return equipmentDao.updateEquipments(equipments)
    }


    fun observeEquipmentDetails(id: Long): Flow<EquipmentDetails> {
        return equipmentDao.observeEquipmentDetails(id)
    }

    suspend fun fetchEquipmentDetails(id: Long): EquipmentDetails? {
        try {
            val details = apiService.details(id).body()
            return details
        } catch (e: Exception) {
            return null
        }
    }

    suspend fun saveEquipmentDetails(equipmentDetails: EquipmentDetails?) {
        if (equipmentDetails?.assignment != null) {
            equipmentDao.updateEquipmentDetails(equipmentDetails?.assignment)
        }
    }
}
