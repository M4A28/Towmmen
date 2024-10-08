package com.mohmmed.mosa.eg.towmmen.data.repository

import com.mohmmed.mosa.eg.towmmen.data.local.dao.CustomerDao
import com.mohmmed.mosa.eg.towmmen.data.module.Customer
import com.mohmmed.mosa.eg.towmmen.domin.repostory.CustomerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CustomerRepositoryImp @Inject constructor(
    private val customerDao: CustomerDao
) : CustomerRepository {
    override fun getAllCustomers(): Flow<List<Customer>> {
        return customerDao.getAllCustomers()
    }

    override suspend fun addNewCustomer(customer: Customer) {
        customerDao.insertCustomer(customer)
    }

    override suspend fun deleteCustomer(customer: Customer) {
        customerDao.deleteCustomer(customer)
    }

    override suspend fun updateCustomer(customer: Customer) {
        customerDao.updateCustomer(customer)
    }

    override fun getCustomer(name: String): Flow<List<Customer>> {
        return customerDao.getCustomerByName(name)
    }



    override fun getCustomerCount(): Flow<Int?> {
        return customerDao.getCustomersCount()
    }

    override fun getCustomerById(id: Int): Flow<Customer> {
        return customerDao.getCustomerById(id)
    }

    override suspend fun clearCustomersDate() {
        customerDao.clearCustomersDate()
    }
}