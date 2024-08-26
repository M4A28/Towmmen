package com.mohmmed.mosa.eg.towmmen.util

import android.os.Environment
import com.mohmmed.mosa.eg.towmmen.data.module.Category
import com.mohmmed.mosa.eg.towmmen.data.module.Customer
import com.mohmmed.mosa.eg.towmmen.data.module.Dealer
import com.mohmmed.mosa.eg.towmmen.data.module.Expanse
import com.mohmmed.mosa.eg.towmmen.data.module.Invoice
import com.mohmmed.mosa.eg.towmmen.data.module.Locker
import com.mohmmed.mosa.eg.towmmen.data.module.Note
import com.mohmmed.mosa.eg.towmmen.data.module.Product
import java.io.File
import java.io.FileWriter
import java.util.Date

fun exportProductsToCsv(products: List<Product>) {
    val createDate = Date()
    val parentDir = File(Environment.getExternalStorageDirectory().path + "/Shop_Manger")
    val currentDayDir = File(parentDir.path + "/${dateToString(createDate,"yyyy-MM-dd")}")
    if(!parentDir.exists()){
        parentDir.mkdirs()
    }
    if(!currentDayDir.exists()){
        currentDayDir.mkdir()
    }
    val csvFile = File(currentDayDir, "products_data_${dateToString(createDate,"HH:mm")}.csv")
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
    if(products.isNotEmpty()) {
        for (product in products) {
            fileWriter.append(buildString {
                append("${product.productId},")
                append("${product.name},")
                append("${product.barcode},")
                append("${product.cost},")
                append("${product.price},")
                append("${product.description},")
                append("${product.imagePath},")
                append("${product.category},")
                append("${product.stockQuantity},")
                append("${product.unit},")
                append("${product.manufactureDate},")
                append("${product.expireDate},")
                append("\n")
            })
        }
    }

    fileWriter.flush()
    fileWriter.close()
}

fun exportCustomersToCsv(customers: List<Customer>) {
    val createDate = Date()
    val parentDir = File(Environment.getExternalStorageDirectory().path + "/Shop_Manger")
    val currentDayDir = File(parentDir.path + "/${dateToString(createDate,"yyyy-MM-dd")}")
    if(!parentDir.exists()){
        parentDir.mkdirs()
    }
    if(!currentDayDir.exists()){
        currentDayDir.mkdir()
    }
    val csvFile = File(currentDayDir, "customers_data_${dateToString(createDate,"HH:mm")}.csv")
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
    if(customers.isNotEmpty()) {
        for (customer in customers) {
            fileWriter.append(buildString {
                append("${customer.customerId},")
                append("${customer.name},")
                append("${customer.email},")
                append("${customer.phone},")
                append("${customer.address},")
                append("${dateToString(customer.registrationDate, "yyyy-MM-dd HH:mm")},")
                append("${dateToString(customer.lastPurchaseDate?: Date(), "yyyy-MM-dd HH:mm")},")
                append("\n")
            })
        }
    }

    fileWriter.flush()
    fileWriter.close()
}

fun exportCategoryToCsv(categorys: List<Category>) {

    val createDate = Date()
    val parentDir = File(Environment.getExternalStorageDirectory().path + "/Shop_Manger")
    val currentDayDir = File(parentDir.path + "/${dateToString(createDate,"yyyy-MM-dd")}")
    if(!parentDir.exists()){
        parentDir.mkdirs()
    }
    if(!currentDayDir.exists()){
        currentDayDir.mkdir()
    }
    val csvFile = File(currentDayDir, "Category's_data_${dateToString(createDate,"HH:mm")}.csv")
    val fileWriter = FileWriter(csvFile)

    // Write CSV header
    fileWriter.append(
        "Id," + "Category\n")

    // Write data
    if(categorys.isNotEmpty()) {
        for (category in categorys) {
            fileWriter.append(buildString {
                append("${category.id},")
                append(category.category)
                append("\n")
            })
        }

    }

    fileWriter.flush()
    fileWriter.close()
}

fun exportDealers(dealers: List<Dealer>){
    val createDate = Date()
    val parentDir = File(Environment.getExternalStorageDirectory().path + "/Shop_Manger")
    val currentDayDir = File(parentDir.path + "/${dateToString(createDate,"yyyy-MM-dd")}")
    if(!parentDir.exists()){
        parentDir.mkdirs()
    }
    if(!currentDayDir.exists()){
        currentDayDir.mkdir()
    }
    val csvFile = File(currentDayDir, "dealers_data_${dateToString(createDate,"HH:mm")}.csv")
    val fileWriter = FileWriter(csvFile)

    // Write CSV header
    fileWriter.append("dealerId,"
            + "dealerName,"
            + "createDate,"
            + "dealerPhoneNumber,"
            + "dealerAddress,"
            + "dealerNote"
            +"\n")

    // Write data
    if(dealers.isNotEmpty()) {
        for (dealer in dealers) {
            fileWriter.append(buildString {
                append("${dealer.dealerId},")
                append("${dealer.dealerName},")
                append("${dateToString(dealer.createDate, "yyyy-MM-dd HH:mm")},")
                append("${dealer.dealerPhoneNumber},")
                append("${dealer.dealerAddress},")
                append(dealer.dealerNote)
                append("\n")
            })
        }
    }

    fileWriter.flush()
    fileWriter.close()
}

