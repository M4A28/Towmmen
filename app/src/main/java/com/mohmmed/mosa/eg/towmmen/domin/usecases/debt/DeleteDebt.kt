package com.mohmmed.mosa.eg.towmmen.domin.usecases.debt

import com.mohmmed.mosa.eg.towmmen.data.module.Debt
import com.mohmmed.mosa.eg.towmmen.data.module.DebtItem
import com.mohmmed.mosa.eg.towmmen.data.repository.DebtRepositoryImp
import javax.inject.Inject

class DeleteDebt @Inject constructor(
    private val debtRepositoryImp: DebtRepositoryImp
){
    suspend operator fun invoke(debt: Debt) {
        debtRepositoryImp.deleteDebt(debt)
    }

}

class DeleteDebtItems @Inject constructor(
    private val debtRepositoryImp: DebtRepositoryImp
){
    suspend operator fun invoke(debtItems: List<DebtItem>) {
        debtRepositoryImp.deleteDebtItems(debtItems)
    }

}