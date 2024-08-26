package com.mohmmed.mosa.eg.towmmen.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mohmmed.mosa.eg.towmmen.data.local.converters.DateTypeConverter
import com.mohmmed.mosa.eg.towmmen.data.local.dao.CategoryDao
import com.mohmmed.mosa.eg.towmmen.data.local.dao.CustomerDao
import com.mohmmed.mosa.eg.towmmen.data.local.dao.DealerDao
import com.mohmmed.mosa.eg.towmmen.data.local.dao.EmployeeDao
import com.mohmmed.mosa.eg.towmmen.data.local.dao.ExpanseDao
import com.mohmmed.mosa.eg.towmmen.data.local.dao.InvoiceDao
import com.mohmmed.mosa.eg.towmmen.data.local.dao.LockerDao
import com.mohmmed.mosa.eg.towmmen.data.local.dao.NoteDao
import com.mohmmed.mosa.eg.towmmen.data.local.dao.ProductDao
import com.mohmmed.mosa.eg.towmmen.data.local.dao.PurchaseDao
import com.mohmmed.mosa.eg.towmmen.data.module.Category
import com.mohmmed.mosa.eg.towmmen.data.module.Customer
import com.mohmmed.mosa.eg.towmmen.data.module.Dealer
import com.mohmmed.mosa.eg.towmmen.data.module.Employee
import com.mohmmed.mosa.eg.towmmen.data.module.Expanse
import com.mohmmed.mosa.eg.towmmen.data.module.Invoice
import com.mohmmed.mosa.eg.towmmen.data.module.InvoiceItem
import com.mohmmed.mosa.eg.towmmen.data.module.Locker
import com.mohmmed.mosa.eg.towmmen.data.module.Note
import com.mohmmed.mosa.eg.towmmen.data.module.Product
import com.mohmmed.mosa.eg.towmmen.data.module.Purchase

@Database(entities = [
    Product::class,
    Customer::class,
    Employee::class,
    Note::class,
    Purchase::class,
    Category::class,
    Invoice::class,
    Expanse::class,
    InvoiceItem::class ,
    Locker::class ,
    Dealer::class],
    version = 9,
    exportSchema = true)

@TypeConverters(DateTypeConverter::class)
abstract class TowmmenDatabase: RoomDatabase() {
    abstract val productDao: ProductDao
    abstract val customerDao: CustomerDao
    abstract val employeeDao: EmployeeDao
    abstract val noteDao: NoteDao
    abstract val purchaseDao: PurchaseDao
    abstract val categoryDao: CategoryDao
    abstract val invoiceDao: InvoiceDao
    abstract val expanseDao: ExpanseDao
    abstract val dealerDao: DealerDao
    abstract val lockerDao: LockerDao
}