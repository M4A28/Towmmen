package com.mohmmed.mosa.eg.towmmen.domin.usecases.customer

import com.mohmmed.mosa.eg.towmmen.data.repository.CustomerRepositoryImp
import com.mohmmed.mosa.eg.towmmen.data.module.Customer
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCustomer @Inject constructor(
    private val customerRepositoryImp: CustomerRepositoryImp
) {
    operator fun invoke(customerName: String): Flow<List<Customer>> {
        return customerRepositoryImp.getCustomer(customerName)
    }
}