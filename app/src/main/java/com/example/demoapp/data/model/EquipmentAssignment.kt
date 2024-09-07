package com.example.demoapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

interface IEquipmentAssignment {
    val equipmentId: Long
    val username: String
    val email: String
    val phoneNumber: String
    val role: String
    val localization: String
    val assignmentDate: String
    val id: Long
}

@Entity(tableName = "equipment_assignments")
data class EquipmentAssignment(
    @PrimaryKey(autoGenerate = true) override val id: Long = 0,
    override val equipmentId: Long,
    override val username: String,
    override val email: String,
    override val phoneNumber: String,
    override val role: String,
    override val localization: String,
    override val assignmentDate: String
) : IEquipmentAssignment