package com.mohmmed.mosa.eg.towmmen.data.module

import androidx.room.Entity
import androidx.room.ForeignKey
import java.util.Date

/*@Entity(
    primaryKeys = ["customerId", "productId"],
    foreignKeys = [
        ForeignKey(
            entity = Customer::class,
            parentColumns = ["id"],
            childColumns = ["customerId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Product::class,
            parentColumns = ["id"],
            childColumns = ["productId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)*/
@Entity(primaryKeys = ["customerId", "productId"])
data class CustomerProductCrossRef(
    val customerId: Int,
    val productId: Int,
    val purchaseDate: Date,
    val quantity: Int
)
