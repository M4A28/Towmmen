package com.mohmmed.mosa.eg.towmmen.domin.usecases.invoice

import com.mohmmed.mosa.eg.towmmen.data.repository.InvoiceRepositoryImp
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTotalInvoices @Inject constructor(
    private val invoiceRepository: InvoiceRepositoryImp
){
    operator fun invoke(): Flow<Double?>{
        return invoiceRepository.getTotalInvoices()
    }

}
