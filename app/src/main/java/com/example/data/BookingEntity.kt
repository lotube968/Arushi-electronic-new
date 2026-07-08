package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookings")
data class BookingEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val serviceType: String,
    val customerName: String,
    val customerPhone: String,
    val description: String,
    val timestamp: Long = System.currentTimeMillis(),
    val status: String = "PENDING",
    val isSynced: Boolean = false
)
