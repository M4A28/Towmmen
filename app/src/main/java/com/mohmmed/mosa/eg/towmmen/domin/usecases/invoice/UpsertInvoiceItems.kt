package com.mohmmed.mosa.eg.towmmen.domin.usecases.invoice

import com.mohmmed.mosa.eg.towmmen.data.module.InvoiceItem
import com.mohmmed.mosa.eg.towmmen.data.repository.InvoiceRepositoryImp
import javax.inject.Inject

class UpsertInvoiceItems @Inject constructor(
    private val invoiceRepositoryImp: InvoiceRepositoryImp
){
    suspend operator fun   invoke(items: List<InvoiceItem>) {
        invoiceRepositoryImp.upsertInvoiceItems(items)
    }
}