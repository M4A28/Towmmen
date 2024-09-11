package com.mohmmed.mosa.eg.towmmen.domin.repostory

import com.mohmmed.mosa.eg.towmmen.data.module.Debt
import com.mohmmed.mosa.eg.towmmen.data.module.DebtItem
import com.mohmmed.mosa.eg.towmmen.data.module.DebtWithItem
import kotlinx.coroutines.flow.Flow

interface DebtRepository {

    suspend fun upsertDebt(debt: Debt)

    suspend fun deleteDebt(debt: Debt)

    suspend fun upsertDebtItems(items: List<DebtItem>)

    suspend fun deleteDebtItems(items: List<DebtItem>)

    fun getNotPaidDebtWithItems(): Flow<List<DebtWithItem>>

    fun getPaidDebtWithItems(): Flow<List<DebtWithItem>>

    fun getAllDebtWithItems(): Flow<List<DebtWithItem>>

    fun getDebtWithItemsByCustomerId(customerId: Int): Flow<List<DebtWithItem>>

    fun getDebtWithItemsByCustomerName(customerName: String): Flow<List<DebtWithItem?>>


    fun getAllDebtAmount(): Flow<Double?>


    suspend fun insertFullDebt(debt: Debt, items: List<DebtItem>)

}