package com.mohmmed.mosa.eg.towmmen.domin.usecases.customer

import com.mohmmed.mosa.eg.towmmen.data.repository.CustomerRepositoryImp
import com.mohmmed.mosa.eg.towmmen.domin.module.Customer
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCustomers @Inject constructor(
    private val customerRepositoryImp: CustomerRepositoryImp
) {
    operator fun invoke(): Flow<List<Customer>> {
       return customerRepositoryImp.getAllCustomers()
    }
}