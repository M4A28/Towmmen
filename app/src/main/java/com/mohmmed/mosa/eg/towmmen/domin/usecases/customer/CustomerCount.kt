package com.mohmmed.mosa.eg.towmmen.domin.usecases.customer

import com.mohmmed.mosa.eg.towmmen.data.repository.CustomerRepositoryImp
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CustomerCount @Inject constructor(
    private val customerRepository: CustomerRepositoryImp
) {

    operator fun invoke(): Flow<Int?> {
        return customerRepository.getCustomerCount()
    }
}