fun exportInvoice(invoices: List<Invoice>){
    val createDate = Date()
    val parentDir = File(Environment.getExternalStorageDirectory().path + "/Shop_Manger")
    val currentDayDir = File(parentDir.path + "/${dateToString(createDate,"yyyy-MM-dd")}")
    if(!parentDir.exists()){
        parentDir.mkdirs()
    }
    if(!currentDayDir.exists()){
        currentDayDir.mkdir()
    }
    val csvFile = File(currentDayDir, "invoices_data_${dateToString(createDate,"HH:mm")}.csv")
    val fileWriter = FileWriter(csvFile)

    // Write CSV header
    fileWriter.append("invoiceId,"
            +"customerId,"
            +"customerName,"
            +"date,"
            +"totalAmount"
            +"\n")

    // Write data
    if(invoices.isNotEmpty()) {
        for (invoice in invoices) {
            fileWriter.append(buildString {
                append("${invoice.invoiceId},")
                append("${invoice.customerId},")
                append("${invoice.customerName},")
                append(dateToString(invoice.date, "yyyy-MM-dd HH:mm"))
                append("\n")
            })
        }
    }

    fileWriter.flush()
    fileWriter.close()
}

fun exportExpanses(expanses: List<Expanse>){
    val createDate = Date()
    val parentDir = File(Environment.getExternalStorageDirectory().path + "/Shop_Manger")
    val currentDayDir = File(parentDir.path + "/${dateToString(createDate,"yyyy-MM-dd")}")
    if(!parentDir.exists()){
        parentDir.mkdirs()
    }
    if(!currentDayDir.exists()){
        currentDayDir.mkdir()
    }
    val csvFile = File(currentDayDir, "expanses_data_${dateToString(createDate,"HH:mm")}.csv")
    val fileWriter = FileWriter(csvFile)

    // Write CSV header
    fileWriter.append("expanseId,"
            + "expanse,"
            + "payDate,"
            + "amount,"
            + "\n")

    // Write data
    if(expanses.isNotEmpty()) {

        for (expanse in expanses) {
            fileWriter.append(buildString {
                append("${expanse.expanseId},")
                append("${expanse.expanse},")
                append("${dateToString(expanse.payDate, "yyyy-MM-dd HH:mm")},")
                append("${expanse.amount}")
                append("\n")
            })
        }
    }


    fileWriter.flush()
    fileWriter.close()

}

fun exportNotes(notes: List<Note>){
    val createDate = Date()
    val parentDir = File(Environment.getExternalStorageDirectory().path + "/Shop_Manger")
    val currentDayDir = File(parentDir.path + "/${dateToString(createDate,"yyyy-MM-dd")}")
    if(!parentDir.exists()){
        parentDir.mkdirs()
    }
    if(!currentDayDir.exists()){
        currentDayDir.mkdir()
    }

    val csvFile = File(currentDayDir, "notes_data_${dateToString(createDate,"HH:mm")}.csv")
    val fileWriter = FileWriter(csvFile)

    // Write CSV header
    fileWriter.append("Id,"
            + "title,"
            + "note,"
            + "date,"
            + "importance"
            + "\n")

    // Write data
    if(notes.isNotEmpty()){

        for (note in notes) {
            fileWriter.append(buildString {
                append("${note.id},")
                append("${note.title},")
                append("${note.note},")
                append("${note.date},")
                append("${note.importance}")
                append("\n")
            })
        }
    }

    fileWriter.flush()
    fileWriter.close()
}
fun exportLockers(lockers: List<Locker>){
    val createDate = Date()
    val parentDir = File(Environment.getExternalStorageDirectory().path + "/Shop_Manger")
    val currentDayDir = File(parentDir.path + "/${dateToString(createDate,"yyyy-MM-dd")}")
    if(!parentDir.exists()){
        parentDir.mkdirs()
    }
    if(!currentDayDir.exists()){
        currentDayDir.mkdir()
    }

    val csvFile = File(currentDayDir, "locker_data_${dateToString(createDate,"HH:mm")}.csv")
    val fileWriter = FileWriter(csvFile)

    // Write CSV header
    fileWriter.append("transActonId,"
            + "transActionType,"
            + "transActionDate,"
            + "transActionAmount,"
            + "transActionNote"
            + "\n")

    // Write data
    if(lockers.isNotEmpty()){

        for (locker in lockers) {
            fileWriter.append(buildString {
                append("${locker.transActonId},")
                append("${locker.transActionType},")
                append("${locker.transActionDate},")
                append("${locker.transActionAmount},")
                append("${locker.transActionNote}")
                append("\n")
            })
        }
    }

    fileWriter.flush()
    fileWriter.close()
}


fun exportAllDate(categorys: List<Category>,
                  customers: List<Customer>,
                  dealers: List<Dealer>,
                  invoices: List<Invoice>,
                  notes: List<Note>,
                  products: List<Product>,
                  expanses: List<Expanse>,
                  lockers: List<Locker>
){

    exportCategoryToCsv(categorys)
    exportCustomersToCsv(customers)
    exportDealers(dealers)
    exportExpanses(expanses)
    exportInvoice(invoices)
    exportNotes(notes)
    exportProductsToCsv(products)
    exportLockers(lockers)

}

