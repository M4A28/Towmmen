package com.mohmmed.mosa.eg.towmmen.data.repository

import com.mohmmed.mosa.eg.towmmen.data.local.dao.PurchaseDao
import com.mohmmed.mosa.eg.towmmen.data.module.Purchase
import com.mohmmed.mosa.eg.towmmen.data.module.PurchaseItem
import com.mohmmed.mosa.eg.towmmen.data.module.PurchaseWithItems
import com.mohmmed.mosa.eg.towmmen.domin.repostory.PurchaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PurchaseRepositoryImp @Inject constructor(
        private val purchaseDao: PurchaseDao
): PurchaseRepository {
    override fun getAllPurchases(): Flow<List<Purchase>> {
       return purchaseDao.getAllPurchases()
    }


    override suspend fun insertPurchase(purchase: Purchase) {
        purchaseDao.insertPurchase(purchase)
    }

    override suspend fun deletePurchase(purchase: Purchase) {
        purchaseDao.deletePurchase(purchase)
    }

    override fun getPurchasesByCustomerId(customerId: Int): Flow<List<Purchase>> {
        return purchaseDao.getPurchasesByCustomerId(customerId)
    }

    override fun getTotalPurchases(): Flow<Int> {
       return purchaseDao.getTotalPurchases()
    }

    override fun getTotalPurchasesByCustomer(customerId: Int): Flow<Int> {
        return purchaseDao.getTotalPurchasesByCustomer(customerId)
    }

    override suspend fun upsertPurchaseWithItems(items: List<PurchaseItem>) {
        purchaseDao.upsertPurchaseWithItems(items)
    }

    override fun getPurchaseWithItems(): Flow<List<PurchaseWithItems>> {
        return purchaseDao.getPurchaseWithItems()
    }

    override fun getPurchaseWithItems(purchaseId: String): Flow<PurchaseWithItems?> {
        return purchaseDao.getPurchaseWithItems(purchaseId)
    }

    override fun getPurchaseWithItemsByDealerId(dealerId: Int): Flow<List<PurchaseWithItems>> {
        return purchaseDao.getPurchaseWithItemsByDealerId(dealerId)
    }

    override suspend fun insertFullPurchase(purchase: Purchase, items: List<PurchaseItem>) {
        purchaseDao.insertFullPurchase(purchase, items)
    }

    override suspend fun clearPurchasesDate() {
        purchaseDao.clearPurchasesDate()
    }
}