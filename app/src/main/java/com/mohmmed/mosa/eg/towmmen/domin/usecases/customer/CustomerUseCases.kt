package com.mohmmed.mosa.eg.towmmen.domin.usecases.customer

data class CustomerUseCases(
    val addNewCustomer: AddNewCustomer,
    val updateCustomer: UpdateCustomer,
    val getAllCustomers: GetAllCustomers,
    val getCustomer: GetCustomer,
    val deleteCustomer: DeleteCustomer,
    val getCustomerCount: CustomerCount,
    val getCustomerById: GetCustomerById
)