package com.mohmmed.mosa.eg.towmmen.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.mohmmed.mosa.eg.towmmen.data.module.Customer
import com.mohmmed.mosa.eg.towmmen.data.module.CustomerProductCrossRef
import com.mohmmed.mosa.eg.towmmen.data.module.CustomerWithProducts
import com.mohmmed.mosa.eg.towmmen.data.module.Product
import com.mohmmed.mosa.eg.towmmen.data.module.ProductWithCustomers
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerProductDao {
    @Insert
    suspend fun insertCustomer(customer: Customer)

    @Delete
    suspend fun deleteCustomer(customer: Customer)

    @Insert
    suspend fun insertProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Insert
    suspend fun insertCustomerProductCrossRef(crossRef: CustomerProductCrossRef)

    @Delete
    suspend fun deleteCustomerProductCrossRef(crossRef: CustomerProductCrossRef)

    @Transaction
    @Query("SELECT * FROM customers WHERE customerId = :customerId")
    fun getCustomerWithProducts(customerId: Int): Flow<List<CustomerWithProducts>>

    @Transaction
    @Query("SELECT * FROM products WHERE productId = :productId")
    fun getProductWithCustomers(productId: Int): Flow<List<ProductWithCustomers>>

    @Query("DELETE FROM products WHERE productId = :productId")
    suspend fun deleteProductById(productId: Int)
}
