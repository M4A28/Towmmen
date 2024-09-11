package com.mohmmed.mosa.eg.towmmen.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.mohmmed.mosa.eg.towmmen.data.module.Debt
import com.mohmmed.mosa.eg.towmmen.data.module.DebtItem
import com.mohmmed.mosa.eg.towmmen.data.module.DebtWithItem
import kotlinx.coroutines.flow.Flow

@Dao
interface DebtDao {


    // todo enhance this and add more caculation
    @Upsert
    suspend fun upsertDebt(debt: Debt)

    @Delete
    suspend fun deleteDebt(debt: Debt)

    @Upsert
    suspend fun upsertDebtItems(items: List<DebtItem>)

    @Delete
    suspend fun deleteDebtItems(items: List<DebtItem>)

    @Transaction
    @Query("SELECT * FROM debts WHERE isPayed = 0 ORDER BY debtDate DESC")
    fun getNotPaidDebtWithItems(): Flow<List<DebtWithItem>>

    @Transaction
    @Query("SELECT * FROM debts WHERE isPayed = 1 ORDER BY debtDate DESC")
    fun getPaidDebtWithItems(): Flow<List<DebtWithItem>>

    @Transaction
    @Query("SELECT * FROM debts ORDER BY debtDate DESC")
    fun getAllDebtWithItems(): Flow<List<DebtWithItem>>

    @Transaction
    @Query("SELECT * FROM debts WHERE  customerId =:customerId ORDER BY debtDate DESC")
    fun getDebtWithItemsByCustomerId(customerId: Int): Flow<List<DebtWithItem>>

    @Transaction
    @Query("SELECT * FROM debts WHERE  customerName =:customerName ORDER BY debtDate DESC")
    fun getDebtWithItemsByCustomerName(customerName: String): Flow<List<DebtWithItem?>>


    @Query("SELECT SUM((debtAmount)) FROM debts")
    fun getAllDebtAmount(): Flow<Double?>


    @Transaction
    suspend fun insertFullDebt(debt: Debt, items: List<DebtItem>) {
        upsertDebt(debt)
        val debtId  = debt.debtId
        items.forEach { item ->
            upsertDebtItems(listOf(item.copy(debtId = debtId)))
        }
    }


}