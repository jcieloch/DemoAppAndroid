package com.example.demoapp.data.local.dao

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import com.example.demoapp.data.model.Equipment
import com.example.demoapp.data.model.EquipmentAssignment
import com.example.demoapp.data.model.IEquipmentAssignment
import kotlinx.coroutines.flow.Flow

@Dao
interface EquipmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateEquipments(equipments: List<Equipment>)

    @Query("SELECT * FROM equipments")
    fun observeEquipments(): Flow<List<Equipment>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateEquipmentDetails(equipmentDetails: EquipmentAssignment?)

    @Transaction
    @Query("SELECT * FROM equipments as ea where ea.id = :equipmentId")
    fun observeEquipmentDetails(equipmentId: Long): Flow<EquipmentDetails>

}

data class EquipmentDetails(
    @Embedded val equipment: Equipment?,
    @Relation(
        parentColumn = "id",
        entityColumn = "equipmentId"
    )
    val assignment: EquipmentAssignment?
)
