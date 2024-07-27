package com.mohmmed.mosa.eg.towmmen.domin.module

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date


@Parcelize
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
): Parcelable