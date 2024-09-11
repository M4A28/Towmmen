package com.mohmmed.mosa.eg.towmmen.util

import android.content.Context
import android.os.Environment
import android.widget.Toast
import com.mohmmed.mosa.eg.towmmen.data.module.Product
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.text.SimpleDateFormat
import java.util.Date

const val productId = 0
const val name = 1
const val barcode = 2
const val cost = 3
const val price = 4
const val description = 5
const val imagePath = 6
const val category = 7
const val stockQuantity = 8
const val unit = 9
const val manufactureDate = 10
const val expireDate = 11

fun readCsvProductData(context: Context, csvPath: String): List<Product> {
    val csvProductsContent = mutableListOf<Product>()
    val smf = SimpleDateFormat("yyyy-MM-dd")
    try {
        val backupFile = File(Environment.getExternalStorageDirectory().path , csvPath.split(":")[1])

        BufferedReader(FileReader(backupFile)).use { reader ->
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                if(line?.contains("[0-9]".toRegex()) == true){
                    val row = line?.split(",")?.map { it.trim() } ?: emptyList()
                    csvProductsContent.add(Product(
                        name = row[name],
                        barcode = row[barcode],
                        cost = row[cost].toDoubleOrNull()?: 0.0,
                        price = row[price].toDoubleOrNull()?: 0.0,
                        description = row[description],
                        imagePath = row[imagePath],
                        category = row[category],
                        stockQuantity = row[stockQuantity].toIntOrNull() ?: 0,
                        unit = row[unit],
                        manufactureDate = smf.parse(row[manufactureDate])?: Date(),
                        expireDate = smf.parse(row[expireDate])?: Date(),
                        createdAt = Date(),
                        updatedAt = Date()
                    ))
                }

            }
            return csvProductsContent
        }



    } catch (e: Exception) {
        e.printStackTrace()
        Toast.makeText(context, "Error reading CSV: ${e.message}", Toast.LENGTH_LONG).show()
        return csvProductsContent
    }
}