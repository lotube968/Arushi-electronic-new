package com.example.data

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow

class RepairRepository(private val repairDao: RepairDao) {
    val allBookings: Flow<List<BookingEntity>> = repairDao.getAllBookings()
    val allWarrantyItems: Flow<List<WarrantyItemEntity>> = repairDao.getAllWarrantyItems()

    suspend fun insertBooking(booking: BookingEntity): Long {
        val localId = repairDao.insertBooking(booking)
        val idToUse = if (booking.id == 0) localId.toInt() else booking.id
        val updatedBooking = booking.copy(id = idToUse)
        
        try {
            val firestore = FirebaseFirestore.getInstance()
            val db = FirebaseDatabase.getInstance()
            
            val firebaseData = hashMapOf(
                "id" to idToUse,
                "serviceType" to updatedBooking.serviceType,
                "customerName" to updatedBooking.customerName,
                "customerPhone" to updatedBooking.customerPhone,
                "description" to updatedBooking.description,
                "timestamp" to updatedBooking.timestamp,
                "status" to updatedBooking.status
            )
            
            // Sync with Firestore
            firestore.collection("bookings")
                .document(idToUse.toString())
                .set(firebaseData)
            
            // Sync with Realtime Database
            db.getReference("bookings")
                .child(idToUse.toString())
                .setValue(firebaseData)

            // Mark as synced
            repairDao.insertBooking(updatedBooking.copy(isSynced = true))
        } catch (e: Exception) {
            Log.w("RepairRepository", "Firebase Firestore/Database sync offline: ${e.message}")
        }
        return localId
    }

    suspend fun deleteBooking(id: Int) {
        repairDao.deleteBookingById(id)
        try {
            FirebaseFirestore.getInstance().collection("bookings").document(id.toString()).delete()
            FirebaseDatabase.getInstance().getReference("bookings").child(id.toString()).removeValue()
        } catch (e: Exception) {
            Log.w("RepairRepository", "Firebase delete skipped: ${e.message}")
        }
    }

    suspend fun insertWarrantyItem(item: WarrantyItemEntity): Long {
        val localId = repairDao.insertWarrantyItem(item)
        val idToUse = if (item.id == 0) localId.toInt() else item.id
        val updatedItem = item.copy(id = idToUse)
        
        try {
            val firestore = FirebaseFirestore.getInstance()
            val db = FirebaseDatabase.getInstance()
            
            val firebaseData = hashMapOf(
                "id" to idToUse,
                "itemName" to updatedItem.itemName,
                "brand" to updatedItem.brand,
                "serialNumber" to updatedItem.serialNumber,
                "purchaseDate" to updatedItem.purchaseDate,
                "warrantyDurationMonths" to updatedItem.warrantyDurationMonths
            )
            
            // Sync with Firestore
            firestore.collection("warranty_items")
                .document(idToUse.toString())
                .set(firebaseData)

            // Sync with Realtime Database
            db.getReference("warranty_items")
                .child(idToUse.toString())
                .setValue(firebaseData)

            // Mark as synced
            repairDao.insertWarrantyItem(updatedItem.copy(isSynced = true))
        } catch (e: Exception) {
            Log.w("RepairRepository", "Firebase warranty sync offline: ${e.message}")
        }
        return localId
    }

    suspend fun deleteWarrantyItem(id: Int) {
        repairDao.deleteWarrantyItemById(id)
        try {
            FirebaseFirestore.getInstance().collection("warranty_items").document(id.toString()).delete()
            FirebaseDatabase.getInstance().getReference("warranty_items").child(id.toString()).removeValue()
        } catch (e: Exception) {
            Log.w("RepairRepository", "Firebase delete skipped: ${e.message}")
        }
    }
}
