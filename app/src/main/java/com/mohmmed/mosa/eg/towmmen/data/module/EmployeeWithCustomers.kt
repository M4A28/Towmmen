package com.mohmmed.mosa.eg.towmmen.data.module

import androidx.room.Embedded
import androidx.room.Relation

data class EmployeeWithCustomers(
    @Embedded val employee: Employee,
    @Relation(
        parentColumn = "employeeId",
        entityColumn = "customerId"
    )
    val customers: List<Customer>
)