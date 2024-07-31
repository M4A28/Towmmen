package com.mohmmed.mosa.eg.towmmen.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.mohmmed.mosa.eg.towmmen.data.module.Employee
import com.mohmmed.mosa.eg.towmmen.data.module.EmployeeWithCustomers
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeeDao {
    @Query("SELECT * FROM employees")
    fun getAllEmployees(): Flow<List<Employee>>

    @Query("SELECT * FROM employees WHERE employeeId = :id")
    fun getEmployeeById(id: Int): Flow<Employee>

    @Query("SELECT * FROM employees WHERE name = :name")
    fun getEmployeeByName(name: String): Flow<List<Employee>>

    @Query("SELECT * FROM employees WHERE department = :department")
    fun getEmployeesByDepartment(department: String): Flow<List<Employee>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployee(employee: Employee)

    @Update
    suspend fun updateEmployee(employee: Employee)

    @Delete
    suspend fun deleteEmployee(employee: Employee)

    @Query("SELECT AVG(salary) FROM employees WHERE department = :department")
    fun getAverageSalaryByDepartment(department: String): Flow<Double>

    @Transaction
    @Query("SELECT * FROM employees")
    fun getEmployeesWithCustomers(): Flow<List<EmployeeWithCustomers>>
}