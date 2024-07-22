package com.mohmmed.mosa.eg.towmmen.di

import com.mohmmed.mosa.eg.towmmen.data.local.dao.CustomerDao
import com.mohmmed.mosa.eg.towmmen.data.local.db.TowmmenDatabase
import com.mohmmed.mosa.eg.towmmen.data.repository.CustomerRepositoryImp
import com.mohmmed.mosa.eg.towmmen.domin.usecases.customer.AddNewCustomer
import com.mohmmed.mosa.eg.towmmen.domin.usecases.customer.CustomerCount
import com.mohmmed.mosa.eg.towmmen.domin.usecases.customer.CustomerUseCases
import com.mohmmed.mosa.eg.towmmen.domin.usecases.customer.DeleteCustomer
import com.mohmmed.mosa.eg.towmmen.domin.usecases.customer.GetAllCustomers
import com.mohmmed.mosa.eg.towmmen.domin.usecases.customer.GetCustomer
import com.mohmmed.mosa.eg.towmmen.domin.usecases.customer.UpdateCustomer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CustomerModule {
    @Provides
    @Singleton
    fun provideCustomerDao(towmmenDatabase: TowmmenDatabase): CustomerDao {
        return towmmenDatabase.customerDao
    }

    @Provides
    @Singleton
    fun provideCustomerRepository(customerDao: CustomerDao): CustomerRepositoryImp {
        return CustomerRepositoryImp(customerDao)
    }

    @Provides
    @Singleton
    fun provideCustomerUseCases(customerRepository: CustomerRepositoryImp): CustomerUseCases {
        return CustomerUseCases(
            addNewCustomer = AddNewCustomer(customerRepository),
            updateCustomer = UpdateCustomer(customerRepository),
            getAllCustomers = GetAllCustomers(customerRepository),
            getCustomer = GetCustomer(customerRepository),
            deleteCustomer = DeleteCustomer(customerRepository),
            getCustomerCount = CustomerCount(customerRepository)
        )
    }
}