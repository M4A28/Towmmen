package com.mohmmed.mosa.eg.towmmen.data.module

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val productId: Int  = 0,
    val name: String,
    val barcode: String,
    val price: Double,
    val description: String,
    val imagePath: String = "",
    val category: String,
    val stockQuantity: Int,
    val unit: String = "Unit",
    val manufactureDate: Date,
    val expireDate: Date,
    val createdAt: Date,
    val updatedAt: Date = Date()
): Parcelable
