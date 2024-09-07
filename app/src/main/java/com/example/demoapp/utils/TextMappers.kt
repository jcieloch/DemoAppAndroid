package com.example.demoapp.utils

import com.example.demoapp.data.model.EquipmentStatus
import com.example.demoapp.data.model.UserRole

fun mapRoleToDisplayName(role: UserRole): String {
    return when (role) {
        UserRole.CATALOGUER -> "Cataloguer"
    }
}

fun mapEquipmentStatusToDisplayName(status: EquipmentStatus?): String {
    return when (status) {
        EquipmentStatus.LOST -> "Lost"
        EquipmentStatus.IN_USE -> "in usage"
        EquipmentStatus.NOT_IN_USE -> "not in usage"
        else -> "No data"
    }
}
