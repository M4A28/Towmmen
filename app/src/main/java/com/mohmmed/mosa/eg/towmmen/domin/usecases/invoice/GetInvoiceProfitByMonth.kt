package com.mohmmed.mosa.eg.towmmen.domin.usecases.invoice

import com.mohmmed.mosa.eg.towmmen.data.module.InvoiceProfitByPeriod
import com.mohmmed.mosa.eg.towmmen.data.repository.InvoiceRepositoryImp
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInvoiceProfitByMonth @Inject constructor(
    private val invoiceRepositoryImp: InvoiceRepositoryImp
){
    operator fun  invoke(): Flow<List<InvoiceProfitByPeriod>> {
        return invoiceRepositoryImp.getInvoiceProfitByMonth()
    }
}
class GetInvoiceProfitByDay @Inject constructor(
    private val invoiceRepositoryImp: InvoiceRepositoryImp
){
    operator fun  invoke(): Flow<List<InvoiceProfitByPeriod>> {
        return invoiceRepositoryImp.getInvoiceProfitByDay()
    }
}
class GetInvoiceProfitByWeek @Inject constructor(
    private val invoiceRepositoryImp: InvoiceRepositoryImp
){
    operator fun  invoke(): Flow<List<InvoiceProfitByPeriod>> {
        return invoiceRepositoryImp.getInvoiceProfitByWeek()
    }
}