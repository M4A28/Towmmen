package com.mohmmed.mosa.eg.towmmen.util

import android.content.Context
import android.os.Build
import com.mohmmed.mosa.eg.towmmen.R
import com.mohmmed.mosa.eg.towmmen.data.module.Product
import java.io.File
import java.io.FileWriter
import java.util.Date

fun exportExpProductsToTxt(products: List<Product>, context: Context): String {
    val createDate = Date()
    val stringBuilder = StringBuilder()
    val parentDir = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
        APP_DIR_R
    else APP_DIR
    val currentDayDir = File(parentDir.path + "/${dateToString(createDate,"yyyy-MM-dd")}")
    if(!parentDir.exists()){
        parentDir.mkdirs()
    }
    if(!currentDayDir.exists()){
        currentDayDir.mkdir()
    }

    val textFile = File(currentDayDir, "EXP_Product_${dateToString(createDate,"yyyy-MM-dd HH:mm")}.txt")
    val fileWriter = FileWriter(textFile)

    // Write data
    if(products.isNotEmpty()) {
        stringBuilder.append(context.getString(R.string.exp_product) + "\n")
        try{
            fileWriter.append(context.getString(R.string.exp_product) + "\n")
            products.forEachIndexed { index, product ->
                fileWriter.append(buildString {
                    append("${index + 1}- ${product.name} \n")
                    append(context.getString(R.string.exp_date_1, dateToString(product.expireDate, "yyyy-MM-dd")) )
                    append(context.getString(R.string.stock_quantity) + ": ${product.stockQuantity} ${product.unit}")
                    append("\n")
                })

            }
            fileWriter.flush()
            fileWriter.close()
        }catch(e: Exception){
            e.printStackTrace()
        }

        products.forEachIndexed { index, product ->
            stringBuilder.apply {
                append("${index + 1}- ${product.name} \n")
                append(context.getString(R.string.exp_date_1, dateToString(product.expireDate, "yyyy-MM-dd")) )
                append(context.getString(R.string.stock_quantity) + ": ${product.stockQuantity} ${product.unit}")
                append("\n")
            }
        }
    }

    return  stringBuilder.toString()
}

fun exportOutOfStockProductsToTxt(products: List<Product>,  context: Context) : String{
    val createDate = Date()
    val stringBuilder = StringBuilder()
    val parentDir = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
        APP_DIR_R
    else APP_DIR
    val currentDayDir = File(parentDir.path + "/${dateToString(createDate,"yyyy-MM-dd")}")
    if(!parentDir.exists()){
        parentDir.mkdirs()
    }
    if(!currentDayDir.exists()){
        currentDayDir.mkdir()
    }
    val textFile = File(currentDayDir, "OUT_OF_STOCK_Product_${dateToString(createDate,"yyyy-MM-dd HH:mm")}.txt")
    val fileWriter = FileWriter(textFile)

    // Write data
    if(products.isNotEmpty()) {
        stringBuilder.append(context.getString(R.string.out_of_stock_product) + "\n")
        // if crash heapn stil get data
        try{
            fileWriter.append(context.getString(R.string.out_of_stock_product) + "\n")
            products.forEachIndexed { index, product ->
                fileWriter.append(buildString {
                    append("${index + 1}- ${product.name} \n")
                    append(context.getString(R.string.cost, formatCurrency(product.cost)) +  "\n")
                    append(context.getString(R.string.price_,formatCurrency(product.price) ))
                    append("\n")
                })


            }
            fileWriter.flush()
            fileWriter.close()

        }catch(e:Exception){
            e.printStackTrace()
        }

        products.forEachIndexed { index, product ->
            stringBuilder.apply {
                append("${index + 1}- ${product.name} \n")
                append(context.getString(R.string.cost, formatCurrency(product.cost)) +  "\n")
                append(context.getString(R.string.price_,formatCurrency(product.price) ))
                append("\n")
            }
        }

    }

    return  stringBuilder.toString()
}


fun exportAllProducts(products: List<Product>, context: Context) : String{
    val createDate = Date()
    val stringBuilder = StringBuilder()
    val parentDir = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
        APP_DIR_R
    else APP_DIR
    val currentDayDir = File(parentDir.path + "/${dateToString(createDate,"yyyy-MM-dd")}")
    if(!parentDir.exists()){
        parentDir.mkdirs()
    }
    if(!currentDayDir.exists()){
        currentDayDir.mkdir()
    }
    val textFile = File(currentDayDir, "Shop_Products_${dateToString(createDate,"yyyy-MM-dd HH:mm")}.txt")
    val fileWriter = FileWriter(textFile)

    // Write data
    if(products.isNotEmpty()) {
        stringBuilder.append(context.getString(R.string.shop_products) + "\n")
        try{
            fileWriter.append(context.getString(R.string.shop_products) + "\n")
            products.forEachIndexed { index, product ->
                fileWriter.append(buildString {
                    append("${index + 1}- ${product.name} \n")
                    append(context.getString(R.string.cost, formatCurrency(product.cost)) +  "\n")
                    append(context.getString(R.string.price_,formatCurrency(product.price) + "\n"))
                    append(context.getString(R.string.stock_quantity) + ": ${product.stockQuantity} ${product.unit}")

                    append("\n")
                })
            }
            fileWriter.flush()
            fileWriter.close()

        }catch (e: Exception){
            e.printStackTrace()
        }
        products.forEachIndexed { index, product ->
            stringBuilder.apply {
                append("${index + 1}- ${product.name} \n")
                append(context.getString(R.string.cost, formatCurrency(product.cost)) +  "\n")
                append(context.getString(R.string.price_,formatCurrency(product.price) + "\n"))
                append(context.getString(R.string.stock_quantity) + ": ${product.stockQuantity} ${product.unit}")
                append("\n")
            }

        }

    }

    return  stringBuilder.toString()
}
