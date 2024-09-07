package com.example.demoapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.demoapp.data.local.dao.EquipmentDao
import com.example.demoapp.data.local.dao.InventoryDao
import com.example.demoapp.data.local.dao.UserDao
import com.example.demoapp.data.model.Equipment
import com.example.demoapp.data.model.EquipmentAssignment
import com.example.demoapp.data.model.InventoryItem
import com.example.demoapp.data.model.Report
import com.example.demoapp.data.model.User

@Database(entities = [User::class, Equipment::class, EquipmentAssignment::class, InventoryItem::class, Report::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun equipmentDao(): EquipmentDao
    abstract fun inventoryDao(): InventoryDao
}
