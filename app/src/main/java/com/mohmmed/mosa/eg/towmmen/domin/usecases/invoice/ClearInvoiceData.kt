package com.mohmmed.mosa.eg.towmmen.domin.usecases.invoice

import com.mohmmed.mosa.eg.towmmen.data.repository.InvoiceRepositoryImp
import javax.inject.Inject

class ClearInvoiceData @Inject constructor(
    private val invoiceRepositoryImp: InvoiceRepositoryImp
){
    suspend operator fun invoke() {
        invoiceRepositoryImp.clearInvoiceDate()
    }
}