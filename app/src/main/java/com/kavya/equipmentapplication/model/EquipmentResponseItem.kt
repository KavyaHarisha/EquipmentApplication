package com.kavya.equipmentapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "equipment")
data class EquipmentResponseItem(
    @PrimaryKey
    val id: Int,
    val length: Float,
    val make: String,
    val value: Float,
    val vin: String,
    val year: Int
)