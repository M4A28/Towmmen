package com.mohmmed.mosa.eg.towmmen.domin.usecases.debt

data class DebtUseCases(
    val upsertDebt: UpsertDebt,
    val upsertDebtItems: UpsertDebtItems,
    val insertFullDebt: InsertFullDebt,
    val deleteDebt: DeleteDebt,
    val deleteDebtItems: DeleteDebtItems,
    val getAllDebtWithItems: GetAllDebtWithItems,
    val getPaidDebtWithItems: GetPaidDebtWithItems,
    val getNotPaidDebtWithItems: GetNotPaidDebtWithItems,
    val getTotalDebt: GetTotalDebt,
    val getDebtByCustomerId: GetDebtByCustomerId,
    val getDebtByCustomerName: GetDebtByCustomerName,


) {
}