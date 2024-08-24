package com.mohmmed.mosa.eg.towmmen.data.module

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "locker")
data class Locker(
    @PrimaryKey(autoGenerate = true)
    val transActonId: Int,
    val transActionType: String,
    val transActionDate: Date,
    val transActionAmount: Double
)


enum class TransActionType{
    ADD,
    SUB,
    SELL,
    RETURN,
    BUY,
    EXPANSE
}