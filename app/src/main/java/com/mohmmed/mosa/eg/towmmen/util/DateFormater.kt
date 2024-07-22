package com.mohmmed.mosa.eg.towmmen.util

import java.text.SimpleDateFormat
import java.util.Date

fun dateToString(date: Date, pattern: String): String{
    return SimpleDateFormat(pattern).format(date)
}



