package com.mohmmed.mosa.eg.towmmen.domin.module

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val productId: Int  = 0,
    val name: String,
    val price: Double,
    val description: String,
    val category: String,
    val stockQuantity: Int,
    val manufactureDate: Date,
    val expireDate: Date,
    val createdAt: Date,
    val updatedAt: Date = Date()
)
