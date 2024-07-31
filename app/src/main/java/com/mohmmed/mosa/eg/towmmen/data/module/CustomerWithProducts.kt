package com.mohmmed.mosa.eg.towmmen.data.module


import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class CustomerWithProducts(
    @Embedded val customer: Customer,
    @Relation(
        parentColumn = "customerId",
        entityColumn = "productId",
        associateBy = Junction(CustomerProductCrossRef::class)
    )
    val products: List<Product>
)


