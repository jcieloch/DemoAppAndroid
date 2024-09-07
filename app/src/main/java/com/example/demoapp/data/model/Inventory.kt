package com.example.demoapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

interface IInventoryItem {
    var id: Long
    var equipmentStatus: String?
    var qr: String
    val username: String?
    val email: String?
    val phoneNumber: String?
    var description: String?
    var localization: String?
}


@Entity(tableName = "inventory_items")
data class InventoryItem(
    @PrimaryKey(autoGenerate = true)
    override var id: Long = 0,
    override var equipmentStatus: String?,
    override var qr: String,
    override val username: String?,
    override val email: String?,
    override val phoneNumber: String?,
    override var description: String?,
    override var localization: String?
) : IInventoryItem