package com.example.demoapp.data.repository

import com.example.demoapp.data.local.dao.InventoryDao
import com.example.demoapp.data.model.IInventoryItem
import com.example.demoapp.data.model.InventoryItem
import com.example.demoapp.data.model.Report
import com.example.demoapp.data.service.RemoteInventoryService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InventoryRepository @Inject constructor(
    private val inventoryDao: InventoryDao,
    private val apiService: RemoteInventoryService
) {

    suspend fun fetchInventoryItemDetails(qr: String): InventoryItem? {
        try { // TODO: obsługa błędów
            val details = apiService.itemDetails(qr).body()
            return details
        } catch (e: Exception) {
            return null
        }
    }

    suspend fun saveInventoryItem(inventoryItem: InventoryItem) {
        inventoryDao.saveInventoryItem(inventoryItem)
    }

    fun observeItems(): Flow<List<IInventoryItem>> {
        return inventoryDao.observeItems()
    }

    fun observeItem(id: Long): Flow<InventoryItem> {
        return inventoryDao.observeItem(id)
    }

    fun observeReport(): Flow<Report> {
        return inventoryDao.observeReport()
    }

    fun getItems(): List<InventoryItem> {
        return inventoryDao.getItems()
    }

    suspend fun clearItems() {
        return inventoryDao.deleteAll()
    }

    suspend fun sendInventory(items: List<InventoryItem>): String {
        return try {
            apiService.sendInventory(items).body() ?: ""
        } catch (e: Exception) {
            "Error" + e.message
        }
    }

    suspend fun saveReport(report: Report) {
        inventoryDao.saveReport(report)
    }
}