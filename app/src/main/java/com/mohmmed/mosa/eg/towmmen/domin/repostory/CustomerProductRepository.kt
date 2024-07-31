package com.mohmmed.mosa.eg.towmmen.domin.repostory

import com.mohmmed.mosa.eg.towmmen.data.module.Customer
import com.mohmmed.mosa.eg.towmmen.data.module.CustomerProductCrossRef
import com.mohmmed.mosa.eg.towmmen.data.module.CustomerWithProducts
import com.mohmmed.mosa.eg.towmmen.data.module.Product
import com.mohmmed.mosa.eg.towmmen.data.module.ProductWithCustomers
import kotlinx.coroutines.flow.Flow

interface CustomerProductRepository {

    fun getCustomerWithProducts(customerId: Int): Flow<List<CustomerWithProducts>>

    fun getProductWithCustomers(productId: Int): Flow<List<ProductWithCustomers>>

    suspend fun insertNewCustomer(customer: Customer)

    suspend fun insertNewProduct(product: Product)

    suspend fun insertCustomerProductCrossRef(crossRef: CustomerProductCrossRef)

    suspend fun deleteCustomerProductCrossRef(crossRef: CustomerProductCrossRef)

    suspend fun deleteCustomer(customer: Customer)

    suspend fun deleteProduct(product: Product)

    suspend fun deleteProductById(productId: Int)
}