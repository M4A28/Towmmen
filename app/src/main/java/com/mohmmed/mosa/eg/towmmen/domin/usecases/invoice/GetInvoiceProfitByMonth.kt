package com.mohmmed.mosa.eg.towmmen.domin.usecases.invoice

import com.mohmmed.mosa.eg.towmmen.data.module.InvoiceProfitByMonth
import com.mohmmed.mosa.eg.towmmen.data.repository.InvoiceRepositoryImp
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInvoiceProfitByMonth @Inject constructor(
    private val invoiceRepositoryImp: InvoiceRepositoryImp
){
    operator fun  invoke(): Flow<List<InvoiceProfitByMonth>> {
        return invoiceRepositoryImp.getInvoiceProfitByMonth()
    }
}