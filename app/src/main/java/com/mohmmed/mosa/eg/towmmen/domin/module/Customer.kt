package com.mohmmed.mosa.eg.towmmen.domin.module

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "customers")
data class Customer(
    @PrimaryKey(autoGenerate = true)
    val customerId: Int = 0,
    val name: String,
    val email: String,
    val phone: String,
    val address: String,
    val registrationDate: Date,
    val lastPurchaseDate: Date?
)