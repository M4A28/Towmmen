package com.mohmmed.mosa.eg.towmmen.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mohmmed.mosa.eg.towmmen.data.local.converters.DateTypeConverter
import com.mohmmed.mosa.eg.towmmen.data.local.dao.CustomerDao
import com.mohmmed.mosa.eg.towmmen.data.local.dao.CustomerProductDao
import com.mohmmed.mosa.eg.towmmen.data.local.dao.EmployeeDao
import com.mohmmed.mosa.eg.towmmen.data.local.dao.NoteDao
import com.mohmmed.mosa.eg.towmmen.data.local.dao.ProductDao
import com.mohmmed.mosa.eg.towmmen.domin.module.Customer
import com.mohmmed.mosa.eg.towmmen.domin.module.CustomerProductCrossRef
import com.mohmmed.mosa.eg.towmmen.domin.module.Employee
import com.mohmmed.mosa.eg.towmmen.domin.module.Note
import com.mohmmed.mosa.eg.towmmen.domin.module.Product

@Database(entities = [Product::class, Customer::class,
    Employee::class,
    CustomerProductCrossRef::class,
                     Note::class], version = 2)
@TypeConverters(DateTypeConverter::class)
abstract class TowmmenDatabase: RoomDatabase() {
    abstract val productDao: ProductDao
    abstract val customerDao: CustomerDao
    abstract val employeeDao: EmployeeDao
    abstract val customerProductDao: CustomerProductDao
    abstract val noteDao: NoteDao
}