package com.mohmmed.mosa.eg.towmmen.domin.repostory

import com.mohmmed.mosa.eg.towmmen.domin.module.Customer
import kotlinx.coroutines.flow.Flow

interface CustomerRepository {

    fun getAllCustomers(): Flow<List<Customer>>

    suspend fun addNewCustomer(customer: Customer)

    suspend fun deleteCustomer(customer: Customer)

    suspend fun updateCustomer(customer: Customer)

    fun getCustomer(name: String): Flow<List<Customer>>

    fun getCustomerCount(): Flow<Int?>

}