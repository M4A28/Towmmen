package com.mohmmed.mosa.eg.towmmen.domin.module

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "employees")
data class Employee(
    @PrimaryKey val employeeId: Int,
    val name: String,
    val position: String,
    val department: String,
    val salary: Double,
    val hireDate: Date,
    val email: String,
    val phone: String
)