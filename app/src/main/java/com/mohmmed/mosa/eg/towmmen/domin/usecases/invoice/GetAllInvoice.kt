package com.mohmmed.mosa.eg.towmmen.domin.usecases.invoice

import com.mohmmed.mosa.eg.towmmen.data.module.Invoice
import com.mohmmed.mosa.eg.towmmen.data.repository.InvoiceRepositoryImp
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllInvoice @Inject constructor(
    private val invoiceRepositoryImp: InvoiceRepositoryImp
){
    operator fun invoke(): Flow<List<Invoice>> {
        return invoiceRepositoryImp.getAllInvoices()
    }
}