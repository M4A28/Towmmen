package com.mohmmed.mosa.eg.towmmen.data.repository

import com.mohmmed.mosa.eg.towmmen.data.local.dao.DebtDao
import com.mohmmed.mosa.eg.towmmen.data.module.Debt
import com.mohmmed.mosa.eg.towmmen.data.module.DebtItem
import com.mohmmed.mosa.eg.towmmen.data.module.DebtWithItem
import com.mohmmed.mosa.eg.towmmen.domin.repostory.DebtRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DebtRepositoryImp @Inject constructor(private val debtDao: DebtDao): DebtRepository {
    override suspend fun upsertDebt(debt: Debt) {
        debtDao.upsertDebt(debt)
    }

    override suspend fun deleteDebt(debt: Debt) {
        debtDao.deleteDebt(debt)
    }

    override suspend fun upsertDebtItems(items: List<DebtItem>) {
        debtDao.upsertDebtItems(items)
    }

    override suspend fun deleteDebtItems(items: List<DebtItem>) {
        debtDao.deleteDebtItems(items)
    }

    override fun getNotPaidDebtWithItems(): Flow<List<DebtWithItem>> {
        return debtDao.getNotPaidDebtWithItems()
    }

    override fun getPaidDebtWithItems(): Flow<List<DebtWithItem>> {
        return debtDao.getPaidDebtWithItems()
    }

    override fun getAllDebtWithItems(): Flow<List<DebtWithItem>> {
        return debtDao.getAllDebtWithItems()
    }

    override fun getDebtWithItemsByCustomerId(customerId: Int): Flow<List<DebtWithItem>> {
        return debtDao.getDebtWithItemsByCustomerId(customerId)
    }

    override fun getDebtWithItemsByCustomerName(customerName: String): Flow<List<DebtWithItem?>> {
        return debtDao.getDebtWithItemsByCustomerName(customerName)
    }

    override fun getAllDebtAmount(): Flow<Double?> {
        return debtDao.getAllDebtAmount()
    }

    override suspend fun insertFullDebt(debt: Debt, items: List<DebtItem>) {
        debtDao.insertFullDebt(debt, items)
    }


}