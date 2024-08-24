package com.mohmmed.mosa.eg.towmmen.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun dateToString(date: Date, pattern: String, locale: Locale = Locale.US): String{
    return SimpleDateFormat(pattern, locale).format(date)
}



