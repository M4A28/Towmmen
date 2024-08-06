package com.mohmmed.mosa.eg.towmmen.domin.usecases.invoice

import com.mohmmed.mosa.eg.towmmen.data.module.Invoice
import com.mohmmed.mosa.eg.towmmen.data.repository.InvoiceRepositoryImp
import javax.inject.Inject

class UpsertInvoice @Inject constructor(
    private val invoiceRepositoryImp: InvoiceRepositoryImp
){
    suspend operator fun  invoke(invoice: Invoice){
        invoiceRepositoryImp.upsertInvoice(invoice)
    }
}