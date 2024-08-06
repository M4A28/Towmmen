package com.mohmmed.mosa.eg.towmmen.data.module

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val note: String,
    val date: Date = Date(),
    val importance: Int = 1, // 1 high, 2 mid, 3 low
    val lastModified: Date = Date()
): Parcelable
