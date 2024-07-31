package com.mohmmed.mosa.eg.towmmen.domin.usecases.customer

import com.mohmmed.mosa.eg.towmmen.data.repository.CustomerRepositoryImp
import com.mohmmed.mosa.eg.towmmen.data.module.Customer
import javax.inject.Inject

class AddNewCustomer @Inject constructor(
    private val customerRepositoryImp: CustomerRepositoryImp
) {
    suspend operator fun invoke(customer: Customer){
        customerRepositoryImp.addNewCustomer(customer)
    }
}