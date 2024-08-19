package com.mohmmed.mosa.eg.towmmen.domin.usecases.customer

import com.mohmmed.mosa.eg.towmmen.data.module.Customer
import com.mohmmed.mosa.eg.towmmen.data.repository.CustomerRepositoryImp
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCustomerById @Inject constructor(
    private val customerRepository: CustomerRepositoryImp
) {

    operator fun invoke(id: Int): Flow<Customer> {
        return customerRepository.getCustomerById(id)
    }
}