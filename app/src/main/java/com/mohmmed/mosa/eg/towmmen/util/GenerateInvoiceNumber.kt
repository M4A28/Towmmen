package com.mohmmed.mosa.eg.towmmen.util

import java.text.SimpleDateFormat
import java.util.Date
import kotlin.random.Random


fun generateInvoiceNumber(currentDate: Date = Date()): String{
    val random = Random.nextInt()
    val date = SimpleDateFormat("yyyyMMddss").format(currentDate)
    return String.format("INV-%s-%d", date, random )
}