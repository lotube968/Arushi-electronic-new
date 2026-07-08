package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RepairDao {
    @Query("SELECT * FROM bookings ORDER BY timestamp DESC")
    fun getAllBookings(): Flow<List<BookingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooking(booking: BookingEntity): Long

    @Query("DELETE FROM bookings WHERE id = :id")
    suspend fun deleteBookingById(id: Int)

    @Query("SELECT * FROM warranty_items ORDER BY purchaseDate DESC")
    fun getAllWarrantyItems(): Flow<List<WarrantyItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWarrantyItem(item: WarrantyItemEntity): Long

    @Query("DELETE FROM warranty_items WHERE id = :id")
    suspend fun deleteWarrantyItemById(id: Int)
}
