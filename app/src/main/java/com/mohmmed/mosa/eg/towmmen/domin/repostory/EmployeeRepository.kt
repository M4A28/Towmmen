package com.mohmmed.mosa.eg.towmmen.domin.repostory

import com.mohmmed.mosa.eg.towmmen.domin.module.Employee
import kotlinx.coroutines.flow.Flow

interface EmployeeRepository {

    suspend fun addNewEmployee(employee: Employee)

    suspend fun deleteEmployee(employee: Employee)

    suspend fun updateEmployee(employee: Employee)

    fun getEmployee(employeeName: String): Flow<List<Employee>>

    fun getAllEmployee(): Flow<List<Employee>>

}