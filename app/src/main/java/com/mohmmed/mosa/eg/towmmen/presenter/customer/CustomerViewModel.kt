package com.mohmmed.mosa.eg.towmmen.presenter.customer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohmmed.mosa.eg.towmmen.data.module.Customer
import com.mohmmed.mosa.eg.towmmen.domin.usecases.customer.CustomerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerViewModel @Inject constructor(
    private val customerUseCases: CustomerUseCases
): ViewModel() {


    fun addNewCustomer(customer: Customer){
        viewModelScope.launch {
            customerUseCases.addNewCustomer(customer)
        }
    }

    fun getAllCustomer(): Flow<List<Customer>> {
        return customerUseCases.getAllCustomers()
    }


    fun getCustomerCount(): Flow<Int?>{
        return customerUseCases.getCustomerCount()
    }

    fun deleteCustomer(customer: Customer){
        viewModelScope.launch {
            customerUseCases.deleteCustomer(customer)
        }
    }


    fun updateCustomer(customer: Customer){
        viewModelScope.launch{
            customerUseCases.updateCustomer(customer)
        }
    }

}