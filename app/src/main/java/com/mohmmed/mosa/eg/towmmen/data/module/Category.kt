package com.mohmmed.mosa.eg.towmmen.data.module

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("category")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val category: String
)