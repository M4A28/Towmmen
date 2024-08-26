package com.mohmmed.mosa.eg.towmmen.domin.usecases.invoice

import com.mohmmed.mosa.eg.towmmen.data.module.MostTopProduct
import com.mohmmed.mosa.eg.towmmen.data.repository.InvoiceRepositoryImp
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMostTopProduct @Inject constructor(
    private val invoiceRepositoryImp: InvoiceRepositoryImp
){
    operator fun  invoke(): Flow<List<MostTopProduct>> {
        return invoiceRepositoryImp.getMostTopProduct()
    }
}