package com.mohmmed.mosa.eg.towmmen.util

import android.os.Environment
import com.mohmmed.mosa.eg.towmmen.data.module.Category
import com.mohmmed.mosa.eg.towmmen.data.module.Customer
import com.mohmmed.mosa.eg.towmmen.data.module.Product
import java.io.File
import java.io.FileWriter

fun exportProductsToCsv(products: List<Product>) {

    val csvFile = File(Environment.getExternalStorageDirectory(), "products_data.csv")
    val fileWriter = FileWriter(csvFile)

    // Write CSV header
    fileWriter.append("productId," +
            "name," +
            "barcode," +
            "cost," +
            "price," +
            "description," +
            "imagePath," +
            "category," +
            "stockQuantity," +
            "unit," +
            "manufactureDate," +
            "expireDate\n")

    // Write data
    for (product in products) {
        fileWriter.append(buildString {
            append("${product.productId},")
            append("${product.name},")
            append(product.barcode)
            append(product.cost)
            append(product.price)
            append(product.description)
            append(product.imagePath)
            append(product.category)
            append(product.stockQuantity)
            append(product.unit)
            append(product.manufactureDate)
            append(product.expireDate)
            append("\n")
        })
    }

    fileWriter.flush()
    fileWriter.close()
}
fun exportCustomersToCsv(customers: List<Customer>) {

    val csvFile = File(Environment.getExternalStorageDirectory(), "customers_data.csv")
    val fileWriter = FileWriter(csvFile)

    // Write CSV header
    fileWriter.append(
        "customerId," +
            "name," +
            "email," +
            "phone," +
            "address," +
            "registrationDate," +
            "lastPurchaseDate\n")

    // Write data
    for (customer in customers) {
        fileWriter.append(buildString {
            append("${customer.customerId},")
            append("${customer.name},")
            append(customer.email)
            append(customer.phone)
            append(customer.address)
            append(customer.registrationDate.toString())
            append(customer.lastPurchaseDate.toString())
            append("\n")
        })
    }

    fileWriter.flush()
    fileWriter.close()
}
fun exportCategoryToCsv(categorys: List<Category>) {

    val csvFile = File(Environment.getExternalStorageDirectory(), "categorys_data.csv")
    val fileWriter = FileWriter(csvFile)

    // Write CSV header
    fileWriter.append(
        "Id," + "Category\n")

    // Write data
    for (category in categorys) {
        fileWriter.append(buildString {
            append("${category.id},")
            append("${category.category},")
            append("\n")
        })
    }

    fileWriter.flush()
    fileWriter.close()
}






