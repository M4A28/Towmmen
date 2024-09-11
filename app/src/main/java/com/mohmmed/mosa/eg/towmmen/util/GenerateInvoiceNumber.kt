package com.mohmmed.mosa.eg.towmmen.util

import java.util.Date
import kotlin.random.Random

// todo enhace this make it good
fun generateInvoiceNumber(currentDate: Date = Date()): String{
    val random = Random.nextInt(0, 4000)
    return "I-${currentDate.time/100000}-$random"
}
fun generatePurchaseNumber(currentDate: Date = Date()): String{
    val random = Random.nextInt(0, 4000)
    return "P-${currentDate.time/100000}-$random"
}

fun generateDebtNumber(currentDate: Date = Date()): String{
    val random = Random.nextInt(0, 4000)
    return "D-${currentDate.time/100000}-$random"
}