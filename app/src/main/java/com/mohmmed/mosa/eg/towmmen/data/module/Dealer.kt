package com.mohmmed.mosa.eg.towmmen.data.module

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
@Entity(tableName = "dealers")
data class Dealer(
    @PrimaryKey(autoGenerate = true)
    val dealerId: Int,
    val dealerName: String,
    val createDate: Date,
    val dealerPhoneNumber: String,
    val dealerAddress: String,
    val dealerNote: String
): Parcelable