package com.mohmmed.mosa.eg.towmmen.data.module

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.Date

@Entity(tableName = "debts")
data class Debt(
    @PrimaryKey(autoGenerate = false)
    val debtId: String,
    val debtAmount: Double,
    val customerId: Int,
    val customerName: String,
    val debtDate: Date,
    val isPayed: Boolean,
    val payDate: Date?
)

@Entity(tableName = "debts_items",
    foreignKeys = [ForeignKey(
        entity = Debt::class,
        parentColumns = ["debtId"],
        childColumns = ["debtId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("debtId")]
)
data class DebtItem(
    @PrimaryKey(autoGenerate = true)
    val debtItemId: Int,
    val debtId: String,
    val productId: Int,
    val productName: String,
    val unitPrice: Double,
    var quantity: Int,
    val date: Date
)


data class DebtWithItem(
    @Embedded val debt: Debt,
    @Relation(
        parentColumn = "debtId",
        entityColumn = "debtId"
    )
    val items: List<DebtItem>
)




