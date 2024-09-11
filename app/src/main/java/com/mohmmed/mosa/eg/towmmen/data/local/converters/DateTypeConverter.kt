package com.mohmmed.mosa.eg.towmmen.data.local.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.util.Date

@ProvidedTypeConverter
class DateTypeConverter {

    @TypeConverter
    fun fromDateToLong(date: Date?): Long{
        return date?.time ?: 0L
    }

    @TypeConverter
    fun fromLongToDate(long: Long): Date{
        return Date(long)
    }
}