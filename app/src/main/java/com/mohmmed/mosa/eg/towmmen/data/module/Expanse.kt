package com.mohmmed.mosa.eg.towmmen.data.module

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
@Entity(tableName = "expanse")
class Expanse(
    @PrimaryKey(autoGenerate = true)
    val expanseId: Int,
    var expanse: String,
    var payDate: Date,
    var amount: Double
): Parcelable