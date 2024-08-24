package com.mohmmed.mosa.eg.towmmen.util

import java.util.Date
import kotlin.random.Random


fun generateInvoiceNumber(currentDate: Date = Date()): String{
    val random = Random.nextInt(0, 2000)
    return "INV-${currentDate.time/10000}-$random"
}