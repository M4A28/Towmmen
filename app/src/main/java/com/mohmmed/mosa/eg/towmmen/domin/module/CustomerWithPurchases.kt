package com.mohmmed.mosa.eg.towmmen.domin.module

import androidx.room.Embedded
import androidx.room.Relation

data class CustomerWithPurchases(
    @Embedded val customer: Customer,
    @Relation(
        parentColumn = "customerId",
        entityColumn = "customerId"
    )
    val purchases: List<Purchase>
)