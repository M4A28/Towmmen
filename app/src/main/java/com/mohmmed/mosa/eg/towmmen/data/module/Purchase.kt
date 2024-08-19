package com.mohmmed.mosa.eg.towmmen.data.module

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.Date

@Entity(tableName = "purchases")
data class Purchase(
    @PrimaryKey
    val purchaseId: String ,
    val dealerId: Int,
    val dealerName: String,
    val totalCost: Double,
    val date: Date
)


@Entity(tableName = "purchase_items",
    foreignKeys = [ForeignKey(
        entity = Purchase::class,
        parentColumns = ["purchaseId"],
        childColumns = ["purchaseId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class PurchaseItem(
    @PrimaryKey(autoGenerate = true)
    val itemId: Int = 0,
    val purchaseId: String,
    val productId: Int,
    val productName: String,
    var quantity: Int,
    val unitPrice: Double,
    val purchaseDate: Date
)


data class PurchaseWithItems(
    @Embedded val purchase: Purchase,
    @Relation(
        parentColumn = "purchaseId",
        entityColumn = "purchaseId"
    )
    val items: List<PurchaseItem>
)
