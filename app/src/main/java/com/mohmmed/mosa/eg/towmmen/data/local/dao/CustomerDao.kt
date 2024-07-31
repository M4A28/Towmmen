package com.mohmmed.mosa.eg.towmmen.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.mohmmed.mosa.eg.towmmen.data.module.Customer
import com.mohmmed.mosa.eg.towmmen.data.module.CustomerWithProducts
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDao{

    @Query("SELECT * FROM customers ORDER BY registrationDate DESC")
    fun getAllCustomers(): Flow<List<Customer>>

    @Query("SELECT * FROM customers WHERE customerId = :id ORDER BY registrationDate DESC")
    fun getCustomerById(id: Int): Flow<Customer>

    @Query("SELECT * FROM customers WHERE name = :name ORDER BY registrationDate DESC")
    fun getCustomerByName(name: String): Flow<List<Customer>>

    @Query("SELECT * FROM customers WHERE email = :email")
    fun getCustomerByEmail(email: String): Flow<Customer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomer(customer: Customer)

    @Update
    suspend fun updateCustomer(customer: Customer)

    @Delete
    suspend fun deleteCustomer(customer: Customer)

    @Transaction
    @Query("SELECT * FROM customers")
    fun getCustomersWithProducts(): Flow<List<CustomerWithProducts>>

    @Query("SELECT * FROM customers ORDER BY lastPurchaseDate DESC LIMIT :limit")
    fun getRecentCustomers(limit: Int): Flow<List<Customer>>

    @Query("SELECT Count(*) FROM customers")
    fun getCustomersCount(): Flow<Int?>


}
