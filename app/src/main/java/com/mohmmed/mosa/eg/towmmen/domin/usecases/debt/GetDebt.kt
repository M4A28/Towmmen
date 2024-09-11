package com.mohmmed.mosa.eg.towmmen.domin.usecases.debt

import com.mohmmed.mosa.eg.towmmen.data.module.DebtWithItem
import com.mohmmed.mosa.eg.towmmen.data.repository.DebtRepositoryImp
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDebtByCustomerName @Inject constructor(
    private val debtRepositoryImp: DebtRepositoryImp
){
    operator fun invoke(name: String): Flow<List<DebtWithItem?>> {
        return debtRepositoryImp.getDebtWithItemsByCustomerName(name)
    }

}
class GetDebtByCustomerId @Inject constructor(
    private val debtRepositoryImp: DebtRepositoryImp
){
    operator fun invoke(id: Int): Flow<List<DebtWithItem>> {
        return debtRepositoryImp.getDebtWithItemsByCustomerId(id)
    }

}
class GetTotalDebt @Inject constructor(
    private val debtRepositoryImp: DebtRepositoryImp
){
    operator fun invoke(): Flow<Double?> {
        return debtRepositoryImp.getAllDebtAmount()
    }

}


class GetNotPaidDebtWithItems @Inject constructor(
    private val debtRepositoryImp: DebtRepositoryImp
){
    operator fun invoke(): Flow<List<DebtWithItem>> {
        return debtRepositoryImp.getNotPaidDebtWithItems()
    }

}

class GetPaidDebtWithItems @Inject constructor(
    private val debtRepositoryImp: DebtRepositoryImp
){
    operator fun invoke(): Flow<List<DebtWithItem>> {
        return debtRepositoryImp.getPaidDebtWithItems()
    }

}

class GetAllDebtWithItems @Inject constructor(
    private val debtRepositoryImp: DebtRepositoryImp
){
    operator fun invoke(): Flow<List<DebtWithItem>> {
        return debtRepositoryImp.getAllDebtWithItems()
    }

}