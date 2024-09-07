package com.example.demoapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reports")
data class Report(
    @PrimaryKey(autoGenerate = false)
    var id: Long = 0,
    var report: String
)