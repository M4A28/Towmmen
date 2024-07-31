package com.mohmmed.mosa.eg.towmmen.data.repository

import com.mohmmed.mosa.eg.towmmen.data.local.dao.CustomerProductDao
import com.mohmmed.mosa.eg.towmmen.data.module.Customer
import com.mohmmed.mosa.eg.towmmen.data.module.CustomerProductCrossRef
import com.mohmmed.mosa.eg.towmmen.data.module.CustomerWithProducts
import com.mohmmed.mosa.eg.towmmen.data.module.Product
import com.mohmmed.mosa.eg.towmmen.data.module.ProductWithCustomers
import com.mohmmed.mosa.eg.towmmen.domin.repostory.CustomerProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CustomerProductRepositoryImp @Inject constructor(
    private val customerProductDao: CustomerProductDao
) : CustomerProductRepository {

    override fun getCustomerWithProducts(customerId: Int): Flow<List<CustomerWithProducts>> {
        return customerProductDao.getCustomerWithProducts(customerId)
    }

    override fun getProductWithCustomers(productId: Int): Flow<List<ProductWithCustomers>> {
        return customerProductDao.getProductWithCustomers(productId)
    }

    override suspend fun insertNewCustomer(customer: Customer) {
        TODO("Not yet implemented")
    }

    override suspend fun insertNewProduct(product: Product) {
        TODO("Not yet implemented")
    }

    override suspend fun insertCustomerProductCrossRef(crossRef: CustomerProductCrossRef) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCustomerProductCrossRef(crossRef: CustomerProductCrossRef) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCustomer(customer: Customer) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteProduct(product: Product) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteProductById(productId: Int) {
        TODO("Not yet implemented")
    }
}