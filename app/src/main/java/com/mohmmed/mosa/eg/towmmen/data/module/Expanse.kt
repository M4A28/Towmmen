package com.mohmmed.mosa.eg.towmmen.data.module

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "expanse")
class Expanse(
    @PrimaryKey(autoGenerate = true)
    val expanseId: Int,
    var expanse: String,
    var payDate: Date,
    var amount: Double
)