package com.example.demoapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.demoapp.data.model.Equipment
import com.example.demoapp.data.model.EquipmentAssignment
import com.example.demoapp.data.model.IInventoryItem
import com.example.demoapp.data.model.InventoryItem
import com.example.demoapp.data.model.Report
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveInventoryItem(inventoryItem: InventoryItem)

    @Query("SELECT * FROM inventory_items")
    fun observeItems(): Flow<List<InventoryItem>>

    @Query("SELECT * FROM inventory_items AS i where i.id = :id")
    fun observeItem(id: Long): Flow<InventoryItem>

    @Query("SELECT * FROM inventory_items")
    fun getItems(): List<InventoryItem>

    @Query("DELETE FROM inventory_items")
    suspend fun deleteAll()

    @Query("SELECT * FROM reports LIMIT 1")
    fun observeReport(): Flow<Report>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveReport(report: Report)
}