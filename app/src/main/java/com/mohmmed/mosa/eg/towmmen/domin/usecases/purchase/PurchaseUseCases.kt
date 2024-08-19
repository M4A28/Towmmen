package com.mohmmed.mosa.eg.towmmen.domin.usecases.purchase

data class PurchaseUseCases(
    val getAllPurchases: GetAllPurchases,
    val getTotalPurchasesByCustomer: GetTotalPurchasesByCustomer,
    val getPurchasesByCustomerId: GetPurchasesByCustomerId,
    val getTotalPurchases: GetTotalPurchases,
    val insertPurchase: InsertPurchase,
    val deletePurchase: DeletePurchase
) {
}