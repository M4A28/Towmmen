package com.mohmmed.mosa.eg.towmmen.domin.usecases.customer

import com.mohmmed.mosa.eg.towmmen.data.repository.CustomerRepositoryImp
import javax.inject.Inject

class CleatCustomerData @Inject constructor(
    private val customerRepositoryImp: CustomerRepositoryImp
) {
    suspend operator fun invoke(){
        customerRepositoryImp.clearCustomersDate()
    }
}