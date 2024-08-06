package com.mohmmed.mosa.eg.towmmen.domin.usecases.invoice

import com.mohmmed.mosa.eg.towmmen.data.module.Invoice
import com.mohmmed.mosa.eg.towmmen.data.module.InvoiceItem
import com.mohmmed.mosa.eg.towmmen.data.repository.InvoiceRepositoryImp
import javax.inject.Inject

class InsertFullInvoice @Inject constructor(
    private val invoiceRepositoryImp: InvoiceRepositoryImp
){
    suspend operator fun  invoke(invoice: Invoice, invoiceItems: List<InvoiceItem>){
        invoiceRepositoryImp.insertFullInvoice(invoice, invoiceItems)
    }
}