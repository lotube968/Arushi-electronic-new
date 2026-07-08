package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "warranty_items")
data class WarrantyItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val itemName: String,
    val brand: String,
    val serialNumber: String,
    val purchaseDate: Long,
    val warrantyDurationMonths: Int,
    val isSynced: Boolean = false
)
