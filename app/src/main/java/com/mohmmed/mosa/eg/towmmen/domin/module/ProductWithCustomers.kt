package com.mohmmed.mosa.eg.towmmen.domin.module

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class ProductWithCustomers(
    @Embedded val product: Product,
    @Relation(
        parentColumn = "productId",
        entityColumn = "customerId",
        associateBy = Junction(CustomerProductCrossRef::class)
    )
    val customers: List<Customer>
)