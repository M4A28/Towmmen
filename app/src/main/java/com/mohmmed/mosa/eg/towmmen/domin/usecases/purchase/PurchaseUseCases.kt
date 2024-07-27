package com.mohmmed.mosa.eg.towmmen.domin.usecases.purchase

data class PurchaseUseCases(
    val getAllPurchases: GetAllPurchases,
    val getTotalPurchasesByCustomer: GetTotalPurchasesByCustomer,
    val getCustomersWithPurchases: GetCustomersWithPurchases,
    val getPurchasesWithDetails: GetPurchasesWithDetails,
    val getPurchasesByCustomerId: GetPurchasesByCustomerId,
    val getTotalPurchases: GetTotalPurchases,
    val insertPurchase: InsertPurchase,
    val deletePurchase: DeletePurchase
) {
}