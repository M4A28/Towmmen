package com.mohmmed.mosa.eg.towmmen.domin.usecases.invoice

import com.mohmmed.mosa.eg.towmmen.data.module.Invoice
import com.mohmmed.mosa.eg.towmmen.data.repository.InvoiceRepositoryImp
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInvoiceByCustomer @Inject constructor(
    private val invoiceRepositoryImp: InvoiceRepositoryImp
){
    operator fun  invoke(customerName: String): Flow<List<Invoice>> {
        return invoiceRepositoryImp.getInvoiceByCustomer(customerName)
    }
}