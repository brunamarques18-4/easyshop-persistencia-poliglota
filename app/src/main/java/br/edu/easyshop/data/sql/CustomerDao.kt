package br.edu.easyshop.data.sql

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(customer: CustomerEntity)

    @Query("SELECT * FROM customers ORDER BY fullName COLLATE NOCASE")
    fun observeAll(): Flow<List<CustomerEntity>>
}
