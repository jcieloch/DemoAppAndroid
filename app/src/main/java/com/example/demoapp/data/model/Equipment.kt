package com.example.demoapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class EquipmentStatus {
    IN_USE, LOST, NOT_IN_USE
}

interface IEquipment {
    val id: Long
    val name: String
    val qrCode: String
    val description: String?
    val status: EquipmentStatus?
    val producer: String?
}

@Entity(tableName = "equipments")
data class Equipment(
    @PrimaryKey(autoGenerate = true) override val id: Long = 0,
    override val name: String,
    override val qrCode: String,
    override val description: String,
    override val status: EquipmentStatus,
    override val producer: String
) : IEquipment