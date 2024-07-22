package com.mohmmed.mosa.eg.towmmen.domin.module

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val note: String,
    val date: Date = Date(),
    val importance: Int = 1, // 1 high, 2 mid, 3 low
    val lastModified: Date = Date()
)
