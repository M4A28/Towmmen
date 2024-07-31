package com.mohmmed.mosa.eg.towmmen.data.module

import androidx.room.Embedded
import androidx.room.Relation

data class PurchaseWithDetails(
    @Embedded val purchase: Purchase,
    @Relation(
        parentColumn = "customerId",
        entityColumn = "customerId"
    )
    val customer: Customer,
    @Relation(
        parentColumn = "productId",
        entityColumn = "productId"
    )
    val product: Product
)