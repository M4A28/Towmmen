package com.mohmmed.mosa.eg.towmmen.data.repository

import com.mohmmed.mosa.eg.towmmen.data.local.dao.EmployeeDao
import com.mohmmed.mosa.eg.towmmen.data.module.Employee
import com.mohmmed.mosa.eg.towmmen.domin.repostory.EmployeeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EmployeeRepositoryImp @Inject constructor(
    private val employeeDao: EmployeeDao
): EmployeeRepository {
    override suspend fun addNewEmployee(employee: Employee) {
        employeeDao.insertEmployee(employee)
    }

    override suspend fun deleteEmployee(employee: Employee) {
        employeeDao.deleteEmployee(employee)
    }

    override suspend fun updateEmployee(employee: Employee) {
        employeeDao.updateEmployee(employee)
    }

    override fun getEmployee(employeeName: String): Flow<List<Employee>> {
        return  employeeDao.getEmployeeByName(employeeName)
    }

    override fun getAllEmployee(): Flow<List<Employee>> {
        return  employeeDao.getAllEmployees()
    }
}