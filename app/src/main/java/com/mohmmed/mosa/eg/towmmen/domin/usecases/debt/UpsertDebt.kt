package com.mohmmed.mosa.eg.towmmen.domin.usecases.debt

import com.mohmmed.mosa.eg.towmmen.data.module.Debt
import com.mohmmed.mosa.eg.towmmen.data.module.DebtItem
import com.mohmmed.mosa.eg.towmmen.data.repository.DebtRepositoryImp
import javax.inject.Inject

class UpsertDebt @Inject constructor(
    private val debtRepositoryImp: DebtRepositoryImp
){
    suspend operator fun invoke(debt: Debt) {
        debtRepositoryImp.upsertDebt(debt)
    }

}

class UpsertDebtItems @Inject constructor(
    private val debtRepositoryImp: DebtRepositoryImp
){
    suspend operator fun invoke(debtItem: List<DebtItem>) {
        debtRepositoryImp.upsertDebtItems(debtItem)
    }

}
class InsertFullDebt @Inject constructor(
    private val debtRepositoryImp: DebtRepositoryImp
){
    suspend operator fun invoke(debt: Debt, debtItem: List<DebtItem>) {
        debtRepositoryImp.insertFullDebt(debt, debtItem)
    }

}